package com.codecool.buyourstuff.controller;

import com.codecool.buyourstuff.config.TemplateEngineUtil;

import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import com.codecool.buyourstuff.dao.UserDao;
import com.codecool.buyourstuff.model.User;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private boolean loginError;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("loginError", loginError);
        engine.process("product/login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDao userDao = DataManager.getUserDao();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            User user = userDao.find(username, password);
            loginError = false;
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/");
        } catch (DataNotFoundException e) {
            loginError = true;
            doGet(req, resp);
        }
    }
}
