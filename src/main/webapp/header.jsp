<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="menu-overlay"></div>

<nav id="side-menu">
    <button id="menu-close" aria-label="Close menu"><i class="fas fa-times"></i></button>
    <div class="side-menu-content">
        <a href="${pageContext.request.contextPath}/index.jsp">
            <img src="${pageContext.request.contextPath}/images/logohd.png" alt="East Out Hotel Logo" class="side-menu-logo">
        </a>
        <ul class="side-menu-nav">
            <li><a href="${pageContext.request.contextPath}/index.jsp" data-key="nav_home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/rooms.jsp" data-key="nav_rooms">Rooms</a></li>
            <li><a href="${pageContext.request.contextPath}/membership.jsp" data-key="nav_membership">Membership</a></li>
            <li><a href="#" data-key="nav_gallery">Gallery</a></li>
            <li><a href="#" data-key="nav_contact">Contact</a></li>
        </ul>
        <a href="${pageContext.request.contextPath}/booking.jsp" class="btn btn-book" data-key="book_now">Book Now</a>
    </div>
</nav>

<header class="minimal-header">
    <div class="container-fluid">
        <button class="menu-toggle" aria-label="Toggle Navigation">
            <i class="fas fa-bars"></i>
        </button>
        <div class="hotel-name-center">
            <a href="${pageContext.request.contextPath}/index.jsp">
                <img src="${pageContext.request.contextPath}/images/logohd.png" alt="East Out Hotel Logo" class="header-logo">
            </a>
        </div>
        <div class="header-right-nav">
            <button class="lang-selector" id="lang-toggle-btn">EN</button>
            <button id="dark-mode-toggle" aria-label="Toggle dark mode">
                <i class="fas fa-moon"></i> <i class="fas fa-sun"></i>
            </button>
            <a href="${pageContext.request.contextPath}/login.jsp" id="profile-icon-link" class="header-icon" aria-label="Login or Profile">
                <i class="fas fa-user-circle"></i>
            </a>
        </div>
    </div>
</header>