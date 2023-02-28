package com.codecool.buyourstuff.controller;

import com.codecool.buyourstuff.dao.*;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.config.TemplateEngineUtil;
import com.codecool.buyourstuff.model.ProductCategory;
import com.codecool.buyourstuff.model.Supplier;
import com.codecool.buyourstuff.model.User;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import com.codecool.buyourstuff.util.Error;
import com.codecool.buyourstuff.util.Util;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private static boolean shouldRetry = true;
    private Integer id = 1;
    private boolean filterByCategory = true;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ProductDao productDataStore = DataManager.getProductDao();
        ProductCategoryDao productCategoryDataStore = DataManager.getProductCategoryDao();
        CartDao cartDataStore = DataManager.getCartDao();
        SupplierDao supplierDataStore = DataManager.getSupplierDao();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        User user = (User) req.getSession().getAttribute("user");
        Integer cartSize =  Optional.ofNullable(user)
                .map(User::getCartId)
                .map(id -> cartDataStore.find(id).size())
                .orElse(0);
        context.setVariable("cartSize", cartSize);

        try {
            context.setVariable("categories", productCategoryDataStore.getAll());
            context.setVariable("suppliers", supplierDataStore.getAll());
            if (filterByCategory) {
                ProductCategory category = productCategoryDataStore.find(id);
                context.setVariable("filter", category);
                context.setVariable("products", productDataStore.getBy(category));
            } else {
                Supplier supplier = supplierDataStore.find(id);
                context.setVariable("filter", supplier);
                context.setVariable("products", productDataStore.getBy(supplier));
            }
            engine.process("product/index.html", context, resp.getWriter());
        } catch (DataNotFoundException e) {
            if (shouldRetry) {
                DataManager.init();
                shouldRetry = false;
                resp.sendRedirect("/");
            } else {
                e.printStackTrace();
                Util.handleError(req, resp, HttpServletResponse.SC_BAD_REQUEST, "Wrong id");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> params = Collections.list(req.getParameterNames());

        try {
            if (params.contains("categoryId")) {
                id = Integer.parseInt(req.getParameter("categoryId"));
                filterByCategory = true;
            } else if (params.contains("supplierId")) {
                id = Integer.parseInt(req.getParameter("supplierId"));
                filterByCategory = false;
            } else {
                CartDao cartDataStore = DataManager.getCartDao();
                ProductDao productDataStore = DataManager.getProductDao();

                int productId = Integer.parseInt(req.getParameter("product"));
                User user = (User) req.getSession().getAttribute("user");
                if (user == null) {
                    resp.sendRedirect("/");
                    return;
                }
                int cartId = user.getCartId();

                Cart cart = cartDataStore.find(cartId);
                try {
                    cart.add(productDataStore.find(productId));
                } catch (DataNotFoundException e) {
                    Util.handleError(req, resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                    return;
                }
            }
            doGet(req, resp);
        } catch (NumberFormatException e) {
            Util.handleError(req, resp, HttpServletResponse.SC_BAD_REQUEST, Error.MALFORMED_FILTER_ID);
        }
    }
}
