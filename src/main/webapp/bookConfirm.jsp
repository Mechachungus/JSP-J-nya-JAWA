<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reservation Confirmed - East Out Hotel</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>

<jsp:include page="header.jsp" />

<main class="page-content">
    <section class="content-grid-section">
        <div class="container">

            <div class="confirmation-wrapper">
                <h2 class="room-page-title" style="text-align:center;" data-key="confirm_title">
                    <i class="fas fa-check-circle" style="color:#2ecc71; margin-right:10px;"></i>
                    Reservation Successful!
                </h2>

                <p style="text-align:center; max-width:650px; margin:0 auto 30px; font-size:1.1rem;">
                    <span data-key="confirm_thank_you">Thank you,</span>
                    <strong>${empty sessionScope.user.fullName ? param['full-name'] : sessionScope.user.fullName}</strong>
                    <span data-key="confirm_thank_you_suffix">! Your stay at East Out Hotel has been successfully booked.</span>
                </p>

                <div class="booking-summary" style="margin: 0 auto; max-width: 600px;">
                    <h3 data-key="confirm_booking_details">Your Booking Details</h3>

                    <div class="summary-item">
                        <span data-key="confirm_guest_name">Guest Name:</span>
                        <strong>${empty sessionScope.user.fullName ? param['full-name'] : sessionScope.user.fullName}</strong>
                    </div>

                    <div class="summary-item">
                        <span data-key="confirm_phone">Phone Number:</span>
                        <strong>${param.phone}</strong>
                    </div>

                    <div class="summary-item">
                        <span data-key="confirm_total_cost">Total Cost:</span>
                        <strong>${param['total-cost'] != null ? param['total-cost'] : param['room-type']}</strong>
                    </div>

                    <div class="summary-item">
                        <span data-key="confirm_dates">Dates:</span>
                        <strong>${param['date-range']}</strong>
                    </div>

                    <div class="summary-item">
                        <span data-key="confirm_payment_method">Payment Method:</span>
                        <strong>${param['payment-method']}</strong>
                    </div>

                    <div class="summary-divider"></div>

                    <p class="summary-note" data-key="confirm_email_note">
                        A confirmation email has been sent to your registered email address.
                        We look forward to welcoming you!
                    </p>
                </div>

                <div style="text-align:center; margin-top:40px;">
                    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-book" data-key="confirm_back_home">
                        Back to Home
                    </a>
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