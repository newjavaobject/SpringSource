package com.czy.spring.source;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/** 
* @author: 
* @version:  
* @since：2017年10月11日 下午11:50:55
* 类说明:
*/
@SuppressWarnings("deprecation")
public class SpringTest {
	
	@Test
	public void Test11(){
		BeanFactory bf = new XmlBeanFactory(new ClassPathResource("spring-servlet.xml"));
//		Test1 test1 = bf.getBean(Test1.class);
		Test1 test11 = (Test1) bf.getBean("test1");
		System.out.println(test11.getTest());
		
//		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-servlet.xml");
//		Test1 test12 = (Test1) ac.getBean("test1");
//		System.out.println(test12.getTest());
	}
	
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {
		BeanFactory bf = new XmlBeanFactory(new ClassPathResource("spring-servlet.xml"));
		Test1 test1 = bf.getBean(Test1.class);
		Test1 Test11 = (Test1) bf.getBean("test1");
		System.out.println(test1.getTest());
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-servlet.xml");
		Test1 test12 = (Test1) ac.getBean("test1");
		System.out.println(test12.getTest());
//		User user = (User) bf.getBean("uu");
//		System.out.println(user.getEmail());
	}
}
