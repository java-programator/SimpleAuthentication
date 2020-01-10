package pl.altkom.web.servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "create_database", urlPatterns = "/create")
public class CreateDatabase extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        DataSource dataSource;
        Connection connection = null;
        Statement stmt = null;
        try {
            InitialContext initCtx = new InitialContext();
            Context context = (Context) initCtx.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc:users");
            connection = dataSource.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE users (name text primary key, password text);");
            stmt.executeUpdate("INSERT into users VALUES('admin', 'admin');");
        } catch (SQLException | NamingException e) {
            throw new ServletException("Cannot create database", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
