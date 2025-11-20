<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - East Out Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body class="page-no-scroll">

    <jsp:include page="header.jsp" />
    
    <main class="page-content">
        <section class="discover-section auth-panel">
            <h2 class="room-page-title" id="welcome-heading">Welcome, ${sessionScope.user.username}!</h2>
            
            <div class="profile-details">
                <h3>Your Details</h3>
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" value="${sessionScope.user.username}" readonly>
                </div>
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" value="${sessionScope.user.fullName}" readonly>
                </div>
                <div class="form-group">
                    <label>Email Address</label>
                    <input type="email" value="${sessionScope.user.email}" readonly>
                </div>
                <div class="form-group">
                    <label>Phone Number</label>
                    <input type="tel" value="${sessionScope.user.phoneNumber}" readonly>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label>Gender</label>
                        <input type="text" value="${sessionScope.user.gender}" readonly>
                    </div>
                    <div class="form-group">
                        <label>Birthdate</label>
                        <input type="text" value="${sessionScope.user.birthDate}" readonly>
                    </div>
                </div>
            </div>
            
            <form action="${pageContext.request.contextPath}/logout" method="GET">
                <button type="submit" id="logout-button" class="btn btn-book btn-full-width">Logout</button>
            </form>
        </section>
    </main>
    
    <jsp:include page="footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/translation.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>