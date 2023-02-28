package com.codecool.buyourstuff.controller;

import com.codecool.buyourstuff.config.TemplateEngineUtil;
import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.Order;
import com.codecool.buyourstuff.model.ShippingInfo;
import com.codecool.buyourstuff.model.User;
import com.codecool.buyourstuff.util.Error;
import com.codecool.buyourstuff.util.Util;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/payment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/");
            return;
        }

        int cartId;
        try {
            cartId = Integer.parseInt(req.getParameter("cart"));
        } catch (NumberFormatException e) {
            Util.handleError(req, resp, HttpServletResponse.SC_BAD_REQUEST, Error.MALFORMED_CART_ID);
            return;
        }
        CartDao cartDataStore = DataManager.getCartDao();
        Cart cart = cartDataStore.find(cartId);

        String name = req.getParameter("name");
        String email = req.getParameter("e-mail");
        String phoneNumber = req.getParameter("phone-number");
        String billingAddress = req.getParameter("billing-address");
        String shippingAddress = req.getParameter("shipping-address");
        ShippingInfo shippingInfo = new ShippingInfo(name, email, phoneNumber, billingAddress, shippingAddress);

        Order order = new Order(cart, shippingInfo);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/payment.html", context, resp.getWriter());
    }
}

