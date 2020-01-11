package pl.altkom.web.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import pl.altkom.web.Modul;
import pl.altkom.web.exception.AuthenticationException;
import pl.altkom.web.model.User;
import pl.altkom.web.service.SecurityManager;
import pl.altkom.web.service.SecurityManagerImpl;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "register", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private SecurityManager securityManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        Injector injector = Guice.createInjector(new Modul());
        securityManager = injector.getInstance(SecurityManager.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        securityManager.addUser(login, password);
        req.getRequestDispatcher("hello").forward(req, resp);
    }
}
