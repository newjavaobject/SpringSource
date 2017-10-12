package com.czy.spring.source.zdy;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * SpringSource:com.czy.spring.source.zdy.UserBeanDefinitionParser.java
 * @auth : chenzhiyu
 * @since : 2017年10月12日 下午3:01:33
 * 说明：
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
	

	@Override
	protected Class<?> getBeanClass(Element element) {
		return User.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		String id = element.getAttribute("id");
		String name = element.getAttribute("name");
		String email = element.getAttribute("email");
		
		if(StringUtils.hasText(id)){
			builder.addPropertyValue("id", id);
		}
		if(StringUtils.hasText(name)){
			builder.addPropertyValue("name", name);
		}
		if(StringUtils.hasText(email)){
			builder.addPropertyValue("email", email);
		}
	}
	
}
















