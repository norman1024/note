package cn.tedu.note.test;

public class DemoBeanImpl 
	implements DemoBeanIf {
	
	/**
	 * 实际调用的业务方法
	 */
	public void hello() {
		System.out.println("Hello World!"); 
	}
}
