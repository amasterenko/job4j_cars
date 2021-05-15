package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
/**
 * The class authenticates users by serving POST-requests or
 * makes users unauthenticated by serving GET-requests.
 * @author AndrewMs
 * @version 1.0
 */

public class AuthServlet extends HttpServlet {
    private static final AdRepository REP = AdRepository.instOf();
    private static final Logger LOG = LoggerFactory.getLogger(AuthServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String cmd = req.getParameter("cmd");
        if ("out".equals(cmd)) {
            req.getSession().setAttribute("user", null);
            req.getRequestDispatcher("ads").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Optional<User> optUser = REP.findUser(email);
            if (optUser.isEmpty() || !optUser.get().getCredentials().validate(password)) {
                req.setAttribute("err", "The specified email or password is wrong.");
                req.getRequestDispatcher("signin.jsp").forward(req, resp);
                return;
            }
            HttpSession sc = req.getSession();
            sc.setAttribute("user", optUser.get());
            resp.sendRedirect(req.getContextPath() + "/ads");
        } catch (Exception e) {
            LOG.error("An exception occurred:", e);
        }
    }
}
