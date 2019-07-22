package org.linbo.demo.pattern.decorator;

public class Main {

	public static void main(String[] args) {
		Coder coder = new Coder();
		System.out.println("Coder工作内容：");
		coder.doWork();
		System.out.println("======================");
		
		GroupLeader leader = new GroupLeader(coder);
		System.out.println("GroupLeader工作内容：");
		leader.doWork();
		System.out.println("======================");
		
		ProjectManager manager = new ProjectManager(leader);
		System.out.println("ProjectManager工作内容：");
		manager.doWork();

	}

}
