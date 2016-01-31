package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import org.apache.log4j.Logger;

/**
 * @author gethappy90
 * @since 31.01.2016.
 */
@WebSocket
public class ChatWebSocket {
    public final static Logger log = Logger.getLogger(ChatWebSocket.class);
    private ChatService chatService;
    private Session session;

    public ChatWebSocket(ChatService chatService) {
        this.chatService = chatService;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        chatService.add(this);
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        chatService.broadCast(message);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        chatService.remove(this);
    }

    public void sendMessage(String message) {
        try {
            session.getRemote().sendString(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
