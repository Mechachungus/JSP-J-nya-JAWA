package com.example.controllers;

import com.example.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// This annotation tells the server: "When the form posts to /login, use this class"
@WebServlet("/login") 
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1. MATCH THESE STRINGS TO YOUR JSP name="" attributes
        String usernameInput = req.getParameter("username"); 
        String passwordInput = req.getParameter("password");

        // 2. Authenticate (Simulated)
        if (usernameInput != null && !usernameInput.isEmpty() && passwordInput.equals("123")) {
            
            // Create a dummy user object (Since we don't have a DB yet)
            User loggedInUser = new User(
                usernameInput, 
                "John Doe",             // Fake Name
                usernameInput + "@test.com", 
                "08123456789", 
                "Male", 
                "1990-01-01", 
                "customer"
            );

            // 3. CRITICAL STEP: Create the Session
            HttpSession session = req.getSession();
            session.setAttribute("user", loggedInUser); // This makes ${sessionScope.user} available in JSP

            // 4. Redirect to the profile/history page
            resp.sendRedirect(req.getContextPath() + "/history.jsp");

        } else {
            // Login Failed
            req.setAttribute("errorMessage", "Invalid credentials. Try 'admin' and '123'");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}