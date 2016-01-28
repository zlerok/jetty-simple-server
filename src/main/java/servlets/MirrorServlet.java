package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author gethappy90
 * @since 13.01.2016.
 */
public class MirrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder message = new StringBuilder("");
        Map<String, String[]> params = req.getParameterMap();
        if (params.containsKey("key")) {
            message.append(params.get("key")[0]);
        }

        resp.getWriter().println(message);

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
