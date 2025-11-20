<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - East Out Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
    <jsp:include page="header.jsp" />

    <div id="the-sun"></div>
    <div id="the-moon"></div>
    
    <main class="page-content">
        <section class="discover-section auth-panel">
            <h2 class="room-page-title" data-key="register_title">Create Account</h2>
            
            <form action="${pageContext.request.contextPath}/register" method="POST">
                <div class="form-group">
                    <label for="username" data-key="register_username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="full-name" data-key="register_full_name">Full Name</label>
                    <input type="text" id="full-name" name="fullName" required>
                </div>
                 <div class="form-group">
                    <label for="phone" data-key="register_phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" required>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="gender" data-key="register_gender">Gender</label>
                        <select id="gender" name="gender" class="summary-select" style="width: 100%; max-width: 100%;">
                            <option value="" data-key="register_gender_select">Select...</option>
                            <option value="male" data-key="register_gender_male">Male</option>
                            <option value="female" data-key="register_gender_female">Female</option>
                        </select required>
                    </div>
                    <div class="form-group">
                        <label for="birthdate" data-key="register_birthdate">Birthdate</label>
                        <input type="date" id="birthdate" name="birthdate" required>
                    </div>
                </div>
                <input type="hidden" name="accountType" value="customer">

                <div class="form-group">
                    <label for="password" data-key="register_password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="re-password" data-key="register_re_password">Re-enter Password</label>
                    <input type="password" id="re-password" name="rePassword" required>
                </div>
                <button type="submit" class="btn btn-book btn-full-width" data-key="register_button">Register</button>
            </form>

           <p class="auth-switch">
                <span data-key="register_have_account">Already have an account?</span> 
                <a href="${pageContext.request.contextPath}/login.jsp" data-key="register_login_here">Login here</a>
            </p>
        </section>
    </main>
    
    <jsp:include page="footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/translation.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>