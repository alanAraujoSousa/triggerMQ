package br.ufpe.cin.triggermq.infrastructure;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientRequestHandler {

	private String host;
	private int port;
	private boolean expectedReply;

	int sentMessageSize;
	int receiveMessageSize;

	Socket clientSocket = null;
	DataOutputStream outToServer = null;
	DataInputStream inFromServer = null;

	public ClientRequestHandler(String host, int port, boolean expectedReply) {
		this.host = host;
		this.port = port;
		this.setExpectedReply(expectedReply);
	}

	public void send(byte[] msg) throws IOException, InterruptedException {

		clientSocket = new Socket(this.host, this.port);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new DataInputStream(clientSocket.getInputStream());

		sentMessageSize = msg.length;
		outToServer.writeInt(sentMessageSize);
		outToServer.write(msg, 0, sentMessageSize);
		outToServer.flush();

		// close socket if no reply is expected
		if (!this.expectedReply) {
			clientSocket.close();
			outToServer.close();
			inFromServer.close();
		}

		return;
	}

	public byte[] receive() throws IOException, InterruptedException, ClassNotFoundException {

		byte[] msg = null;

		if (expectedReply) {
			receiveMessageSize = inFromServer.readInt();
			msg = new byte[receiveMessageSize];
			inFromServer.read(msg, 0, receiveMessageSize);

			clientSocket.close();
			outToServer.close();
			inFromServer.close();
		}
		return msg;
	}

	public boolean isExpectedReply() {
		return expectedReply;
	}

	public void setExpectedReply(boolean expectedReply) {
		this.expectedReply = expectedReply;
	}
}