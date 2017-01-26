import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


@WebSocket
public class ChatWebSocketHandler {

    private MessageHandler messageHandler = new MessageHandler();

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception{

    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason){
        Message message = new Message();
        message.setText("");
        message.setType("onclose");
        messageHandler.actOnMessage(message, user);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String incomingData){
        Message message = MessageGenerator.generateMessage(incomingData);
        messageHandler.actOnMessage(message, user);
    }

}