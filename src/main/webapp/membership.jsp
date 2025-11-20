<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Membership - East Out Hotel</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
    <jsp:include page="header.jsp" />

    <div id="the-sun"></div>
    <div id="the-moon"></div>
    
    <main class="page-content">
        <section class="discover-section">
            <h2 class="room-page-title" data-key="membership_title">Our Membership</h2>
            <p class="description" style="max-width: 600px; margin: 0 auto 40px;" data-key="membership_subtitle">
                Join our exclusive membership program to enjoy discounts and special benefits on every stay.
            </p>

            <div class="membership-grid">
                <div class="membership-card">
                    <h3 data-key="membership_basic_name">Basic</h3>
                    <div class="membership-price" data-key="membership_basic_price">Free</div> 
                    <div class="membership-discount" data-key="membership_basic_discount">0% Discount</div>
                    <p data-key="membership_basic_desc">For anyone who has EastOut account. You can access all features of the web without any additional benefits and upgradable level.</p>
                    <p class="membership-note" data-key="membership_basic_note">Default membership for all customers.</p> 
                </div>

                <div class="membership-card active"> 
                    <h3 data-key="membership_vip_name">VIP</h3>
                    <div class="membership-price">Rp5.000.000</div> 
                    <div class="membership-discount" data-key="membership_vip_discount">25% Discount</div>
                    <p data-key="membership_vip_desc">For you, our best friend. You will get access to a special discount of 25% for every transaction. You will also get a special present for your birthday.</p>
                    <a href="#" class="btn btn-book" data-key="join_now">Join Now</a>
                </div>

                <div class="membership-card">
                    <h3 data-key="membership_vvip_name">VVIP</h3>
                    <div class="membership-price">Rp9.000.000</div> 
                    <div class="membership-discount" data-key="membership_vvip_discount">50% Discount</div>
                    <p data-key="membership_vvip_desc">For you, our family. You will get access to a special discount of 50% for every transaction. You can have free meal course twice a week. You will also get a special present for your birthday.</p>
                    <a href="#" class="btn btn-book" data-key="join_now">Join Now</a>
                </div>
            </div>
        </section>
    </main>
    
    <jsp:include page="footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/translation.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>

</body>
</html>
