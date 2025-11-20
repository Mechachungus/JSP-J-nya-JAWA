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
    <jsp:include page="header.jsp" />

    <div id="the-sun"></div>
    <div id="the-moon"></div>
    
    <main class="page-content">
        <section class="discover-section auth-panel" style="max-width: 800px;">
            <h2 class="room-page-title" id="welcome-heading">Welcome, ${sessionScope.user.username}!</h2>
            
            <div class="tabs">
                <button class="tab-link active" data-tab="details" data-key="profile_tab_details">My Details</button>
                <button class="tab-link" data-tab="bookings" data-key="profile_tab_bookings">My Bookings</button>
            </div>

            <div id="details" class="tab-content active">
                <div class="profile-details">
                    <h3 data-key="profile_your_details">Your Details</h3>
                    <div class="form-group">
                        <label data-key="profile_username">Username</label>
                        <input type="text" value="${sessionScope.user.username}" readonly>
                    </div>
                    <div class="form-group">
                        <label data-key="profile_full_name">Full Name</label>
                        <input type="text" value="${sessionScope.user.fullName}" readonly>
                    </div>
                    <div class="form-group">
                        <label data-key="profile_email">Email Address</label>
                        <input type="email" value="${sessionScope.user.email}" readonly>
                    </div>
                    <div class="form-group">
                        <label data-key="profile_phone">Phone Number</label>
                        <input type="tel" value="${sessionScope.user.phoneNumber}" readonly>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label data-key="profile_gender">Gender</label>
                            <input type="text" value="${sessionScope.user.gender}" readonly>
                        </div>
                        <div class="form-group">
                            <label data-key="profile_birthdate">Birthdate</label>
                            <input type="text" value="${sessionScope.user.birthDate}" readonly>
                        </div>
                    </div>
                </div>
            </div>

            <div id="bookings" class="tab-content">
                <div class="order-history-list">
                    <h3 data-key="profile_order_history">Order History</h3>
                    
                    <div class="order-item">
                        <div class="order-item-details">
                            <strong data-key="room_luxury">Luxury Villa</strong>
                            <span data-key="profile_upcoming">Upcoming: Nov 15, 2025 - Nov 18, 2025</span>
                        </div>
                        <div class="order-item-price">$2100.00</div>
                    </div>
                    
                    <div class="order-item">
                        <div class="order-item-details">
                            <strong data-key="room_deluxe">Deluxe Room</strong>
                            <span data-key="profile_completed">Completed: Sep 10, 2025 - Sep 12, 2025</span>
                        </div>
                        <div class="order-item-actions">
                            <a href="${pageContext.request.contextPath}/review.jsp?transaction=123" class="btn-review" data-key="profile_leave_review">Leave a Review</a>
                        </div>
                    </div>
                    
                    <div class="order-item">
                        <div class="order-item-details">
                            <strong data-key="room_executive">Executive Suite</strong>
                            <span data-key="profile_completed">Completed: Jul 01, 2025 - Jul 05, 2025</span>
                        </div>
                        <div class="order-item-actions">
                            <span class="review-submitted" data-key="profile_review_submitted"><i class="fas fa-check"></i> Review Submitted</span>
                        </div>
                    </div>

                </div>
            </div>

            <form action="${pageContext.request.contextPath}/logout" method="GET" style="margin-top: 20px;">
                <button type="submit" class="btn btn-book btn-full-width" data-key="profile_logout">Logout</button>
            </form>
        </section>
    </main>
    
    <jsp:include page="footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/translation.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const tabLinks = document.querySelectorAll('.tab-link');
            const tabContents = document.querySelectorAll('.tab-content');

            tabLinks.forEach(link => {
                link.addEventListener('click', () => {
                    const tabId = link.getAttribute('data-tab');

                    // Deactivate all
                    tabLinks.forEach(item => item.classList.remove('active'));
                    tabContents.forEach(item => item.classList.remove('active'));

                    // Activate clicked
                    link.classList.add('active');
                    document.getElementById(tabId).classList.add('active');
                });
            });
        });
    </script>
</body>
</html>