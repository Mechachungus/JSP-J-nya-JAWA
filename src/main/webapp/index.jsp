<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>East Out Hotel - Home</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>

    <jsp:include page="header.jsp" />

    <div id="the-sun"></div>
    <div id="the-moon"></div>

    <main class="page-content">
        <section class="discover-section">
            <div class="container">
                <h2 data-key="discover_title">Discover East Out Hotel</h2>
                <p class="subtitle" data-key="discover_subtitle">Your adventure begins here</p>
                <p class="description" data-key="discover_desc">
                    Ready for a sun-drenched escape? Sitting at the heart of the mountains, our hotel is a lovingly developed Finca, blending rustic charm with modern luxury for an intimate, 27-bedroom experience.
                </p>
            </div>
        </section>

        <section class="content-grid-section">
            <div class="container">
                <div class="grid-row">
                    <div class="grid-text">
                        <h3 data-key="staying_title">Staying at Eastout</h3>
                        <p data-key="staying_desc">Choose from our 27 beautifully designed rooms...</p>
                        <a href="${pageContext.request.contextPath}/rooms.jsp" class="explore-link" data-key="explore_rooms">Explore Rooms <i class="fas fa-arrow-right"></i></a>
                    </div>
                    <div class="grid-image">
                        <img src="${pageContext.request.contextPath}/images/Hotel.jpg" alt="Hotel Room Interior">
                    </div>
                </div>
                
                <div class="grid-row row-reverse">
                    <div class="grid-text">
                        <h3 data-key="dining_title">Dining</h3>
                        <p data-key="dining_desc">Savour authentic local dishes...</p>
                    </div>
                    <div class="grid-image">
                        <img src="${pageContext.request.contextPath}/images/dining.jpg" alt="Restaurant Dining">
                    </div>
                </div>
            </div>
        </section>

        <section class="newsletter-section">
            <div class="container">
                <a href="#" data-key="newsletter_signup">Sign up to our newsletter <i class="fas fa-arrow-right"></i></a>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/translations.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>

</body>
</html>