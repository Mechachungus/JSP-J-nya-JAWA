<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Our Rooms - East Out Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
    <jsp:include page="header.jsp" />

    <div id="the-sun"></div>
    <div id="the-moon"></div>
    
    <main class="page-content">
        <section class="content-grid-section">
            <div class="container">
                <h2 class="room-page-title" data-key="rooms_title">Our Rooms</h2>
                <div class="room-listing">
                    <div class="room-type-card">
                        <img src="${pageContext.request.contextPath}/images/Deluxe.jpg" alt="Deluxe Room">
                        <div class="room-type-details">
                            <h3 data-key="room_deluxe">Deluxe Room</h3>
                            <div class="room-meta" data-key="room_deluxe_meta">Size: 35 sqm / 376 sqft</div>
                            <p data-key="room_deluxe_desc">Our Deluxe Rooms offer a perfect blend of comfort and style, featuring a plush king-sized bed, a modern bathroom, and serene views of the surrounding landscape.</p>
                            <a href="${pageContext.request.contextPath}/booking.jsp?room=deluxe" class="btn btn-book" data-key="book_now">Book Now</a>
                        </div>
                    </div>
                    <div class="room-type-card">
                        <img src="${pageContext.request.contextPath}/images/Executive.jpg" alt="Executive Suite">
                        <div class="room-type-details">
                            <h3 data-key="room_executive">Executive Suite</h3>
                            <div class="room-meta" data-key="room_executive_meta">Size: 50 sqm / 538 sqft</div>
                            <p data-key="room_executive_desc">Ideal for business or leisure, the Executive Suite provides a spacious living area, a private workstation, and exclusive access to our club lounge.</p>
                            <a href="${pageContext.request.contextPath}/booking.jsp?room=executive" class="btn btn-book" data-key="book_now">Book Now</a>
                        </div>
                    </div>
                    <div class="room-type-card">
                        <img src="${pageContext.request.contextPath}/images/Luxurious.jpg" alt="Luxury Villa">
                        <div class="room-type-details">
                            <h3 data-key="room_luxury">Luxury Villa</h3>
                            <div class="room-meta" data-key="room_luxury_meta">Size: 75 sqm / 807 sqft</div>
                            <p data-key="room_luxury_desc">Experience ultimate privacy in our Luxury Villa, featuring a private plunge pool, an expansive terrace, and personalized butler service for an unforgettable stay.</p>
                            <a href="${pageContext.request.contextPath}/booking.jsp?room=luxury" class="btn btn-book" data-key="book_now">Book Now</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/translation.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>