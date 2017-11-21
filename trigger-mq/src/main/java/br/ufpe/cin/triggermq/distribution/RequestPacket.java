package br.ufpe.cin.triggermq.distribution;

import java.io.Serializable;

public class RequestPacket implements Serializable {
	private RequestPacketHeader packetHeader;
	private RequestPacketBody packetBody;

	private static final long serialVersionUID = 1L;

	public RequestPacket(){}

	public RequestPacketHeader getPacketHeader() {
		return packetHeader;
	}

	public void setPacketHeader(RequestPacketHeader packetHeader) {
		this.packetHeader = packetHeader;
	}

	public RequestPacketBody getPacketBody() {
		return packetBody;
	}

	public void setPacketBody(RequestPacketBody packetBody) {
		this.packetBody = packetBody;
	}
	
}
