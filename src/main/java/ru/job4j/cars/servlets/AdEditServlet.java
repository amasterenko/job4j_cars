package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.*;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
/**
 * doGet - replies with car categories required for creation of new ad.
 * doPost - receives the new ad's data and saves it using AdRepository methods.
 * The new ad's photos are saving on the disk (parameter "imagesPath" in web.xml) in
 * a separate folder for each ad.
 *
 * @author AndrewMs
 * @version 1.0
 *
 */


@MultipartConfig
public class AdEditServlet extends HttpServlet {
    private static final AdRepository REP = AdRepository.instOf();
    private static final Logger LOG = LoggerFactory.getLogger(AdEditServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("makes", REP.findMakes().orElse(List.of()));
        req.setAttribute("bodies", REP.findBodies().orElse(List.of()));
        req.setAttribute("colors", REP.findColors().orElse(List.of()));
        req.setAttribute("engines", REP.findEngines().orElse(List.of()));
        req.setAttribute("transmissions", REP.findTransmissions().orElse(List.of()));
        req.getRequestDispatcher("adedit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            Ad ad = new Ad();
            ad.setTitle(req.getParameter("title"));
            ad.setModel(new Model(Integer.parseInt(req.getParameter("model"))));
            ad.setBody(new Body(Integer.parseInt(req.getParameter("body"))));
            ad.setColor(new Color(Integer.parseInt(req.getParameter("color"))));
            ad.setEngine(new Engine(Integer.parseInt(req.getParameter("engine"))));
            if(req.getParameter("engDisp") != null) {
                ad.setEngineDisplacement(Float.parseFloat(req.getParameter("engDisp")));
            }
            ad.setTransmission(new Transmission(Integer.parseInt(req.getParameter("transmission"))));
            ad.setKm(Integer.parseInt(req.getParameter("km")));
            ad.setYear(Integer.parseInt(req.getParameter("year")));
            ad.setPrice(Integer.parseInt(req.getParameter("price")));
            ad.setUser((User) req.getSession().getAttribute("user"));
            ad.setDescription(req.getParameter("description"));
            ad = REP.saveAd(ad).orElse(new Ad(0));
            if (ad.getId() == 0) {
                req.setAttribute("err", "The ad could not be saved.");
                req.getRequestDispatcher("/adedit.jsp").forward(req, resp);
                return;
            }

            String uploadPath = getServletContext().getInitParameter("imagesPath") + File.separator + ad.getId();
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            List<Part> fileParts = req.getParts().stream()
                    .filter(part -> "files".equals(part.getName()) && part.getSize() > 0)
                    .collect(Collectors.toList());
            int i = 0;
            for (Part filePart : fileParts) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String fileExt = fileName.substring(fileName.lastIndexOf('.'));
                String filePath = uploadPath + File.separator + (i++) + fileExt;
                filePart.write(filePath);
                ad.addPhoto(new Photo(filePath));
            }
            REP.update(ad);
            resp.sendRedirect(req.getContextPath() + "/ads");
        } catch (Exception e) {
            LOG.error("Exception occurred: ", e);
        }
    }
}
