package chat;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gethappy90
 * @since 31.01.2016.
 */
public class ChatService {
    public final static Logger log = Logger.getLogger(ChatService.class);

    private Set<ChatWebSocket> webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public boolean add(ChatWebSocket webSocket) {
        return webSockets.add(webSocket);
    }

    public void remove(ChatWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

    public void broadCast(String data) {
        for (ChatWebSocket user : webSockets) {
            try {
                user.sendMessage(data);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }






}
