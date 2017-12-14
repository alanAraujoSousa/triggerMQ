package br.ufpe.cin.triggermq.distribution;

import java.io.Serializable;

public class MessageBody implements Serializable {
	private String body;

	private static final long serialVersionUID = 1L;

	public MessageBody(String body){
		this.setBody(body);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
