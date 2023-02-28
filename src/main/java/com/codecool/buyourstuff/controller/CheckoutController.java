package com.codecool.buyourstuff.controller;

import com.codecool.buyourstuff.config.TemplateEngineUtil;
import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CartDao cartDataStore = DataManager.getCartDao();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/");
        } else {
            Cart cart = cartDataStore.find(user.getCartId());
            context.setVariable("cart", cart);
            engine.process("product/checkout.html", context, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
