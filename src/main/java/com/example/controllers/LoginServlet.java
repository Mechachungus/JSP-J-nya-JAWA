package com.example.controllers;

import com.example.dao.UserDAO;
import com.example.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Jika user sudah login, redirect ke index
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        // Tampilkan halaman login
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Ambil data dari form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validasi input kosong
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            
            request.setAttribute("error", "Username dan Password harus diisi!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Proses login menggunakan UserDAO
        User user = userDAO.loginUser(username, password);
        
        if (user != null) {
            // Login berhasil - Simpan data user ke session
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
            session.setAttribute("customerID", user.getCustomerID());
            session.setAttribute("accountID", user.getAccountID());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("fullName", user.getFullName());
            session.setAttribute("accountType", user.getAccountType());
            session.setAttribute("membershipID", user.getMembershipID());
            
            // Set session timeout (30 menit)
            session.setMaxInactiveInterval(30 * 60);
            
            // Redirect berdasarkan tipe account
            if ("admin".equalsIgnoreCase(user.getAccountType())) {
                response.sendRedirect("admin/dashboard.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }
        } else {
            // Login gagal - Kembali ke login dengan error message
            request.setAttribute("error", "Username atau Password salah!");
            request.setAttribute("username", username); // Preserve username
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}