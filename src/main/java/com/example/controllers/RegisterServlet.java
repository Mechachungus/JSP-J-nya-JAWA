package com.example.controllers;

import com.example.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1. Get data from register.jsp form
        String username = req.getParameter("username");
        String fullName = req.getParameter("fullName"); // Make sure name="fullName" in JSP
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String gender = req.getParameter("gender");
        String birthDate = req.getParameter("birthdate");
        String accountType = "customer"; // Default

        // 2. Create User Object
        User newUser = new User(username, fullName, email, phone, gender, birthDate, accountType);

        // 3. Save to Session (Log them in)
        HttpSession session = req.getSession();
        session.setAttribute("user", newUser);

        // 4. Redirect to Profile
        resp.sendRedirect(req.getContextPath() + "/history.jsp");
    }
}