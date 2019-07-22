package org.linbo.demo.pattern.decorator;

public class ProjectManager implements IWorker {

	private GroupLeader leader;
	
	public ProjectManager(GroupLeader leader) {
		super();
		this.leader = leader;
	}

	@Override
	public void doWork() {
		this.leader.doWork();
		System.out.println("管项目组长");
	}

}
