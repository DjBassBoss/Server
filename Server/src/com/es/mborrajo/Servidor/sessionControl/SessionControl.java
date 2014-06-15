package com.es.mborrajo.Servidor.sessionControl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.mborrajo.util.logging.Level;
import com.mborrajo.util.logging.Logger;
import com.mborrajo.util.logging.LoggerManager;

public class SessionControl {
	private List<User> activeUsers;
	private Timer timer1;
	
	private static SessionControl sc;
	
	private static Logger logger;
	
	public int updateTime = 1000;
	public int expireTime = 5;
	
	public static SessionControl getSessionControl(){
		if (sc == null){
			sc = new SessionControl();
		}
		
		return sc;
	}
	
	private SessionControl(){
		activeUsers = new ArrayList<User>();
		
		
		//Setup Timer
		TimerTask timerTask = new TimerTask(){
			public void run(){
				sc.checkActiveUsers();
			}
		};
		timer1 = new Timer();
		timer1.scheduleAtFixedRate(timerTask, 0, updateTime);
		
		try {
			logger = LoggerManager.getLogger();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
	}
	
	public boolean addUser(User user){
		if (getUserIndex(user.username) == -1){
			logger.logMessage(Level.INFO, "SessionControl: USER:" + user.username + " - Session Started.");
			activeUsers.add(user);
			return true;
		}
		return false;
		
	}
	
	/*private User getUser(String username){
		
		for (int i = 0; i < activeUsers.size(); i++){
			User user = activeUsers.get(i);
			if (user.isUser(username)) return user;
		}
		return null;
	}*/
	
	
	public int getUserIndex(String username){
		
		for (int i = 0; i < activeUsers.size(); i++){
			User user = activeUsers.get(i);
			if (user.isUser(username)) return i;
		}
		return -1;
		
	}
	
	public boolean userActive(String username) throws Exception{
		
		logger.logMessage(Level.FINEST, "SessionControl: USER:" + username + " - User Active.");
		int index = getUserIndex(username);
		if (index == -1 ) throw new Exception("User not found");
		User user = activeUsers.get(index);
		user.lastActivityInstant = Instant.now();
		activeUsers.set(index, user);
		
		return true;
	}
	

	public void checkActiveUsers(){
		
		for (int i = 0; i < activeUsers.size(); i++){
			User user = activeUsers.get(i);
			user.sessionDuration = Duration.between(user.sessionStartInstant,Instant.now());
			if (Duration.between(user.lastActivityInstant,Instant.now()).toMillis() > (expireTime * 1000)){
				activeUsers.remove(user);
				logger.logMessage(Level.INFO, "SessionControl: USER:" + user.username + " - Session Expired.");
			}
		}
	}
	
}
