package br.ufpe.cin.triggermq.distribution;

import java.io.Serializable;

public class ReplyPacket implements Serializable {
	private String reply;

	private static final long serialVersionUID = 1L;

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

}
