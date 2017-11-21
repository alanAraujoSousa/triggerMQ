package br.ufpe.cin.triggermq.distribution;

import java.io.Serializable;

public class RequestPacketHeader implements Serializable {

	private String operation;

	private String bindingKey;

	private static final long serialVersionUID = 1L;

	public RequestPacketHeader() {
	}

	public String getOperation() {
		return operation;
	}

	public RequestPacketHeader setOperation(String operation) {
		this.operation = operation;
		return this;
	}

	public String getBindingKey() {
		return bindingKey;
	}

	public RequestPacketHeader setBindingKey(String bindingKey) {
		this.bindingKey = bindingKey;
		return this;
	}

}
