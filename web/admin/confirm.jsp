

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Product"%>
<%@page import="model.Category"%>
<%@page import="model.Size"%>
<%@page import="model.Color"%>
<%@page import="java.util.*"%>
<jsp:useBean id="product" class="model.Product" scope="session" />
<jsp:setProperty name="product" property="product_id" />
<jsp:setProperty name="product" property="product_name" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="admin/css/main.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="css/confirm.css"/>
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="http://code.jquery.com/jquery.min.js" type="text/javascript"></script>
    </head>
    <%              try{
        String category_id = request.getParameter("category_id");                   
                    String product_price = request.getParameter("price");
                    String product_size = request.getParameter("size");
                    String product_color = request.getParameter("color");
                    String product_quantity = request.getParameter("quantity");                                     
                    String product_img = "images/" + request.getParameter("product_img");
                    String product_describe = request.getParameter("describe");
                    int quantity = Integer.parseInt(product_quantity);
                    Float price = Float.parseFloat(product_price);
                    int cid = Integer.parseInt(category_id);
                    Category cate = new Category(cid);
                    String[] size_rw = product_size.split("\\s*,\\s*");
                    String[] color_rw = product_color.split("\\s*,\\s*");
                    int[] size = new int[size_rw.length];
                    int[] color = new int[color_rw.length];
                    //size
                    List<Size> list = new ArrayList<>();
                    try {
                        for (int i = 0; i < size.length; i++) {
                            Size s = new Size(product.getProduct_id(), size_rw[i]);
                            list.add(s);
                        }
                    } catch (Exception e) {
                    }
                    // color
                    List<Color> list2 = new ArrayList<>();
                    try {
                        for (int i = 0; i < color.length; i++) {
                            Color s1 = new Color(product.getProduct_id(), color_rw[i]);
                            list2.add(s1);
                        }
                    } catch (Exception e) {
                    }
                    product.setCate(cate);
                    product.setProduct_price(price);
                    product.setQuantity(quantity);
                    product.setProduct_describe(product_describe);
                    product.setImg(product_img);
                    product.setSize(list);
                    product.setColor(list2);
    %>
    <body>
        <main class="app-content">
            <form class="row" action="../productmanager?action=insertproduct" method="POST">
                <div class="form-group col-md-3">
                    <table>
                        <tr>
                            <td>Mã sản phẩm</td>
                            <td><c:out value="${product.product_id}"/></td>
                        </tr>
                    </table>
                </div>
                <div class="form-group col-md-3">                  
                    <table>
                        <tr>
                            <td>Số Danh Mục</td>
                            <td><c:out value="${product.cate.category_id}"/></td>
                        </tr>
                    </table>
                </div>
                <div class="form-group col-md-3">
                    <table>
                        <tr>
                            <td>Tên Sản Phẩm</td>
                            <td><c:out value="${product.product_name}"/></td>
                        </tr>
                    </table>
                </div>
                <div class="form-group  col-md-3">
                    <table>
                        <tr>
                            <td>Giá Bán</td>
                            <td><c:out value="${product.product_price}"/></td>
                        </tr>
                    </table>
                </div>
                <div class="form-group col-md-3">
                    <table>
                        <tr>
                            <td>Size</td>
                        <c:forEach items="${product.size}" var="list">
                            <td><c:out value="${list.size}"/></td>
                        </c:forEach>
                        </tr>
                    </table>

                </div>
                <div class="form-group col-md-3">
                    <table>
                        <tr>
                            <td>Color</td>
                        <c:forEach items="${product.color}" var="list1">
                            <td><c:out value="${list1.color}"/></td>
                        </c:forEach>
                        </tr>
                    </table>
                </div>
                <div class="form-group  col-md-3">
                    <table>
                        <tr>
                            <td>Số Lượng</td>
                            <td><c:out value="${product.quantity}"/></td>
                        </tr>
                    </table>
                </div>
                <div class="form-group col-md-12">
                    <table>
                        <tr>
                            <td>Hình Ảnh Sản Phẩm</td>
                            <td><img src="../${product.img}" alt="alt"/></td>
                        </tr>
                    </table>
                </div>
                <div class="form-group col-md-12">
                      <table>
                        <tr>
                            <td>Mô Tả Sản Phẩm</td>
                            <td><c:out value="${product.product_describe}"/></td>
                        </tr>
                    </table>
                </div>
                <button class="btn btn-save" type="submit">Lưu lại</button>
                &nbsp;
                <a class="btn btn-cancel" href="../productmanager">Hủy bỏ</a>
            </form>
        </div>

    </div>
</div>
</div>
<% } catch(Exception e){
response.sendRedirect("http://localhost:8080/shop/home");
}%>
</main>
</body>
</html>
