package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.Ad;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * doGet - replies with current user's ads.
 * doPost - receives sold ad's id, checks the ad's owner and set the ad's status to "sold".
 * @author AndrewMs
 * @version 1.0
 *
 */
public class UserAdsServlet extends HttpServlet {
    private static final AdRepository REP = AdRepository.instOf();
    private static final Logger LOG = LoggerFactory.getLogger(UserAdsServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            User currentUser = (User) req.getSession().getAttribute("user");
            req.setAttribute("ads", REP.findAdsByUser(currentUser).orElse(List.of()));
            req.setAttribute("userCars", true);
            req.getRequestDispatcher("ads.jsp").forward(req, resp);

        } catch(Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            int soldAdId = Integer.parseInt(req.getParameter("sold"));
            User adOwner = REP.findAdOwner(new Ad(soldAdId)).orElse(new User(0));
            User currentUser = (User)req.getSession().getAttribute("user");
            if (adOwner.equals(currentUser)) {
                Ad ad = REP.findAdById(soldAdId).orElse(new Ad());
                ad.setSold(true);
                REP.update(ad);
            }
            resp.sendRedirect(req.getContextPath() + "/userads");
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }
}
