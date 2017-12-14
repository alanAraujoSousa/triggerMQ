package br.ufpe.cin.triggermq.distribution;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QueueClientProducer {

	public static void main(String[] args) throws IOException, Throwable {

		String consumerIP = args[0];
		String producerName = args[1];
		
		QueueManagerProxy producerClient = new QueueManagerProxy(consumerIP, producerName);

		Map<String, Integer> params = extractArgs(args);
		String msg = null;
		for (Iterator<Entry<String, Integer>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Integer> e = iterator.next();

			msg += e.getKey() + ": " + e.getValue();
			if (iterator.hasNext()) {
				msg += ", ";
			}
		}

		producerClient.send(msg);
	}

	private static Map<String, Integer> extractArgs(String[] args) {

		List<String> asList = Arrays.asList(args);
		asList.remove(0); // the ip of producer 
		asList.remove(1); // the name of producer 
		Map<String, Integer> map = new HashMap<>();
		for (Iterator<String> iterator = asList.iterator(); iterator.hasNext();) {

			String key = iterator.next();
			String value = iterator.next();

			value = value.replace("%", "");
			int parseInt = Integer.parseInt(value);

			map.put(key, parseInt);
		}

		return map;
	}
}
