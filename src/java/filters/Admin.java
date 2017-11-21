/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import businesslogic.UserService;
import domainmodel.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 684243
 */
@WebFilter(filterName = "Admin", urlPatterns = {"/*"})
public class Admin implements Filter {

    private FilterConfig filterConfig = null;

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        String username = (String) session.getAttribute("username");
        UserService service = new UserService();

        User user = null;
        try {
            user = service.get(username);
        } catch (Exception e) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, e);
        }

        if (user != null && user.getRole().getRoleID() == 1) {
            chain.doFilter(request, response);

        } else {
            ((HttpServletResponse) response).sendRedirect("login");

        }

    }

    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;

    }
}
