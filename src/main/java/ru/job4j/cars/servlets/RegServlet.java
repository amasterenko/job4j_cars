package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
/**
 * Saves a user with specified fields to the repository.
 * Checks the user's email for uniqueness.
 *
 * @author AndrewMs
 * @version 1.0
 */

public class RegServlet extends HttpServlet {
    private static final AdRepository REP = AdRepository.instOf();
    private static final Logger LOG = LoggerFactory.getLogger(RegServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            String name = req.getParameter("name");
            String phone = req.getParameter("phone");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
        Optional<User> optUser = REP.saveUser(new User(name, email, phone, password));
        if (optUser.isEmpty()) {
            req.setAttribute("err", "The specified phone or email already exists.");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/signin.jsp");
        } catch (Exception e) {
            LOG.error("Exception occurred: ", e);
        }
    }
}
