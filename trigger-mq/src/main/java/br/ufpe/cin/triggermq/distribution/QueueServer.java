package br.ufpe.cin.triggermq.distribution;

import java.io.IOException;

public class QueueServer {

	public static void main(String[] args) throws IOException, Throwable {

		System.out.println("Queue Server ready...");
		QueueManager queueManager = new QueueManager(1313);
		queueManager.run();
		System.out.println("Queue Server down...");
	}
}
