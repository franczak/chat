
import org.eclipse.jetty.websocket.api.*;
import org.json.*;

import bot.Bot;

import java.io.IOException;
import java.text.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;


public class Chat {

    private Map<Session, String> userUsernameMap;
    private Map<Session, String> userRoomMap;
    private LinkedList<String> roomList;
    private Bot bot;

    public Chat (){
        userUsernameMap = new ConcurrentHashMap<>();
        userRoomMap = new ConcurrentHashMap<>();
        roomList = new LinkedList<>();
        bot = new Bot();
        roomList.add("chatbot");
    }
    private void sendMessage(String sender, String message, String room){
        LinkedList<String> users = createRoomUsersList(room);
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session ->{
            try{
                if (userRoomMap.containsKey(session)) {
                    if (userRoomMap.get(session).equals(room)) {
                        session.getRemote().sendString(String.valueOf(new JSONObject()
                                .put("type", "message")
                                .put("userMessage", createHtmlMessageFromSender(sender, message))
                                .put("userlist", users))
                        );
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    
    public void broadcastMessage(Session session, String message){
        String sender = getNickName(session);
        String room = getRoom(session);
        sendMessage(sender, message, room);
    }

    
    public void serverSaysUserLeftRoom(Session user){
        String sender = "Czat";
        String message = getNickName(user) + " opuœci³ pokój.";
        String room = getRoom(user);
        removeUserFromRoom(user);
        sendMessage(sender, message, room);
        sendRoomList(user, getNickName(user));
    }

    public void serverSaysUserJoined(Session user, String room){
        String sender = "Czat";
        String message = getNickName(user) + " do³¹czy³ do pokoju.";
        addUserToRoom(user, room);
        sendMessage(sender, message, room);
    }
    

    
    public void botAnswer(Session user, String option){
        String message = bot.answer(option);
        try {
			sendMessageFromBot(message,user);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void sendMessageFromBot(String message, Session user) throws IOException{
    	 try {
			user.getRemote().sendString(String.valueOf(new JSONObject()
			         .put("type", "message")
			         .put("userMessage", createHtmlMessageFromSender("chatbot", message))
			         .put("userlist", user)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
    			 
    	}
    
    private  String createHtmlMessageFromSender(String sender, String message){
        return article().with(
                b(sender + " :"),
                p(message),
                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
        ).render();
    }


    private LinkedList<String> createRoomUsersList(String room){
        LinkedList<String> users = new LinkedList<>();
        userUsernameMap.keySet().forEach(
                user -> {
                    if (userRoomMap.containsKey(user)) {
                        if (userRoomMap.get(user).equals(room)) {
                            users.add(userUsernameMap.get(user));
                        }
                    }
                }
        );
        return users;
    }

    public void sendRoomList(Session session, String name){
        addUserNickname(session, name);
        try{
            session.getRemote().sendString(String.valueOf(new JSONObject()
                    .put("type", "roomlist")
                    .put("roomlist", roomList.toArray())
            ));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendTakenNicknamesToUser(Session session){
        try{
            session.getRemote().sendString(String.valueOf(new JSONObject()
                    .put("type", "userlist")
                    .put("userlist", userUsernameMap.values())
            ));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getRoom(Session user){
        return userRoomMap.get(user);
    }

    private String getNickName(Session user){
        return userUsernameMap.get(user);
    }

    public void removeUser(Session user){
        userUsernameMap.remove(user);
    }

    private void removeUserFromRoom(Session user){
        userRoomMap.remove(user);
    }

    private void addRoom(String room){
        roomList.add(room);
    }

    void addUserNickname(Session user, String nickname){
        userUsernameMap.put(user, nickname);
    }
    private void addUserToRoom(Session user, String room){
        userRoomMap.put(user, room);
        if(!roomList.contains(room)){
            addRoom(room);
        }
    }
}