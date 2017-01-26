import org.eclipse.jetty.websocket.api.Session;

public class MessageHandler {
    private Chat chat;

    public MessageHandler(){
        this.chat = new Chat();
    }

    public void actOnMessage(Message message, Session session){
        switch(message.getType()){
            case "nickname":
                handleNickname(message, session);
                break;
            case "room":
                handleRoom(message, session);
                break;
            case "message":
                handleMessage(message, session);
                break;
            case "leave":
                handleLeave(message, session);
                break;
            case "getnames":
                handleNameList(message, session);
                break;
            default:
                break;
        }
    }

    private void handleNickname(Message message, Session session){
        chat.sendRoomList(session, message.getText());
    }

    private void handleNameList(Message message, Session session){
        chat.sendTakenNicknamesToUser(session);
    }

    private void handleRoom(Message message, Session session){
        chat.serverSaysUserJoined(session, message.getText());
    }
    
    private void handleMessage(Message message, Session session){
        if(chat.getRoom(session).equals("chatbot")){
        	if(message.getText().equals("!pogoda") || message.getText().equals("!godzina") || message.getText().equals("!dzien"))
            chat.botAnswer(session, message.getText());
      }else
        chat.broadcastMessage(session, message.getText());

   

    }

    private void handleLeave(Message message, Session session){
        chat.serverSaysUserLeftRoom(session);
    }

}