package ru.job4j.cars.servlets;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.Make;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * doGet - replies with car model categories' using JSON.
 *
 * @author AndrewMs
 * @version 1.0
 *
 */
public class CarModelsServlet extends HttpServlet {
    private static final AdRepository REP = AdRepository.instOf();
    private static final Logger LOG = LoggerFactory.getLogger(CarModelsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Make make = new Make(Integer.parseInt(req.getParameter("makeid")));
            resp.getWriter().print(new JSONArray(REP.findModelsByMake(make).orElse(List.of())));
        } catch(Exception e) {
            LOG.error("An exception occurred", e);
        }
    }
}
