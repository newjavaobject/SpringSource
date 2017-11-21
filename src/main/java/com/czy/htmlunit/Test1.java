package com.czy.htmlunit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * SpringSource:com.czy.htmlunit.Test.java
 * 
 * @auth : chenzhiyu
 * @since : 2017年11月14日 上午9:01:52 说明：
 */
public class Test1 {
	@Test
	public void homePage() throws Exception {
		final WebClient webClient = new WebClient();
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		final HtmlPage page = webClient.getPage("http://www.sc.10086.cn/service/index.html?ts=1510622955236");
		
		final String pageAsXml = page.asXml();
		System.out.println(pageAsXml);
		
		
		HttpClient hc = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("http://www.sc.10086.cn/service/index.html?ts=1510622955236");
		HttpResponse response = hc.execute(httpGet);
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		System.out.println(EntityUtils.toString(response.getEntity()));
		httpGet.releaseConnection();
//		Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

//		final String pageAsText = page.asText();
//		System.out.println(pageAsText);
//		Assert.assertTrue(pageAsText
//				.contains("Support for the HTTP and HTTPS protocols"));
	}
}
