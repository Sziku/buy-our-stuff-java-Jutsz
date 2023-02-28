package com.codecool.buyourstuff.controller;

import com.codecool.buyourstuff.config.TemplateEngineUtil;
import com.codecool.buyourstuff.dao.DataManager;
import com.codecool.buyourstuff.dao.UserDao;

import com.codecool.buyourstuff.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private boolean registerError;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("registerError", registerError);
        engine.process("product/register.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDao userDao = DataManager.getUserDao();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userDao.isNameAvailable(username)) {
            registerError = false;
            userDao.add(new User(username, password));
            resp.sendRedirect("/");
        } else {
            registerError = true;
            doGet(req, resp);
        }
    }
}
