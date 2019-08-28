package org.linbo.demo.pattern.adapter;

public class AdpaterPrint2 implements INewPrint {
	
	private OldPrint oldPrint;
	
	public AdpaterPrint2(OldPrint oldPrint) {
		super();
		this.oldPrint = oldPrint;
	}

	@Override
	public void print(String auth, String str) {
		System.out.println("使用auth参数进行认证处理");
		oldPrint.print(str);
	}


	public static void main(String[] args) {
		OldPrint oldPrint = new OldPrint();
		INewPrint print = new AdpaterPrint2(oldPrint);
		print.print("Auth", "你好");
	}
}
