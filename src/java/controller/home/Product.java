/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import DAO.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;

/**
 *
 * @author TGDD
 */
public class Product extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            ProductDAO c = new ProductDAO();
            List<model.Product> productList = c.getProduct();
            List<Category> category = c.getCategory();
            int page, numberpage = 9;// page lấy số trang , numberpage lấy sấố sản phấm trên 1 trang
            int size = productList.size();
            int num = (size % 9 == 0 ? (size / 9) : ((size / 9)) + 1);//số trang cần thiết dựa trên số sản phẩm và số sản phẩm trên mỗi trang
            String xpage = request.getParameter("page");
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            int start, end;
            start = (page - 1) * numberpage;
            end = Math.min(page * numberpage, size);
            List<model.Product> product = c.getListByPage(productList, start, end);
            request.setAttribute("page", page);
            request.setAttribute("num", num);
            request.setAttribute("CategoryData", category);
            request.setAttribute("ProductData", product);
            request.getRequestDispatcher("shop_category.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("listByCategory")) {
            String category_id = request.getParameter("category_id");
            int category_id1 = Integer.parseInt(category_id);
            ProductDAO c = new ProductDAO();
            List<model.Product> productList = c.getProductByCategory(category_id1);// lấy sản phẩm theo id của danh mục
            List<Category> category = c.getCategory();
            int page, numberpage = 9;
            int size = productList.size();
            int num = (size % 9 == 0 ? (size / 9) : ((size / 9)) + 1);//so trang
            String xpage = request.getParameter("page");
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            int start, end;
            start = (page - 1) * numberpage;
            end = Math.min(page * numberpage, size);
            List<model.Product> product = c.getListByPage(productList, start, end);
            request.setAttribute("page", page);
            request.setAttribute("num", num);
            request.setAttribute("CategoryData", category);
            request.setAttribute("ProductData", product);
            request.getRequestDispatcher("shop_category.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("productdetail")) {
            String product_id = request.getParameter("product_id");
            ProductDAO c = new ProductDAO();
            List<model.Size> sizeList = c.getSizeByID(product_id);
            List<model.Color> colorList = c.getColorByID(product_id);
            model.Product product = c.getProductByID(product_id); //lấy thông tin sản phẩm dựa trên product_id
            int category_id = product.getCate().getCategory_id();
            List<model.Product> productByCategory = c.getProductByCategory(category_id);// lấy danh sách sản phẩm dựa trên category_id
            request.setAttribute("ProductData", product);
            request.setAttribute("SizeData", sizeList);
            request.setAttribute("ColorData", colorList);
            request.setAttribute("ProductByCategory", productByCategory);
            request.getRequestDispatcher("product-details.jsp").forward(request, response);
        } else if (action.equals("sort")) {
            String type = request.getParameter("type");
            switch (type) {
                case "low":
                    {
                        ProductDAO c = new ProductDAO();
                        List<model.Product> productList = c.getProductLow();
                        List<Category> category = c.getCategory();
                        int page, numberpage = 9;
                        int size = productList.size();
                        int num = (size % 9 == 0 ? (size / 9) : ((size / 9)) + 1);//so trang
                        String xpage = request.getParameter("page");
                        if (xpage == null) {
                            page = 1;
                        } else {
                            page = Integer.parseInt(xpage);
                        }       int start, end;
                        start = (page - 1) * numberpage;
                        end = Math.min(page * numberpage, size);
                        List<model.Product> product = c.getListByPage(productList, start, end);
                        request.setAttribute("page", page);
                        request.setAttribute("num", num);
                        request.setAttribute("CategoryData", category);
                        request.setAttribute("ProductData", product);
                        request.getRequestDispatcher("shop_category.jsp").forward(request, response);
                        break;
                    }
                case "high":
                    {
                        ProductDAO c = new ProductDAO();
                        List<model.Product> productList = c.getProductHigh();
                        List<Category> category = c.getCategory();
                        int page, numberpage = 9;
                        int size = productList.size();
                        int num = (size % 9 == 0 ? (size / 9) : ((size / 9)) + 1);//so trang
                        String xpage = request.getParameter("page");
                        if (xpage == null) {
                            page = 1;
                        } else {
                            page = Integer.parseInt(xpage);
                        }       int start, end;
                        start = (page - 1) * numberpage;
                        end = Math.min(page * numberpage, size);
                        List<model.Product> product = c.getListByPage(productList, start, end);
                        request.setAttribute("page", page);
                        request.setAttribute("num", num);
                        request.setAttribute("CategoryData", category);
                        request.setAttribute("ProductData", product);
                        request.getRequestDispatcher("shop_category.jsp").forward(request, response);
                        break;
                    }
                case "a-z":
                    {
                        ProductDAO c = new ProductDAO();
                        List<model.Product> productList = c.getProductAZ();
                        List<Category> category = c.getCategory();
                        int page, numberpage = 9;
                        int size = productList.size();
                        int num = (size % 9 == 0 ? (size / 9) : ((size / 9)) + 1);//so trang
                        String xpage = request.getParameter("page");
                        if (xpage == null) {
                            page = 1;
                        } else {
                            page = Integer.parseInt(xpage);
                        }       int start, end;
                        start = (page - 1) * numberpage;
                        end = Math.min(page * numberpage, size);
                        List<model.Product> product = c.getListByPage(productList, start, end);
                        request.setAttribute("page", page);
                        request.setAttribute("num", num);
                        request.setAttribute("CategoryData", category);
                        request.setAttribute("ProductData", product);
                        request.getRequestDispatcher("shop_category.jsp").forward(request, response);
                        break;
                    }
                default:
                    break;
            }
        } else if (action.equals("search")) {
            String text = request.getParameter("text");
            ProductDAO c = new ProductDAO();
            List<model.Product> productList = c.SearchAll(text);
            List<Category> category = c.getCategory();
            int page, numberpage = 9;
            int size = productList.size();
            int num = (size % 9 == 0 ? (size / 9) : ((size / 9)) + 1);//so trang
            String xpage = request.getParameter("page");
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            int start, end;
            start = (page - 1) * numberpage;
            end = Math.min(page * numberpage, size);
            List<model.Product> product = c.getListByPage(productList, start, end);
            request.setAttribute("page", page);
            request.setAttribute("num", num);
            request.setAttribute("CategoryData", category);
            request.setAttribute("ProductData", product);
            request.getRequestDispatcher("shop_category.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
