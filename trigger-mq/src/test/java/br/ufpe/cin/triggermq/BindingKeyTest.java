package br.ufpe.cin.triggermq;

import org.junit.Test;

import br.ufpe.cin.triggermq.utils.Utils;

/**
 * Created by Alan Araujo on 6/23/17.
 */
public class BindingKeyTest {

	@Test
	public void test1() throws Exception {

		boolean match = Utils.match("cpu < 70", "machine: 2, cpu: 69, memory: 50, disk: 20");
		assert (match == true);
	}

	@Test
	public void test2() throws Exception {

		boolean match = Utils.match("cpu < 70", "machine: 2, cpu: 71, memory: 50, disk: 20");
		assert (match == false);
	}

	@Test
	public void test3() throws Exception {

		boolean match = Utils.match("cpu < 30 and memory < 20", "machine: 2, cpu: 25, memory: 19, disk: 20");
		assert (match == true);
	}
	
	@Test
	public void test4() throws Exception {

		boolean match = Utils.match("cpu < 30 and memory > 20", "machine: 2, cpu: 25, memory: 19, disk: 20");
		assert (match == false);
	}
	
	@Test
	public void test5() throws Exception {
		boolean match = Utils.match("cpu == 30 and memory != 20", "machine: 2, cpu: 30, memory: 20, disk: 20");
		assert (match == false);
	}

}