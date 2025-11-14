package com.example.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String pass = req.getParameter("password");

        // demo authentication
        if (email.equals("test@gmail.com") && pass.equals("123")) {
            resp.sendRedirect("index.jsp");
        } else {
            resp.sendRedirect("login.jsp");
        }
    }
}
