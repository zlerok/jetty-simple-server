package main;

import accounts.AccountService;
import database.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.MirrorServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

import java.util.logging.Logger;

/**
 * @author gethappy90
 * @since 13.01.2016
 */
public class Main {

    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();
        startServer(dbService);
    }

    static void addAuthorizedServlets(ServletContextHandler context, DBService dbService) {
        AccountService accountService = new AccountService(dbService);
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
    }

    static void addMirrorServlet(ServletContextHandler context) {
        MirrorServlet servlet = new MirrorServlet();
        context.addServlet(new ServletHolder(servlet), "/mirror");
    }

    static void startServer(DBService dbService) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        addAuthorizedServlets(context, dbService);

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        Logger.getGlobal().info("Server started");
        server.join();
    }
}
