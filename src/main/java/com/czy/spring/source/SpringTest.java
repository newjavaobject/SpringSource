package com.czy.spring.source;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
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
		System.out.println(test.getTest());
		
//		User user = (User) bf.getBean("uu");
//		System.out.println(user.getEmail());
	}
}
