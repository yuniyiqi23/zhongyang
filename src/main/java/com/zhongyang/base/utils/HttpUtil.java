package com.zhongyang.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.zhongyang.base.pojo.Header;
import com.zhongyang.base.pojo.ApiCaseDetail;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

	public final static String GET = "GET";
	public final static String POST = "POST";
	public final static String PATCH = "PATCH";
	public final static String PUT = "PUT";
	public final static String DELETE = "DELETE";

	private static Logger logger = Logger.getLogger(HttpUtil.class);

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String get(String url, Map<String, String> params) {
		try {
			// 组织参数
			List<BasicNameValuePair> paraList = new ArrayList<BasicNameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String name = key;
				String value = params.get(key);
				paraList.add(new BasicNameValuePair(name, value));
			}
			url = url + "?" + URLEncodedUtils.format(paraList, "utf-8");
			System.out.println("url = " + url);
			// get请求
			HttpGet get = new HttpGet(url);
			// http发包客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();

			// 执行发包，得到响应
			CloseableHttpResponse response = httpClient.execute(get);
			// 获取响应体信息
			HttpEntity httpEntity = response.getEntity();
			// InputStream is = httpEntity.getContent();
			return EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String get(String url, String requestString) {
		try {
			// json转map
			Map<String, Object> params = JSONObject.parseObject(requestString);
			// 组织参数
			List<BasicNameValuePair> paraList = new ArrayList<BasicNameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String name = key;
				String value = params.get(key).toString();
				paraList.add(new BasicNameValuePair(name, value));
			}
			url = url + "?" + URLEncodedUtils.format(paraList, "utf-8");
			System.out.println("url = " + url);
			// get请求
			HttpGet get = new HttpGet(url);
			get.setHeader("X-Lemonban-Media-Type", "lemonban.v1");
			// http发包客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();

			// 执行发包，得到响应
			CloseableHttpResponse response = httpClient.execute(get);
			// 获取响应体信息
			HttpEntity httpEntity = response.getEntity();
			// InputStream is = httpEntity.getContent();
			return EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 *            请求url
	 * @return
	 */
	public static String post(String url, String requestString) {
		String entityString = null;
		try {
			// post请求
			HttpPost post = new HttpPost(url);
			post.setHeader("X-Lemonban-Media-Type", "lemonban.v1");
			// 组织参数

			// UrlEncodedFormEntity formEntity = new
			// UrlEncodedFormEntity(paraList,
			// "utf-8");
			StringEntity entity = new StringEntity(requestString,
					ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			// http发包客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 执行发包，得到响应
			CloseableHttpResponse response = httpClient.execute(post);
			// 获取响应头信息
//			Header[] headers = response.getAllHeaders();
//			for (Header header : headers) {
				// System.out.println(header.getName() + " : " +
				// header.getValue());
//			}

			// 获取响应体信息
			HttpEntity httpEntity = response.getEntity();
			// InputStream is = httpEntity.getContent();
			entityString = EntityUtils.toString(httpEntity);
			// System.out.println(entityString);
			return entityString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityString;
	}

	/**
	 * post
	 * @param apiDetail
	 * @return
	 */
	public static String post(ApiCaseDetail apiDetail) {
		String entityString = null;
		try {
			// post请求
			HttpPost post = new HttpPost(apiDetail.getApiInfo().getUrl());
			// 封装Header
			// post.setHeader("X-Lemonban-Media-Type", "lemonban.v1");
			List<Header> headerList = JSONObject.parseArray(apiDetail.getApiInfo().getHeaders(),
					Header.class);
			for (Header header : headerList) {
				header.setValue(ParamUtils.getReplaceStr(header.getValue()));
				post.setHeader(header.getName(), header.getValue());
			}
			// 组织参数
			// UrlEncodedFormEntity formEntity = new
			// UrlEncodedFormEntity(paraList,
			// "utf-8");
			StringEntity entity = new StringEntity(apiDetail.getRequestData(),
					ContentType.APPLICATION_JSON);
			post.setEntity(entity);
			// http发包客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 执行发包，得到响应
			CloseableHttpResponse response = httpClient.execute(post);
			// 获取响应体信息
			HttpEntity httpEntity = response.getEntity();
			// InputStream is = httpEntity.getContent();
			entityString = EntityUtils.toString(httpEntity);
			// System.out.println(entityString);
			return entityString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityString;
	}

	/**
	 *
	 * @param url
	 *            请求url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> params) {
		String entityString = null;
		try {
			// post请求
			HttpPost post = new HttpPost(url);
			// HttpEntity entity = new StringEntity(
			// "username=15158011234&password=96e79218965eb72c92a549dd5a330112",
			// ContentType.APPLICATION_FORM_URLENCODED);

			// 组织参数
			List<BasicNameValuePair> paraList = new ArrayList<BasicNameValuePair>();

			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String name = key;
				String value = params.get(key);
				paraList.add(new BasicNameValuePair(name, value));
			}

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					paraList, "utf-8");
			post.setEntity(formEntity);
			// http发包客户端
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 执行发包，得到响应
			CloseableHttpResponse response = httpClient.execute(post);
			// 获取响应体信息
			HttpEntity httpEntity = response.getEntity();
			// InputStream is = httpEntity.getContent();
			entityString = EntityUtils.toString(httpEntity);
			// System.out.println(entityString);
			return entityString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityString;
	}

    public static String get(ApiCaseDetail apiCaseDetail) {
//		TODO get方法
		return null;
    }

	public static String excute(ApiCaseDetail apiCaseDetail) {
		// 组织请求体的参数
		apiCaseDetail.setRequestData(ParamUtils.getReplaceStr(apiCaseDetail
				.getRequestData()));
		// 组织鉴权参数
		String authStr = apiCaseDetail.getApiInfo().getAuth();
		if(authStr != null && "T".equalsIgnoreCase(authStr)){
			String reqStr = apiCaseDetail.getRequestData();
			Object tokenObj = ParamUtils.getGlobalData("token");
			if(tokenObj != null){
				long timeStamp = System.currentTimeMillis() / 1000;
				// 组织token前50位 + 时间戳
				String str = tokenObj.toString().substring(0, 50) + timeStamp;
				// 加密签名
//				String sign = EncryptUtills.rsaEncrypt(str);
				// 将请求体转map
				Map<String, Object> reqDataMap = (Map<String, Object>)JSONObject.parse
						(reqStr);
				// 组织请求体参数
				reqDataMap.put("timestamp",timeStamp);
//				reqDataMap.put("sign", sign);
				String finalReqStr = JSONObject.toJSONString(reqDataMap);
				// 将数据重新放回apiCaseDetail
				apiCaseDetail.setRequestData(finalReqStr);
			}
		}

		// 获取Http请求类型
		String type = apiCaseDetail.getApiInfo().getType();

		String result = null;
		logger.info("发起" + type + "请求");
		if (HttpUtil.GET.equalsIgnoreCase(type)) {
			result = HttpUtil.get(apiCaseDetail);
		} else if (HttpUtil.POST.equalsIgnoreCase(type)) {
			result = HttpUtil.post(apiCaseDetail);
		} else if (HttpUtil.PUT.equalsIgnoreCase(type)) {
			result = HttpUtil.put(apiCaseDetail);
		} else if (HttpUtil.PATCH.equalsIgnoreCase(type)) {
			result = HttpUtil.patch(apiCaseDetail);
		}
		return result;
	}

	private static String patch(ApiCaseDetail apiCaseDetail) {
		// TODO patch方法
		return null;
	}

	private static String put(ApiCaseDetail apiCaseDetail) {
		// TODO put方法
		return null;
	}

	public static void main(String[] args){
//		System.out.println(str);
		String reqStr = "{\"member_id\": 1,\"amount\": 0}";
		String token = "VA0usIdIGoeoHXY3MNGxG6YnbjElai/47yVHFIxw1z6cLte85LXNiCBM3e4Np/2OClqGZpbpH1w5VA0usIdIGoeoHXY3MNGxG6YnbjElai/47yVHFIxw1z6cLte85LXNiCBM3e4Np/2OClqGZpbpH1w5";
		long timeStamp = System.currentTimeMillis() / 1000;
		// 组织token前50位 + 时间戳
		String str = token.substring(0, 50) + timeStamp;
		// 加密签名
//		String sign =  EncryptUtils.rsaEncrypt(str);
		// 将请求体转map
		Map<String, Object> reqDataMap = (Map<String, Object>)JSONObject.parse
				(reqStr);
		// 组织请求体参数
		reqDataMap.put("timeStamp",timeStamp);
//		reqDataMap.put("sign", sign);
		String finalReqStr = JSONObject.toJSONString(reqDataMap);
		System.out.println(finalReqStr);
	}

}
