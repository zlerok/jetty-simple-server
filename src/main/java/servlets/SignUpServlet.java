package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import database.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author gethappy90
 * @since 13.01.2016.
 */
public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if (accountService.getUserByLogin(login).isPresent()) {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
                response.getWriter().print("User already exist");
                Logger.getGlobal().info("login " + login + ".  pass: " + password);
                return;
            }
            UserProfile newUser = new UserProfile(login, password.toCharArray());
            accountService.addNewUser(newUser);
            Gson gson = new Gson();
            String json = gson.toJson(newUser);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(json);
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (DBException e) {
            createInternalProblemResponse(response);

            e.printStackTrace();
        }
    }

    private static void createInternalProblemResponse(HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().print("Try again later");
    }
}
