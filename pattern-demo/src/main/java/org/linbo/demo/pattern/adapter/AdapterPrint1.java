package org.linbo.demo.pattern.adapter;

public class AdapterPrint1 extends OldPrint implements INewPrint{

	@Override
	public void print(String auth, String str) {
		System.out.println("使用auth参数进行认证处理");
		super.print(str);
	}

	public static void main(String[] args) {
		INewPrint print = new AdapterPrint1();
		print.print("Auth", "你好");
	}
	
}
