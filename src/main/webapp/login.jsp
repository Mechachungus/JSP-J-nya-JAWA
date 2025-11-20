<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - East Out Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body class="page-no-scroll">

    <jsp:include page="header.jsp" />

    <main class="page-content">
        <section class="discover-section auth-panel">
            <h2 class="room-page-title" data-key="login_title">Login</h2>
            
            <form action="${pageContext.request.contextPath}/login" method="POST">
                
                <% if(request.getAttribute("errorMessage") != null) { %>
                    <p style="color: red; text-align: center;"><%= request.getAttribute("errorMessage") %></p>
                <% } %>

                <div class="form-group">
                    <label for="username" data-key="login_username">Username (or Email)</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password" data-key="login_password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-book btn-full-width" data-key="login_title">Login</button>
            </form>

            <p class="auth-switch">
                <span data-key="login_no_account">Don't have an account?</span> 
                <a href="${pageContext.request.contextPath}/register.jsp" data-key="login_register_here">Register here</a>
            </p>
        </section>
    </main>
    
    <jsp:include page="footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/translation.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    
</body>
</html>