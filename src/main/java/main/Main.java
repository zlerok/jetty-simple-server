package main;

import accounts.AccountService;
import database.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.MirrorServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;
import servlets.WebSocketChatServlet;

import java.util.logging.Logger;

/**
 * @author gethappy90
 * @since 13.01.2016
 */
public class Main {

    public static void main(String[] args) throws Exception {
        startServer();
    }

    static void addAuthorizedServlets(ServletContextHandler context) {
        DBService dbService = new DBService();
        AccountService accountService = new AccountService(dbService);
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
    }

    static void addMirrorServlet(ServletContextHandler context) {
        MirrorServlet servlet = new MirrorServlet();
        context.addServlet(new ServletHolder(servlet), "/mirror");
    }

    static Server addWebSocketChatServlet(ServletContextHandler context, Server server) {
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        //resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase("chat");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, context});

        server.setHandler(handlerList);
        return server;
    }

    static void startServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        Server server = new Server(8080);
        //addAuthorizedServlets(context);
        addWebSocketChatServlet(context, server);

        server.start();
        Logger.getGlobal().info("Server started");
        server.join();
    }
}
