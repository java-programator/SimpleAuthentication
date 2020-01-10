package pl.altkom.web.servlet;

import pl.altkom.web.exception.AuthenticationException;
import pl.altkom.web.model.User;
import pl.altkom.web.service.SecurityManager;
import pl.altkom.web.service.SecurityManagerImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private SecurityManager securityManager;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            securityManager = new SecurityManagerImpl();
        } catch (AuthenticationException e) {
            throw new ServletException("Cannot init connection to database", e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && password != null) {
            if (securityManager.isCorrectPassword(login, password)) {
                User user = new User(login, password);
                req.getSession().setAttribute("loggedUser", user);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("hello").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("hello").forward(req, resp);
        }
    }
}
