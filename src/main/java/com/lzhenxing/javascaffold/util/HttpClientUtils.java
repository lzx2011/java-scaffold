package com.lzhenxing.javascaffold.util;

import com.lzhenxing.javascaffold.common.CommonConst;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * http 请求 工具类 
 * @author
 * @version 
 * @since
 */
public final class HttpClientUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); 
	// 单例
	private HttpClientUtils() {}
		
	static CloseableHttpClient httpclient = null;
	
	static {
		// 初始化线程池
		RequestConfig params = RequestConfig.custom()
			.setConnectTimeout(200) // 连接超时2s
			.setSocketTimeout(400).build();
		
		PoolingHttpClientConnectionManager pccm = new PoolingHttpClientConnectionManager();
		pccm.setMaxTotal(200); //  连接池最大并发连接数
		pccm.setDefaultMaxPerRoute(20); // 单路由最大并发数
		
		httpclient = HttpClients.custom().setConnectionManager(pccm).setDefaultRequestConfig(params).build();
	}
	
	public enum REQUEST_METHOD {
		GET, POST
	}
	
	/**
	 * GET获取信息
	 * @param urlToRequest
	 * @param parameters
	 * @param timeout
	 * @return
	 * @throws IOException
	 */
	public static String get(String urlToRequest, Map<String, String> parameters, Integer timeout) {
		
		if (urlToRequest == null || urlToRequest.trim().length() == 0) return null;
		
		if (parameters == null) {
			parameters = new HashMap<String, String>();
		}
		String urlParameters = constructURLString(parameters);
		if (urlToRequest.indexOf("?") == -1) {
			urlToRequest += "?" + urlParameters;
		} else {
			urlToRequest += "&" + urlParameters;
		}
	    
	    HttpGet request = new HttpGet(urlToRequest);
	    if (timeout != null && timeout > 0) {
	    	RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout*2).build();
	    	request.setConfig(requestConfig);
	    }
	    
	    return invoke(request);
    }
	
	/**
	 * POST提交信息
	 * @param urlToRequest
	 * @param parameters
	 * @param timeout
	 * @return
	 */
	public static String post(String urlToRequest, Map<String, String> parameters, Integer timeout) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
          
        if (parameters != null && !parameters.isEmpty()) {
        	for (String key : parameters.keySet()) {
        		nvps.add(new BasicNameValuePair(key, parameters.get(key)));
			}
        }
          
        try {  
        	HttpPost post = new HttpPost(urlToRequest);  
        	if (timeout != null && timeout > 0) {
		    	RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout*2).build();
		    	post.setConfig(requestConfig);
		    }
        	post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
        	
        	return invoke(post);
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            logger.error("[HttpClientUtils][post][Unsupported Encoding Exception]", e);
        }
		return null;
	}
	
	private static String invoke(HttpUriRequest request) {  
          
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, "UTF-8"); 
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("[HttpClientUtils][invoke][method:" + request.getMethod() + " URI:" + request.getURI() + "] is request exception", e);
		} finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("[HttpClientUtils][invoke][method:" + request.getMethod() + " URI:" + request.getURI() + "] is closed exception", e);
				}
			}
		}
        return null;  
    }

	/**
	 * 使用代理方式发送post请求
	 */
	public static String doPostByProxy(String targetUrl,int targetPort,String proxyIp,int proxyPort,Map<String,String> parameters){
		String result = "";
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<>();

		if (parameters != null && !parameters.isEmpty()) {
			for (String key : parameters.keySet()) {
				formparams.add(new BasicNameValuePair(key, parameters.get(key)));
			}
		}

		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		// 依次是目标请求地址，端口号,协议类型
		HttpHost target = new HttpHost(targetUrl, targetPort, "http");
		// 依次是代理地址，代理端口号，协议类型
		HttpHost proxy = new HttpHost(proxyIp, proxyPort, "http");
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

		// 请求地址
		HttpPost httpPost = new HttpPost(targetUrl);
		httpPost.setConfig(config);

		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse response = closeableHttpClient.execute(
					target, httpPost);
			// getEntity()
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				result = EntityUtils.toString(httpEntity, "UTF-8");
				// 打印响应内容
//				System.out.println("response:"
//						+ EntityUtils.toString(httpEntity, "UTF-8"));
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	return result;

	}

	/**
	 * Expand the map of parameters to construct a URL string
	 * 
	 * @param parameters
	 * @return
	 */
	private static String constructURLString(Map<String, String> parameters) {
		StringBuffer url = new StringBuffer();

		boolean first = true;

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			try {
				// type checking, we can get nulls in here
				if ((entry.getValue() == null) || (entry.getKey() == null)) {
					continue;
				}
				if (entry.getValue().length() == 0) {
					continue;
				}

				if (first) {
					first = false;
				} else {
					url.append("&");
				}
				url.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
				logger.error("[HttpClientUtils][constructURLString][Unsupported Encoding Exception]", ex);
			}
		}

		return url.toString();
	}

	public static String mapParamToString(Map<String, ?> params) {
		String postcontent = "";
		Map.Entry entry;
		if (params != null && params.size() > 0) {
			for (Iterator var3 = params.entrySet().iterator(); var3
					.hasNext(); postcontent = postcontent + (String) entry.getKey() + "=" + entry.getValue() + "&") {
				entry = (Map.Entry) var3.next();
			}
		}

		if (postcontent.length() > 0 && postcontent.endsWith("&")) {
			postcontent = postcontent.substring(0, postcontent.length() - 1);
		}

		return postcontent;
	}

	public static String sendPost(String url, String param, boolean isProxy) {
		logger.info(param);
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = null;
			if(isProxy){//使用代理模式
				@SuppressWarnings("static-access")
				Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(CommonConst.PROXY_IP, Integer.valueOf(CommonConst.PROXY_PORT)));
				conn = (HttpURLConnection) realUrl.openConnection(proxy);
			}else{
				conn = (HttpURLConnection) realUrl.openConnection();
			}

			// 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "utf8"); // 8859_1
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result = "{\"status\":-1,\"result\":\"\",\"msg\":\"发送 POST 请求出现异常！\"}";
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		return result;
	}

	public static int sendHead(String url) throws Exception{
		int code = 1000;
		//try{
			HttpHead head = new HttpHead(url);
			HttpResponse response = httpclient.execute(head);
			code = response.getStatusLine().getStatusCode();
		//}catch (IOException e){
		//	logger.error("head failed " + e.getMessage());
		//}catch (Exception e){
		//	logger.error("other exception " + e.getMessage());
		//}
		return code;
	}

	public static void main(String[] args){
		String url = "";
		//System.out.println(sendHead(url));
	}

	
}

