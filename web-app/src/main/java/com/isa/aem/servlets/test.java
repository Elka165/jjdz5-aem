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
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/welcome")
public class test extends HttpServlet {

    @Inject
    private TemplateProvider templateProvider;

    @Override
    public void init() throws ServletException {
        System.out.println("servlet utworzony");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Template template = templateProvider
                .getTemplate(getServletContext(), "welcome-user");

        Map<String, Object> model = new HashMap<>();
        model.put("name", "Basia");

        try {
            template.process(model, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
