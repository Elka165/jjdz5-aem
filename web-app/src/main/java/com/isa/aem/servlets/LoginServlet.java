package com.isa.aem.servlets;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.isa.aem.dao.ActivityDao;
import com.isa.aem.dao.UserDao;
import com.isa.aem.informationcollect.RecordCreator;
import com.isa.aem.model.Activity;
import com.isa.aem.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final String ID_TOKEN_PARAMETER = "id_token";
    private static final String NAME_PARAMETER = "name";
    private static final String USER_NAME_PARAMETER = "userName";

    @Inject
    private RecordCreator recordCreator;

    @Inject
    private UserDao userDao;
    private ActivityDao activityDao;

    private Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) {

        resp.setContentType("text/html");

        try {
            String idToken = req.getParameter(ID_TOKEN_PARAMETER);
            GoogleIdToken.Payload payLoad = IdTokenVerifierAndParser.getPayload(idToken);
            String name = (String) payLoad.get(NAME_PARAMETER);
            String email = payLoad.getEmail();
            LOG.info("User name: " + name);
            LOG.info("User email: " + email);

            HttpSession session = req.getSession(true);
            session.setAttribute(USER_NAME_PARAMETER, name);

            checkAndAddIfNotExistUser(req, email, name);
//            addLoginDateTime(req);
            //fix me 
            session.setAttribute("email", email);
            resp.sendRedirect("/currency-manager");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkAndAddIfNotExistUser(HttpServletRequest req,
                                            String email,
                                            String name) {

        if (userDao.checkIfEmailExist(email)) {
            addUser(req);
            LOG.info("Add user : {}", name);
        } else {
            LOG.info("User: {}, exist now", name);
        }
    }

    private void addUser(HttpServletRequest req) {

        String nameByGoogle = recordCreator.getNameByGoogle(req);
        String emailByGoogle = recordCreator.getEmailByGoogle(req);

        Boolean isAdmin = recordCreator.isAdmin(emailByGoogle);

        User user = recordCreator.createUser(
                nameByGoogle,
                emailByGoogle,
                isAdmin);

        userDao.save(user);
        LOG.info("Add new user: {}.", nameByGoogle);
    }

    private void addLoginDateTime(HttpServletRequest req) {

        Long id = recordCreator.findIdFromDataBaseByEmailFromSession(req);
        System.out.println(id);

        User user = userDao.findById(id);

        Activity loginDataTime = recordCreator.logInTime(LocalDateTime.now());

        loginDataTime.setUser(user);
        activityDao.save(loginDataTime);
        LOG.info("Login date and time: {}.", loginDataTime);
    }
}
