package com.example.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Fetch the session if it exists
        HttpSession session = req.getSession(false);
        
        // 2. Kill the session
        if (session != null) {
            session.invalidate();
        }

        // 3. Go back to home page
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}