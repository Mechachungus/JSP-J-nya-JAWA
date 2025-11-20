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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
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
        
        // Tampilkan halaman register
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Ambil data dari form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");
        String birthDate = request.getParameter("birthDate");
        
        // Validasi input kosong
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty() ||
            phoneNumber == null || phoneNumber.trim().isEmpty() ||
            gender == null || gender.trim().isEmpty() ||
            birthDate == null || birthDate.trim().isEmpty()) {
            
            request.setAttribute("error", "Semua field harus diisi!");
            forwardWithData(request, response);
            return;
        }
        
        // Validasi username length (3-20 karakter)
        if (username.length() < 3 || username.length() > 20) {
            request.setAttribute("error", "Username harus 3-20 karakter!");
            forwardWithData(request, response);
            return;
        }
        
        // Validasi password length (6-20 karakter)
        if (password.length() < 6 || password.length() > 20) {
            request.setAttribute("error", "Password harus 6-20 karakter!");
            forwardWithData(request, response);
            return;
        }
        
        // Validasi konfirmasi password
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password tidak cocok!");
            forwardWithData(request, response);
            return;
        }
        
        // Validasi phone number (harus angka dan 10-16 digit)
        if (!phoneNumber.matches("\\d{10,16}")) {
            request.setAttribute("error", "Nomor telepon harus 10-16 digit angka!");
            forwardWithData(request, response);
            return;
        }
        
        // Validasi gender (harus M atau F)
        if (!gender.equals("M") && !gender.equals("F")) {
            request.setAttribute("error", "Gender tidak valid!");
            forwardWithData(request, response);
            return;
        }
        
        // Validasi full name length
        if (fullName.length() > 50) {
            request.setAttribute("error", "Nama lengkap maksimal 50 karakter!");
            forwardWithData(request, response);
            return;
        }
        
        // Cek apakah username sudah digunakan
        if (userDAO.isUsernameExists(username)) {
            request.setAttribute("error", "Username sudah digunakan! Silakan pilih username lain.");
            forwardWithData(request, response);
            return;
        }
        
        // Buat object User baru
        User newUser = new User(username, fullName, phoneNumber, gender, birthDate, "Customer");
        
        // Register user ke database
        boolean success = userDAO.registerUser(newUser, password);
        
        if (success) {
            // Registrasi berhasil - Redirect ke login dengan pesan sukses
            response.sendRedirect("login.jsp?success=registered");
        } else {
            // Registrasi gagal - Kembali ke register dengan error
            request.setAttribute("error", "Gagal mendaftar. Silakan coba lagi.");
            forwardWithData(request, response);
        }
    }
    
    // Helper method untuk forward dengan preserve data form
    private void forwardWithData(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Preserve input data agar user tidak perlu input ulang
        request.setAttribute("username", request.getParameter("username"));
        request.setAttribute("fullName", request.getParameter("fullName"));
        request.setAttribute("phoneNumber", request.getParameter("phoneNumber"));
        request.setAttribute("gender", request.getParameter("gender"));
        request.setAttribute("birthDate", request.getParameter("birthDate"));
        
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}