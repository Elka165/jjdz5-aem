package com.isa.aem.servlets;

import com.isa.aem.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/hello-servlet-aem")
public class HelloServletAem extends HttpServlet {

    @Inject
    private TemplateProvider templateProvider;

    @Override
    public void init() throws ServletException {
        System.out.println("servlet utworzony");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Template template = templateProvider
                .getTemplate(getServletContext(), "test");
        try {
            template.process(new HashMap<>(), resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
