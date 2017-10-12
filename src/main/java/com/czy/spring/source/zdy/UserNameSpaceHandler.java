package com.czy.spring.source.zdy;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * SpringSource:com.czy.spring.source.zdy.UserNameSpaceHandler.java
 * @auth : chenzhiyu
 * @since : 2017年10月12日 下午3:08:43
 * 说明：
 */
public class UserNameSpaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
	}
	
}
