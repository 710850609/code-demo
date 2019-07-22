package org.linbo.demo.pattern.decorator;

/**
 * 码农
 * @author 71085
 *
 */
public class Coder implements IWorker {

	@Override
	public void doWork() {
		System.out.println("打代码");
	}

}
