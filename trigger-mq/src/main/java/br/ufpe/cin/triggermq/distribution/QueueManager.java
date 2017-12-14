package br.ufpe.cin.triggermq.distribution;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import br.ufpe.cin.triggermq.infrastructure.ServerRequestHandler;

public class QueueManager {

	private String host;
	private int port;
	Map<String, Queue> queues = new HashMap<String, Queue>();

	public QueueManager(int port) throws UnknownHostException {
		this.setHost(InetAddress.getLocalHost().getHostName());
		this.setPort(port);
	}

	public void run() throws IOException, Throwable {

		byte[] requestPacketUnmarshalled = new byte[1024];
		byte[] replyPacketMarshalled = new byte[1024];
		RequestPacket requestPacketMarshalled = new RequestPacket();
		ReplyPacket replyPacketUnmarshalled = new ReplyPacket();
		String queueName = null;

		Marshaller marshaller = new Marshaller();
		ServerRequestHandler srh = new ServerRequestHandler(this.port);

		while (true) {

			requestPacketUnmarshalled = srh.receive();
			requestPacketMarshalled = (RequestPacket) marshaller.unmarshall(requestPacketUnmarshalled);

			switch (requestPacketMarshalled.getPacketHeader().getOperation().trim()) {

			// put something in a queue
			case "send":
				queueName = new String(requestPacketMarshalled.getPacketBody().getMessage().getHead().getDestination());
				Message message = requestPacketMarshalled.getPacketBody().getMessage();
				
				if (queues.containsKey(queueName)) {
					queues.get(queueName).enqueue(message);
				} else {
					queues.put(queueName, new Queue());
					queues.get(queueName).enqueue(message);
				}
				break;

			// remove a message from a queue
			case "receive":
				RequestPacketBody packetBody = requestPacketMarshalled.getPacketBody();
				MessageHeader head = packetBody.getMessage().getHead();
				
				queueName = new String(head.getDestination());

				if (queues.containsKey(queueName) && queues.get(queueName).queueSize() != 0) {
					String bindingKey = head.getBindingKey();
					if (bindingKey != null) {
						Queue queue = queues.get(queueName);
						Message query = queue.query(bindingKey);
						MessageBody body2 = query.getBody();
						String body = body2.getBody();
						replyPacketUnmarshalled.setReply(body);						
					} else {
						replyPacketUnmarshalled.setReply(queues.get(queueName).dequeue().getBody().getBody());						
					}
				} else {
					replyPacketUnmarshalled.setReply("");
				}

				replyPacketMarshalled = marshaller.marshall((Object) replyPacketUnmarshalled);
				srh.send(replyPacketMarshalled);
				
				break;
			}
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
