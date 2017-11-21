package br.ufpe.cin.triggermq.distribution;

import java.io.IOException;
import java.util.ArrayList;

import br.ufpe.cin.triggermq.infrastructure.ClientRequestHandler;

public class QueueManagerProxy implements IQueueManager {

	private String queueName = null;

	public QueueManagerProxy(String queueName) {
		this.setQueueName(queueName);
	}

	@Override
	public void send(String m) throws IOException, InterruptedException {

		// configure
		ClientRequestHandler crh = new ClientRequestHandler("localhost", 1313, false);
		Marshaller marshaller = new Marshaller();
		RequestPacket packet = new RequestPacket();
		Message message = new Message();

		// configure message
		MessageHeader messageHeader = new MessageHeader();
		messageHeader.setDestination(this.queueName);
		message.setHeader(messageHeader);
		message.setBody(new MessageBody(m));

		// configure packet
		RequestPacketBody packetBody = new RequestPacketBody();
		ArrayList<Object> parameters = new ArrayList<Object>(0);

		packetBody.setParameters(parameters);
		packetBody.setMessage(message);
		
		RequestPacketHeader packetHeader = new RequestPacketHeader();
		packetHeader.setOperation("send");
		
		packet.setPacketHeader(packetHeader);
		packet.setPacketBody(packetBody);

		// send request
		crh.send(marshaller.marshall((Object) packet));
	}

	@Override
	public String receive() throws IOException, InterruptedException, ClassNotFoundException {
		return this.receive(null);
	}

	@Override
	public String receive(String bindingKey) throws IOException, InterruptedException, ClassNotFoundException {

		ClientRequestHandler crh = new ClientRequestHandler("localhost", 1313, true);
		Marshaller marshaller = new Marshaller();
		
		RequestPacket requestPacket = new RequestPacket();
		ReplyPacket marshalledReplyPacket = new ReplyPacket();
		byte[] unmarshalledReplyPacket = new byte[1024];
		Message message = new Message();

		// configure message
		MessageHeader messageHeader = new MessageHeader();
		messageHeader.setDestination(this.queueName);
		messageHeader.setBindingKey(bindingKey);
		
		message.setHeader(messageHeader);
		message.setBody(new MessageBody("messageBody"));

		// configure packet
		RequestPacketBody packetBody = new RequestPacketBody();
		ArrayList<Object> parameters = new ArrayList<Object>(0);

		packetBody.setParameters(parameters);
		packetBody.setMessage(message);

		RequestPacketHeader packetHeader = new RequestPacketHeader();
		packetHeader.setOperation("receive");

		requestPacket.setPacketHeader(packetHeader);
		requestPacket.setPacketBody(packetBody);

		// send request
		crh.send(marshaller.marshall((Object) requestPacket));

		// receive reply
		unmarshalledReplyPacket = crh.receive();
		marshalledReplyPacket = (ReplyPacket) marshaller.unmarshall(unmarshalledReplyPacket);

		return marshalledReplyPacket.getReply();
	}

	public String getQueueName() {
		return queueName;
	}

	public QueueManagerProxy setQueueName(String queueName) {
		this.queueName = queueName;
		return this;
	}
}
