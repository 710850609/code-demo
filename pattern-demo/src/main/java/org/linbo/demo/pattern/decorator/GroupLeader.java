package org.linbo.demo.pattern.decorator;

/**
 * 小组长，除了默认工作
 * @author 71085
 *
 */
public class GroupLeader implements IWorker {

	private Coder coder;
	
	public GroupLeader(Coder coder) {
		this.coder = coder;
	}
	@Override
	public void doWork() {
		this.coder.doWork();
		System.out.println("管码农");
	}

}
