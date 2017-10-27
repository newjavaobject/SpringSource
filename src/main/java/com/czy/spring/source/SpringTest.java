package com.czy.spring.source;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.czy.spring.source.zdy.User;

/** 
* @author: 
* @version:  
* @since：2017年10月11日 下午11:50:55
* 类说明:
*/
@SuppressWarnings("deprecation")
public class SpringTest {
	public static void main(String[] args) {
		BeanFactory bf = new XmlBeanFactory(new ClassPathResource("spring-servlet.xml"));
		Test test = bf.getBean(Test.class);
		Test test1 = (Test) bf.getBean("test");
		System.out.println(test.getTest());
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-servlet.xml");
		Test test2 = (Test) ac.getBean("test");
		System.out.println(test2.getTest());
//		User user = (User) bf.getBean("uu");
//		System.out.println(user.getEmail());
	}
}
