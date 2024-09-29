

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<footer class="footer_widgets">
    <div class="footer_top">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <div class="widgets_container contact_us">
                        <h3>Liên hệ chúng tôi</h3>
                        <div class="footer_contact">
                            <p>Address: 4/47 Lê Duẩn, Thành Phố Huế, Tỉnh Thừa Thiên Huế</p>
                            <p>Phone: <a href="tel:+(+84)12345678">(+84) 12345678</a> </p>
                            <p>Email: <a href="mailto:haiddhe176390@fpt.edu.vn">haiddhe176390@fpt.edu.vn</a></p>
                            <ul>
                                <li><a href="#" title="Twitter"><i class="fa fa-twitter"></i></a></li>
                                <li><a href="#" title="google-plus"><i class="fa fa-google-plus"></i></a></li>
                                <li><a href="#" title="facebook"><i class="fa fa-facebook"></i></a></li>
                                <li><a href="#" title="youtube"><i class="fa fa-youtube"></i></a></li>
                            </ul>

                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6">
                    <div class="widgets_container newsletter">
                        <h3>Nhận những thông tin sản phẩm mới </h3>
                        <div class="newleter-content">
                            <p>Chất lượng tạo nên thương hiệu !</p>
                            <div class="subscribe_form">
                                <form id="emailForm" method="POST" action="emailservlet">
                                    <input id="mc-email" type="email" name="EmailInfomation" autocomplete="off" placeholder="Enter you email address here..." />
                                    <button type="submit" >Đăng ký</button>
                                    
                                </form>
                            </div>
                            <div id="message"> </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
                        
 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    $("#emailForm").submit(function(event) {
        event.preventDefault(); // Prevent the form from submitting traditionally

        var to = $("#mc-email").val();
        var subject = "Đăng ký nhận thông tin về sản phẩm mới";
        var content = "Đăng ký thành công";

        $.ajax({
            type: "POST", // Use POST method to send data
            url: "emailservlet", // Replace with the URL that handles email sending
            data: { EmailInfomation: to, Subject: subject, Content: content },
            complete: function(res) { 
                if (res.status === 200) {
                    $("#message").text("Email has been sent successfully");
                } else {
                    $("#message").text("Failed to send email. Please try again.");
                }
            }
        });
    });
});
</script>
      
