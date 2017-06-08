package cn.tedu.note.test;

import java.lang.reflect.Proxy;

public class ProxyDemo {

	public static void main(String[] args) {
		DemoBeanIf bean = 
			new DemoBeanImpl();
		DemoAspectBean aspect = 
			new DemoAspectBean(bean);
		
		//使用 Java 提供的动态代理
		Object obj = Proxy.newProxyInstance(
			DemoBeanIf.class.getClassLoader(),
			new Class[]{DemoBeanIf.class},
			aspect);
		//返回的对象 obj 是实现DemoBeanIf接口的
		//一个匿名子类，就包含hello()方法
		//这个方法如果调用将执行aspect的invoke
		//方法
		DemoBeanIf proxyBean=
				(DemoBeanIf) obj;
		proxyBean.hello();
		bean.hello();
	}

}
