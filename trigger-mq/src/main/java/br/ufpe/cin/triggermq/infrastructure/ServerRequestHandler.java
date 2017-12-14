package br.ufpe.cin.triggermq.infrastructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRequestHandler {
	private int port;
	private ServerSocket welcomeSocket = null;

	Socket connectionSocket = null;

	int sentMessageSize;
	int receivedMessageSize;
	DataOutputStream outToClient = null;
	DataInputStream inFromClient = null;

	public ServerRequestHandler(int port) throws IOException {
		this.port = port;
		this.welcomeSocket = new ServerSocket(this.port);
	}

	public byte[] receive() throws IOException, Throwable {

		byte[] rcvMsg = null;

		connectionSocket = welcomeSocket.accept();

		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		inFromClient = new DataInputStream(connectionSocket.getInputStream());

		receivedMessageSize = inFromClient.readInt();
		rcvMsg = new byte[receivedMessageSize];

		inFromClient.read(rcvMsg, 0, receivedMessageSize);

		return rcvMsg;
	}

	public void send(byte[] msg) throws IOException, InterruptedException {

		sentMessageSize = msg.length;
		outToClient.writeInt(sentMessageSize);
		outToClient.write(msg);
		outToClient.flush();

		connectionSocket.close();
		outToClient.close();
		inFromClient.close();

		return;
	}

}
