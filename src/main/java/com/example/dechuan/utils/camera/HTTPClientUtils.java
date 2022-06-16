package com.example.dechuan.utils.camera;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

;

/***
 * @description httpclient工具类
 */
public class HTTPClientUtils {
	private static Logger logger = LoggerFactory.getLogger(HTTPClientUtils.class);

	public static String imgUpload(String path, String imageFilePath) throws ClientProtocolException, IOException{
		// 2. 将所有需要上传元素打包成HttpEntity对象
		String Authorization = loginSupos();//获取ticket
		HttpEntity reqEntity = MultipartEntityBuilder.create()
				.addBinaryBody("file", new File(imageFilePath)).build();
		// 3. 创建HttpPost对象，用于包含信息发送post消息
		HttpPost httpPost = new HttpPost(path);
		httpPost.addHeader("Accept", "*/*");
		httpPost.addHeader("Authorization", Authorization);
		httpPost.addHeader("Connection", "keep-alive");
		httpPost.setEntity(reqEntity);
		// 4. 创建HttpClient对象，传入httpPost执行发送网络请求的动作
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);
		// 5. 获取返回的实体内容对象并解析内容
		HttpEntity resultEntity = response.getEntity();
		String responseMessage = "";
		try{

			if(resultEntity!=null){
				InputStream is = resultEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuffer sb = new StringBuffer();
				String line = "";
				while((line = br.readLine()) != null){
					sb.append(line);
				}
				responseMessage = sb.toString();
				logger.info("图片上传结束"+ responseMessage);
			}
			EntityUtils.consume(resultEntity);
		}finally{
			if (null != response){
				response.close();
			}
		}
		return responseMessage;
	}

	public static String sendEamil(String path,String alarm) throws ClientProtocolException, IOException{
		// 2. 将所有需要上传元素打包成HttpEntity对象
		String Authorization = loginSupos();//获取ticket

		JSONObject eamilJson = new JSONObject();
		String user[] = {"admin"};
		eamilJson.put("sender","scp");
		eamilJson.put("source", "system");
		eamilJson.put("type", "stationLetter");
		eamilJson.put("receivers", user);
		JSONObject contentJson = new JSONObject();
		contentJson.put("encoding","UTF-8");
		contentJson.put("text",alarm);
		contentJson.put("voice",1);
		eamilJson.put("content",contentJson);
		StringEntity reqEntity = new StringEntity(eamilJson.toString(), "utf-8");
		// 3. 创建HttpPost对象，用于包含信息发送post消息
		HttpPost httpPost = new HttpPost(path);
		httpPost.addHeader("Accept", "*/*");
		httpPost.addHeader("Authorization", Authorization);
		httpPost.addHeader("Connection", "keep-alive");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setEntity(reqEntity);
		// 4. 创建HttpClient对象，传入httpPost执行发送网络请求的动作
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);
		// 5. 获取返回的实体内容对象并解析内容
		HttpEntity resultEntity = response.getEntity();
		String responseMessage = "";
		try{
			if(resultEntity!=null){
				InputStream is = resultEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuffer sb = new StringBuffer();
				String line = "";
				while((line = br.readLine()) != null){
					sb.append(line);
				}
				responseMessage = sb.toString();
				System.out.println("站内信推送结束"+ responseMessage);
			}
			EntityUtils.consume(resultEntity);
		}finally{
			if (null != response){
				response.close();
			}
		}
		return responseMessage;
	}

	/**
	 * 获取登录ticket
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String loginSupos() throws ClientProtocolException, IOException{
			String Authorization="";
			JSONObject loginJson = new JSONObject();
			loginJson.put("username","***");
			loginJson.put("password","***");
			loginJson.put("autoLogin",true);
			loginJson.put("type","account");
			StringEntity reqEntity = new StringEntity(loginJson.toString(), "utf-8");
			HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/api/auth/users/login");
			httpPost.addHeader("Accept", "*/*");
			httpPost.addHeader("Connection", "keep-alive");
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(reqEntity);
			// 4. 创建HttpClient对象，传入httpPost执行发送网络请求的动作
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(httpPost);
			// 5. 获取返回的实体内容对象并解析内容
			HttpEntity resultEntity = response.getEntity();

			try{
				if(resultEntity!=null){
					InputStream is = resultEntity.getContent();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					StringBuffer sb = new StringBuffer();
					String line = "";
					while((line = br.readLine()) != null){
						sb.append(line);
					}
					Authorization = sb.toString();
					net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(Authorization);
					String ticket = (String) jsonObject.get("ticket");
					Authorization="Bearer "+ticket;
					System.out.println("获取用户ticket结束"+Authorization);
					return Authorization;
				}
				EntityUtils.consume(resultEntity);
			}finally{
				if (null != response){
					response.close();
				}
			}
			return  Authorization;

	}
}
