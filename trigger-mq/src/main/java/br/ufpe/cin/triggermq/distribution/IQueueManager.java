package br.ufpe.cin.triggermq.distribution;

import java.io.IOException;

public interface IQueueManager {

	public void send(String msg) throws IOException, InterruptedException;

	public String receive() throws IOException, InterruptedException, ClassNotFoundException;

	public String receive(String bindingKey) throws IOException, InterruptedException, ClassNotFoundException;

}
