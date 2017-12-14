package br.ufpe.cin.triggermq.distribution;

import java.io.Serializable;

public class MessageHeader implements Serializable {
	private String destination;

	private static final long serialVersionUID = 1L;

	private String bindingKey;

	public String getBindingKey() {
		return bindingKey;
	}

	public MessageHeader() {
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setBindingKey(String bindingKey) {
		this.bindingKey = bindingKey;
	}

}
