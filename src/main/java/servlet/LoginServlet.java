package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Hardcoded username and password for demonstration purposes
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate the credentials (hardcoded for now)
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            // Create a session and store the username
            HttpSession session = request.getSession();
            session.setAttribute("user", username);

            // Redirect to the first report page (you can redirect anywhere)
            response.sendRedirect("billReport");      //made a change here ,i removed the .jsp
        } else {
            // If login fails, redirect back to login page with an error parameter
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
