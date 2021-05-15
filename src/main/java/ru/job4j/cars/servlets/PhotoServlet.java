package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;
/**
 * doGet - replies with an image file stored in the repository using photo id.
 * If there is no photo in the repository the servlet replies with
 * "n.gif" file from "img" project folder.
 *
 * @author AndrewMs
 * @version 1.0
 *
 */
public class PhotoServlet extends HttpServlet {
    private static final AdRepository REP = AdRepository.instOf();
    private static final Logger LOG = LoggerFactory.getLogger(PhotoServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
            int photoId = Integer.parseInt(req.getParameter("id"));
            //String defaultPath = getServletContext().getInitParameter("imagesPath") + File.separator + "n.gif";
            String defaultPath = getServletContext().getRealPath("img") + File.separator + "n.gif";
            Optional<Photo> foundPhoto = REP.findPhoto(photoId);
            File downloadFile = new File(foundPhoto.orElse(new Photo(defaultPath)).getImgPath());
            resp.setContentType("application/octet-stream");
            resp.setHeader(
                    "Content-Disposition",
                    "attachment; filename=\"" + downloadFile.getName() + "\""
            );
            try (FileInputStream stream = new FileInputStream(downloadFile)) {
                resp.getOutputStream().write(stream.readAllBytes());
            }
        } catch (Exception e) {
            LOG.error("Exception :", e);
        }
    }
}
