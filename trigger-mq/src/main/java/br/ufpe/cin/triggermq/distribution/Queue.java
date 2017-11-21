package br.ufpe.cin.triggermq.distribution;

import java.util.Iterator;
import java.util.LinkedList;

import br.ufpe.cin.triggermq.utils.Utils;

public class Queue {

	private java.util.Queue<Message> queue = new LinkedList<Message>();

	public Queue() {
	};

	public void enqueue(Message msg) {
		this.queue.add(msg);
	}

	public Message dequeue() {
		Message t;

		t = this.queue.poll();

		return t;
	}

	public int queueSize() {
		return this.queue.size();
	}

	public Message query(String bindingKey) {
		for (Iterator<Message> iterator = queue.iterator(); iterator.hasNext();) {
			Message message = iterator.next();
			MessageBody body = message.getBody();
			
			boolean match = Utils.match(bindingKey, body.getBody());
			
			if (match) {
				iterator.remove();
				return message;
			}
		}
		Message message = new Message();
		message.setBody(new MessageBody(""));
		return message;
	}
}
