package br.ufpe.cin.triggermq.distribution;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QueueClientConsumer {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		
		String consumerIP = args[0];
		String consumerName = args[1];
		
		QueueManagerProxy consumerClient = new QueueManagerProxy(consumerIP, consumerName);
		
		Map<String, Integer> params = extractArgs(args);
		String bindingKey = null;
		for (Iterator<Entry<String, Integer>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Integer> e = iterator.next();

			bindingKey += e.getKey() + ": " + e.getValue();
			if (iterator.hasNext()) {
				bindingKey += " and ";
			}
		}

		while (true) {
			String msg = consumerClient.receive(bindingKey);
			
			if (!msg.isEmpty()) {
				System.out.println("Routing Key " + bindingKey + " -- " + msg);
			}
			Thread.sleep(500);
		}
	}
	
	private static Map<String, Integer> extractArgs(String[] args) {

		List<String> asList = Arrays.asList(args);
		asList.remove(0); // the ip of consumer 
		asList.remove(1); // the name of consumer 
		Map<String, Integer> map = new HashMap<>();
		for (Iterator<String> iterator = asList.iterator(); iterator.hasNext();) {

			String key = iterator.next();
			String value = iterator.next();

			int parseInt = Integer.parseInt(value);

			map.put(key, parseInt);
		}

		return map;
	}
}
