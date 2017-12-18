package com.battle.socket;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.quickserver.net.server.ClientCommandHandler;
import org.quickserver.net.server.ClientHandler;

public class EchoCommandHandler implements ClientCommandHandler{

	@Override
	public void closingConnection(ClientHandler arg0) throws IOException {
		
		System.out.println("closingConnection");
		
	}

	@Override
	public void gotConnected(ClientHandler arg0) throws SocketTimeoutException, IOException {
		
		System.out.println("gotConnected");
		
	}

	@Override
	public void lostConnection(ClientHandler arg0) throws IOException {
		
		System.out.println("lostConnection");
		
	}

	@Override
	public void handleCommand(ClientHandler handler, String command) throws SocketTimeoutException, IOException {
		
		handler.sendClientMsg("ssss");
		
	}

}
