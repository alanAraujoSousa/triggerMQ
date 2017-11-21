package br.ufpe.cin.triggermq;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufpe.cin.triggermq.distribution.QueueManagerProxy;
import br.ufpe.cin.triggermq.distribution.QueueServer;

/**
 * Created by Alan Araujo on 6/23/17.
 */
public class ConsumerTest {

	private static final String AWS_AUTO_SCALING_SERVICE = "Aws Auto-scaling Service";
	private ExecutorService executor = Executors.newFixedThreadPool(6);

	private int machinesMonitored;
	private int bind1;
	private int bind2;
	private int bind3;
	private int bind4;

	@Before
	public void init() throws Throwable {
		startQueueServer();
		startProducer();
		startConsumer1();
		startConsumer2();
		startConsumer3();
		startConsumer4();
	}

	@After
	public void stop() {
		executor.shutdown();
		System.out.println("\n\n End test, total machines monitored: " + machinesMonitored + " \n bind to consumer 1: "
				+ bind1 + " \n bind to consumer 2: " + bind2 + " \n bind to consumer 3: " + bind3
				+ " \n bind to consumer 4: " + bind4);
	}

	@Test
	public void testName() throws Exception {
		Thread.sleep(5000);
	}

	/**
	 * Inicia o servidor de filas
	 * 
	 * @throws InterruptedException
	 * 
	 */
	private void startQueueServer() throws InterruptedException {
		executor.submit(new Thread(() -> {
			try {
				QueueServer.main(null);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}));
		Thread.sleep(500);
	}

	/**
	 * Cria filas e envia mensagens para elas
	 * 
	 * @throws Exception
	 */
	private void startProducer() throws Exception {
		executor.submit(new Thread(() -> {
			try {
				machinesMonitored = 0;
				while (true) {
					QueueManagerProxy awsAutoScalingService = new QueueManagerProxy(AWS_AUTO_SCALING_SERVICE);

					Random r = new Random();
					String msg = "machine: " + machinesMonitored + ", cpu: " + r.nextInt(101) + ", memory: "
							+ r.nextInt(101) + ", disk: " + r.nextInt(101);

					awsAutoScalingService.send(msg);

					machinesMonitored++;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}));
	}

	/**
	 * Recebe as mensagens das filas, subscribers indicam interesse em um padrão
	 * de de mensagem.
	 * 
	 * @throws Exception
	 */
	private void startConsumer1() throws Exception {

		executor.submit(new Thread(() -> {
			try {
				while (true) {
					QueueManagerProxy qP1 = new QueueManagerProxy(AWS_AUTO_SCALING_SERVICE);

					String bindingKey = "cpu > 70 and disk < 20";
					String msg = qP1.receive(bindingKey);

					if (!msg.isEmpty()) {
						System.out.println("binding key 1: " + bindingKey + " -- " + msg);
						bind1++;
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}));
	}

	/**
	 * Recebe as mensagens das filas, subscribers indicam interesse em um padrão
	 * de mensagem.
	 * 
	 * @throws Exception
	 */
	private void startConsumer2() throws Exception {

		executor.submit(new Thread(() -> {
			try {
				while (true) {
					QueueManagerProxy qP1 = new QueueManagerProxy(AWS_AUTO_SCALING_SERVICE);

					String bindingKey = "cpu < 20 and memory < 20";
					String msg = qP1.receive(bindingKey);

					if (!msg.isEmpty()) {
						System.out.println("binding key 2: " + bindingKey + " -- " + msg);
						bind2++;
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}));
	}

	/**
	 * Recebe as mensagens das filas, subscribers indicam interesse em um padrão
	 * de mensagem.
	 * 
	 * @throws Exception
	 */
	private void startConsumer3() throws Exception {

		executor.submit(new Thread(() -> {
			try {
				while (true) {
					QueueManagerProxy qP1 = new QueueManagerProxy(AWS_AUTO_SCALING_SERVICE);

					String bindingKey = "cpu == 20";
					String msg = qP1.receive(bindingKey);

					if (!msg.isEmpty()) {
						System.out.println("binding key 3: " + bindingKey + " -- " + msg);
						bind3++;
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}));
	}

	/**
	 * Recebe as mensagens das filas, subscribers indicam interesse em um padrão
	 * de mensagem.
	 * 
	 * @throws Exception
	 */
	private void startConsumer4() throws Exception {

		executor.submit(new Thread(() -> {
			try {
				while (true) {
					QueueManagerProxy qP1 = new QueueManagerProxy(AWS_AUTO_SCALING_SERVICE);

					String bindingKey = "cpu != 90 and memory < 10";
					String msg = qP1.receive(bindingKey);

					if (!msg.isEmpty()) {
						System.out.println("binding key 4: " + bindingKey + " -- " + msg);
						bind4++;
					}

				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}));
	}
}