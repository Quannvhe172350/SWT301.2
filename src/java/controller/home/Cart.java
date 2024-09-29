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
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Item;

/**
 *
 * @author TGDD
 */
public class Cart extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");

        if (action == null) {

            model.Cart cart = null;
            Object o = session.getAttribute("cart");
            if (o != null) {
                cart = (model.Cart) o;
            } else {
                cart = new model.Cart();
            }
            String Squantity = request.getParameter("quantity");
            String product_id = request.getParameter("product_id");
            String size = request.getParameter("size");
            String color = request.getParameter("color");

            try {
                int quanity = Integer.parseInt(Squantity);
                ProductDAO pdao = new ProductDAO();
                model.Product product = pdao.getProductByID(product_id); //lấy thông tin về sản phẩm dựa trên id
                Item item = new Item(product, quanity, size, color);
                cart.addItem(item);

            } catch (Exception e) {
            }
            List<Item> list = cart.getItems();
            session.setAttribute("cart", cart);
            session.setAttribute("total", cart.getTotalMoney());
            session.setAttribute("size", list.size());
            request.getRequestDispatcher("product?action=productdetail&product_id=" + product_id).forward(request, response);
        } else if (action.equalsIgnoreCase("showcart")) {
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } else if (action.equals("deletecart")) {
            model.Cart cart = null;
            Object o = session.getAttribute("cart");
            if (o != null) {
                cart = (model.Cart) o;
            } else {
                cart = new model.Cart();
            }
            String product_id = request.getParameter("product_id");
            cart.removeItem(product_id);//xóa sản phẩm 
            List<Item> list = cart.getItems();
            session.setAttribute("cart", cart);
            session.setAttribute("total", cart.getTotalMoney());
            session.setAttribute("size", list.size());
            request.getRequestDispatcher("cart.jsp").forward(request, response);
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
