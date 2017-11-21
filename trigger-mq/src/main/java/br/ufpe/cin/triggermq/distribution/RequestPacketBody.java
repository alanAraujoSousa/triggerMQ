package br.ufpe.cin.triggermq.distribution;

import java.io.Serializable;
import java.util.ArrayList;

public class RequestPacketBody implements Serializable {
	private ArrayList<Object> parameters = new ArrayList<Object>();
	private Message message;

	private static final long serialVersionUID = 1L;
	
	public ArrayList<Object> getParameters() {
		return parameters;
	}
	public void setParameters(ArrayList<Object> parameters) {
		this.parameters = parameters;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
