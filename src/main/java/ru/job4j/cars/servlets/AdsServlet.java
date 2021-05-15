package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * doGet - replies with all the ads and car categories' data.
 * doPost - replies with filtered ads and car categories' data.
 * Method receives the filter's parameters from client and saves them to maps,
 * then it passes them to AdRepository method filterAds.
 *
 * @author AndrewMs
 * @version 1.0
 *
 */
public class AdsServlet extends HttpServlet {
    private static final AdRepository REP = AdRepository.instOf();
    private static final Logger LOG = LoggerFactory.getLogger(AdsServlet.class.getName());
    private static final Map<String, String> NO_RANGE_FILTERS = new HashMap<>() {{
        put("filterCreated", "daysAgo");
        put("filterMake", "makeId");
        put("filterModel", "modelId");
        put("filterEngine", "engineId");
    }};
    private final Map<String, Set<String>> RANGE_FILTERS = new HashMap<>() {{
        put("filterPrice", Set.of("minPrice", "maxPrice"));
        put("filterYear", Set.of("minYear", "maxYear"));
        put("filterKm", Set.of("minKm", "maxKm"));
    }};


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            req.setAttribute("userCars", false);
            req.setAttribute("ads", REP.findActiveAds().orElse(List.of()));
            req.setAttribute("makes", REP.findMakes().orElse(List.of()));
            req.setAttribute("engines", REP.findEngines().orElse(List.of()));
            req.getRequestDispatcher("ads.jsp").forward(req, resp);
        } catch (Exception e) {
            LOG.error("Exception :", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        try {
            Map<String, Map<String,Object>> map = new HashMap<>();
            RANGE_FILTERS.forEach((name, parameters) ->{
                Map<String, Object> paramMap = new HashMap<>();
                parameters.forEach(parameter -> paramMap.put(
                        parameter, Integer.parseInt(req.getParameter(parameter)))
                );
                map.put(name, paramMap);
            });
            NO_RANGE_FILTERS.forEach((name, parameter) -> {
                if (req.getParameter(parameter) != null) {
                    map.put(
                            name,
                            new HashMap<>() {{
                                put(parameter, Integer.parseInt(req.getParameter(parameter)));
                            }});
                };
            });
            req.setAttribute("ads", REP.filterAds(map).orElse(List.of()));
            req.setAttribute("makes", REP.findMakes().orElse(List.of()));
            req.setAttribute("engines", REP.findEngines().orElse(List.of()));
            req.getRequestDispatcher("ads.jsp").forward(req, resp);
        } catch (Exception e) {
            LOG.error("Exception occurred: ", e);
        }
    }
}
