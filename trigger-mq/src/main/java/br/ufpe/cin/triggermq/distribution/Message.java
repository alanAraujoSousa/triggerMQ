package br.ufpe.cin.triggermq.distribution;

import java.io.Serializable;

public class Message implements Serializable {
	private MessageHeader header;
	private MessageBody body;

	private static final long serialVersionUID = 1L;

	public MessageHeader getHead() {
		return header;
	}
	public void setHeader(MessageHeader header) {
		this.header = header;
	}
	public MessageBody getBody() {
		return body;
	}
	public void setBody(MessageBody body) {
		this.body = body;
	}
}
