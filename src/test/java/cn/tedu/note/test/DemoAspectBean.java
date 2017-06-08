package cn.tedu.note.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 相当于AOP切面， 是扩展功能的类 
 */
public class DemoAspectBean 
	implements InvocationHandler{
	
	DemoBeanIf bean;
	public DemoAspectBean(
			DemoBeanIf bean) {
		this.bean = bean;
	}
	/*
	 * 相当于 @Around 
	 */
	public Object invoke(
			Object proxy, 
			Method method, 
			Object[] args) 
				throws Throwable {
		//proxy 代理接口 
		//method 代理方法 
		//args 方法调用参数
		
		System.out.println("开始");
		//执行目标方法
		Object obj = method.invoke(bean, args);
		
		System.out.println("结束");
		return obj;
	}
}
