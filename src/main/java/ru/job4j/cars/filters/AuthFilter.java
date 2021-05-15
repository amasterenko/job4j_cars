package ru.job4j.cars.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects unauthorized users to signin.jsp when they attempt to get /edit or /userads pages
 * and passes all the users to main page (/ads) and signin/signup pages.
 *
 *@author AndrewMs
 *@version 1.0
 */
public class AuthFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) {
        try {
            HttpServletRequest req = (HttpServletRequest) sreq;
            HttpServletResponse resp = (HttpServletResponse) sresp;
            String uri = req.getRequestURI();
            if (req.getSession().getAttribute("user") == null && (uri.endsWith("/edit") || uri.endsWith("/userads"))) {
                resp.sendRedirect(req.getContextPath() + "/signin.jsp");
                return;
            }
            chain.doFilter(sreq, sresp);
        }catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    @Override
    public void destroy() {
    }
}