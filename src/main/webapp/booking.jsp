<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Your Stay - East Out Hotel</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/litepicker/dist/css/litepicker.css"/>
    <script src="https://cdn.jsdelivr.net/npm/litepicker/dist/litepicker.js"></script>
</head>
<body class="page-no-scroll">

    <jsp:include page="header.jsp" />

    <main class="page-content">
        <section class="content-grid-section">
            <div class="container">
                <h2 class="room-page-title" data-key="booking_title">Complete Your Reservation</h2>
                <div class="booking-layout">
                    
                    <div class="booking-form">
                        <form id="reservation-form" action="${pageContext.request.contextPath}/bookRoom" method="POST">
                            <h3 data-key="booking_your_details">Your Details</h3>
                            
                            <div class="form-group">
                                <label for="full-name" data-key="booking_full_name">Full Name</label>
                                <input type="text" id="full-name" name="full-name" value="${sessionScope.user.fullName}" required>
                            </div>
                            <div class="form-group">
                                <label for="phone" data-key="booking_phone">Phone Number</label>
                                <input type="tel" id="phone" name="phone" value="${sessionScope.user.phoneNumber}">
                            </div>
                            
                            <h3 data-key="booking_select_dates">Select Dates</h3>
                            <div class="form-group">
                                <label for="date-picker" data-key="booking_checkin_checkout">Check-In & Check-Out</label>
                                <input type="text" id="date-picker" name="date-range" required>
                            </div>
                            
                            <h3 data-key="booking_payment_details">Payment Details</h3>
                            <div class="form-group">
                                <label for="card-name" data-key="booking_card_name">Name on Card</label>
                                <input type="text" id="card-name" name="card-name" required>
                            </div>
                            <div class="form-group">
                                <label for="card-number" data-key="booking_card_number">Card Number</label>
                                <input type="text" id="card-number" name="card-number" required>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="card-expiry" data-key="booking_card_expiry">Expiry (MM/YY)</label>
                                    <input type="text" id="card-expiry" name="card-expiry" placeholder="MM/YY" required>
                                </div>
                                <div class="form-group">
                                    <label for="card-cvc" data-key="booking_card_cvc">CVC</label>
                                    <input type="text" id="card-cvc" name="card-cvc" placeholder="123" required>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-book btn-full-width" data-key="booking_confirm_pay">Confirm & Pay</button>
                        </form>
                    </div>
                    
                    <div class="booking-summary">
                        <h3 data-key="booking_your_reservation">Your Reservation</h3>
                        <div class="summary-item">
                            <label for="room-type-select" data-key="booking_room_type">Room Type:</label>
                            <select id="room-type-select" name="room-type" class="summary-select">
                                <option value="" data-key="booking_select_room">-- Select a room --</option>
                                <option value="deluxe" data-key="room_deluxe">Deluxe Room</option>
                                <option value="executive" data-key="room_executive">Executive Suite</option>
                                <option value="luxury" data-key="room_luxury">Luxury Villa</option>
                            </select>
                        </div>
                        <div class="summary-item">
                            <span data-key="booking_price_night">Price per night:</span>
                            <strong id="selected-room-price">-</strong>
                        </div>
                        <div class="summary-divider"></div>
                        <div class="summary-item total">
                            <span data-key="booking_total">Estimated Total:</span>
                            <strong id="selected-room-total">-</strong>
                        </div>
                        <p class="summary-note" data-key="booking_note">The final price will be calculated based on your selected dates and any applicable taxes.</p>
                    </div>
                </div>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />

    <script src="${pageContext.request.contextPath}/js/translations.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        // 1. Define Room Data & Select Elements
        const roomData = {
            'deluxe': { name: 'Deluxe Room', price: 1500000 },
            'executive': { name: 'Executive Suite', price: 2500000 },
            'luxury': { name: 'Luxury Villa', price: 4000000 }
        };
        
        const roomSelect = document.getElementById('room-type-select');
        const priceElement = document.getElementById('selected-room-price');
        const totalElement = document.getElementById('selected-room-total');
        const datePickerInput = document.getElementById('date-picker'); 
        
        let picker = null; 
        let numberOfNights = 0; 
        let currentRoomType = roomSelect.value; 

        // Helper Functions
        function formatRupiah(number) {
            if (isNaN(number) || number === null || number <= 0) return '-';
            return 'Rp' + number.toLocaleString('id-ID'); 
        }

        function calculateAndUpdateDisplay() {
            const roomType = currentRoomType;
            if (roomType && roomData[roomType]) {
                const room = roomData[roomType];
                priceElement.textContent = formatRupiah(room.price);

                if (numberOfNights > 0) {
                    const totalPrice = room.price * numberOfNights;
                    totalElement.textContent = formatRupiah(totalPrice);
                } else {
                    totalElement.textContent = '-';
                }
            } else {
                priceElement.textContent = '-';
                totalElement.textContent = '-';
            }
        }

        // Fake locked dates logic (In real app, this comes from Java/Database)
        function getLockedDatesForRoom(roomType) {
            const fakeBookedDates = {
                'deluxe': ['2025-11-10', '2025-11-11', '2025-11-18', '2025-11-19', '2025-11-20'],
                'executive': ['2025-11-12', '2025-11-13'],
                'luxury': ['2025-11-15']
            };
            return Promise.resolve(roomType ? (fakeBookedDates[roomType] || []) : []);
        }

        function initializeLitepicker(roomType) {
            if (picker) {
                picker.destroy();
                picker = null;
                numberOfNights = 0;
                datePickerInput.value = '';
                calculateAndUpdateDisplay();
            }
            
            if (!roomType) {
                datePickerInput.disabled = true;
                return; 
            } else {
                 datePickerInput.placeholder = '';
                 datePickerInput.disabled = false;
            }

            getLockedDatesForRoom(roomType).then(lockedDays => {
                if (!picker) { 
                    picker = new Litepicker({
                        element: datePickerInput, 
                        singleMode: false,
                        allowRepick: true,
                        format: 'MMM D, YYYY',
                        minDate: new Date(),
                        lockDays: lockedDays,
                        tooltipText: { one: 'night', other: 'nights' },
                        setup: (pickerInstance) => {
                            pickerInstance.on('tooltip', (tooltip, day) => {
                                if (day.isLocked) tooltip.innerHTML = 'Sorry, this date is unavailable';
                            });

                            pickerInstance.on('selected', (date1, date2) => {
                                if (date1 && date2) {
                                    const startDate = date1.dateInstance;
                                    const endDate = date2.dateInstance;
                                    const diffTime = Math.abs(endDate - startDate);
                                    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 
                                    numberOfNights = diffDays > 0 ? diffDays : 0;
                                } else {
                                    numberOfNights = 0; 
                                }
                                calculateAndUpdateDisplay();
                            });
                        }
                    });
                }
            }).catch(error => {
                console.error("Error:", error);
                datePickerInput.disabled = true;
            });
        }

        roomSelect.addEventListener('change', function() {
            currentRoomType = this.value;
            numberOfNights = 0;
            calculateAndUpdateDisplay();
            initializeLitepicker(currentRoomType);
        });

        const params = new URLSearchParams(window.location.search);
        const urlRoom = params.get('room');
        
        if (urlRoom && roomData[urlRoom]) {
            roomSelect.value = urlRoom;
            roomSelect.disabled = true; 
            currentRoomType = urlRoom;
        } else {
            currentRoomType = roomSelect.value;
        }
        
        calculateAndUpdateDisplay();
        initializeLitepicker(currentRoomType);
    });
    </script>
</body>
</html>