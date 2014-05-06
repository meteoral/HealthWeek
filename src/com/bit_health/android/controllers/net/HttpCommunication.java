package com.bit_health.android.controllers.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.bit_health.android.controllers.IProgressCallback;
import android.util.Pair;

public class HttpCommunication {

//	static public String post(String url, Map<String, String> headers,
//			String body) {
//		HttpClient httpClient = new HttpClient();
//		HttpHost host = new HttpHost(url);
//		HttpPost post = new HttpPost();
//		for (Map.Entry<String, String> entry : headers.entrySet()) {
//			post.addHeader(entry.getKey(), entry.getValue());
//		}
//		post.addHeader("connection", "keep-alive");
//		post.addHeader("Charsert", "UTF-8");
//		try {
//			post.setEntity(new StringEntity(body));
//			HttpResponse response = httpClient.execute(host, post);
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				return EntityUtils.toString(response.getEntity());
//			}
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}

//	static public boolean downloadFile(String url, FileOutputStream out,
//			IProgressCallback callback) {
//		InputStream in = null;
//		try {
//			URL uri = new URL(url);
//			HttpURLConnection conn;
//			conn = (HttpURLConnection) uri.openConnection();
//			conn.setConnectTimeout(15000);
//			// 得到响应码
//			int res = conn.getResponseCode();
//			if (res == HttpStatus.SC_OK) {
//				byte[] data = new byte[1024 * 8];
//				int readLen = 0;
//				int hasReadLen = 0;
//				in = conn.getInputStream();
//				int iLength = in.available();
//				while ((readLen = in.read(data)) > 0) {
//					out.write(data, 0, readLen);
//					hasReadLen = hasReadLen + readLen;
//					if (callback != null) {
//						callback.notifyProgressChanged(hasReadLen, iLength, "");
//					}
//				}
//				in.close();
//				in = null;
//				return true;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (in != null) {
//			try {
//				in.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			in = null;
//		}
//		return false;
//
//	}
	static public boolean downloadFile(String url, FileOutputStream out,
			IProgressCallback callback) {
		HttpClient httpClient = new HttpClient();
		//设置连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		GetMethod getMethod = new GetMethod(url);
		//设置get请求超时为5秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if(statusCode == HttpStatus.SC_OK){
				byte[] data = new byte[1024 * 8];
				int readLen = 0;
				int hasReadLen = 0;
				InputStream in = getMethod.getResponseBodyAsStream();
				int iLength = in.available();
				while ((readLen = in.read(data)) > 0) {
					out.write(data, 0, readLen);
					hasReadLen = hasReadLen + readLen;
					if (callback != null) {
						callback.notifyProgressChanged(hasReadLen, iLength, "");
					}
				}
				in.close();
				in = null;
				return true;
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	static public String get(String url, Map<String, String> params)
			throws IOException {
		if (params != null && params.size() > 0) {
			url = url + "?";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				url = url + entry.getKey() + "=" + entry.getValue() + "&";
			}
			// 去掉最后一个"&"
			url = url.substring(0, url.length() - 1);
		}

		HttpClient httpClient = new HttpClient();
		//设置连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		GetMethod getMethod = new GetMethod(url);
		//设置get请求超时为5秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		int statusCode = 0;
		String returnValue = null;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			String ackOk = "0";
			InputStreamReader inputreader = null;
			if (statusCode == HttpStatus.SC_OK) {
				inputreader = new InputStreamReader(getMethod.getResponseBodyAsStream(), "UTF-8");
				int ch;
				StringBuilder sb2 = new StringBuilder();
				while ((ch = inputreader.read()) != -1) {
					sb2.append((char) ch);
				}
				ackOk = sb2.toString();
			}
			
			if (inputreader == null) {
				returnValue = null;
			} else {
				inputreader.close();
				returnValue = ackOk;
			}
			return returnValue;
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}
//	static public String get(String url, Map<String, String> params)
//			throws IOException {
//		if (params != null && params.size() > 0) {
//			url = url + "?";
//			for (Map.Entry<String, String> entry : params.entrySet()) {
//				url = url + entry.getKey() + "=" + entry.getValue() + "&";
//			}
//			// 去掉最后一个"&"
//			url = url.substring(0, url.length() - 1);
//		}
//
//		URL _url = new URL(url);
//		HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
//		// DataOutputStream outStream = null;
//		conn.setConnectTimeout(15000);
//		// conn.setRequestProperty("accept", "*/*");
//		// conn.setRequestProperty("connection", "Keep-Alive");
//		// conn.setRequestProperty("user-agent",
//		// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//		// // 建立实际的连接
//		// conn.connect();
//		// try {
//		// outStream = new DataOutputStream(conn.getOutputStream());
//		// } catch (IOException e) {
//		// System.out.print("获取输出流失败");
//		// return null;
//		// }
//
//		// 得到响应码
//		int res = conn.getResponseCode();
//		String ackOk = "0";
//		InputStreamReader inputreader = null;
//		if (res == HttpStatus.SC_OK) {
//			inputreader = new InputStreamReader(conn.getInputStream(), "UTF-8");
//			int ch;
//			StringBuilder sb2 = new StringBuilder();
//			while ((ch = inputreader.read()) != -1) {
//				sb2.append((char) ch);
//			}
//			ackOk = sb2.toString();
//		}
//		String returnValue = null;
//		if (inputreader == null) {
//			returnValue = null;
//		} else {
//			inputreader.close();
//			conn.disconnect();
//			returnValue = ackOk;
//		}
//
//		return returnValue;
//	}

	static public String postForm(String url, Map<String, String> params,
			Map<String, Pair<String, String>> files, String session,
			IProgressCallback callback) throws IOException {
		String CHARSET = "UTF-8";
		PostMethod post = new PostMethod(url);
		List<Part> parts = new ArrayList<Part>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			StringPart strpart = new  StringPart(entry.getKey(),entry.getValue());
			strpart.setCharSet(CHARSET);
			parts.add(strpart);
		}
		if (files != null) {
			Iterator<Entry<String, Pair<String, String>>> iter = files
					.entrySet().iterator();
			while (iter.hasNext()) {
				// 发送数据
				Entry<String, Pair<String, String>> file = iter.next();

				parts.add(new FilePart(file.getKey(), new File(file.getValue().second)));
			}
		}
		
		//设置post请求超时为30秒
		post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,30000);
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		post.setRequestEntity(new MultipartRequestEntity(parts
				.toArray(new Part[0]), post.getParams()));
		
		HttpClient clients = new HttpClient();
		int status = clients.executeMethod(post);
		
		InputStreamReader inputreader = null;
		String ackOk = "0";
		if (status == HttpStatus.SC_OK) {
			// 在getInputStream()函数调用的时候，就会把准备好的http请求(http头+http正文)发送到服务器，然后返回一个输入流，用于读取服务器对于此次http请求的返回信息
			inputreader = new InputStreamReader(post.getResponseBodyAsStream(), CHARSET);
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = inputreader.read()) != -1) {
				sb2.append((char) ch);
			}
			ackOk = sb2.toString();
		}
		
		String returnValue = null;
		if (inputreader == null) {
			returnValue = null;
		} else {
			inputreader.close();
			returnValue = ackOk;
		}

		return returnValue;
	}

//	static public String postForm(String url, Map<String, String> params,
//			Map<String, Pair<String, String>> files, String session,
//			IProgressCallback callback) throws IOException {
//
//		String BOUNDARY = java.util.UUID.randomUUID().toString();
//		String PREFIX = "--", LINEND = "\r\n";
//		String MULTIPART_FROM_DATA = "multipart/form-data";
//		String CHARSET = "UTF-8";
//		URL _url = new URL(url);// 构造一个URL对象
//		HttpURLConnection conn = (HttpURLConnection) _url.openConnection();// 使用HttpURLConnection打开连接
//		DataOutputStream outStream = null;
//		conn.setConnectTimeout(15000);// 设置超时时间
//		conn.setDoInput(true); // 设置输入流
//		conn.setDoOutput(true); // 设置输出流
//		conn.setUseCaches(false); // Post请求不能使用缓存
//		conn.setRequestMethod("POST");// 设置请求方式为Post
//		conn.setRequestProperty("connection", "keep-alive");// 设置维持长连接
//		conn.setRequestProperty("Charsert", "UTF-8");// 设置文件字符集
//		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
//				+ ";boundary=" + BOUNDARY);// 设置文件类型---------这个是什么类型的文件？
//		if (!TextUtils.isEmpty(session)) {
//			conn.setRequestProperty("Cookie", session);
//		}
//
//		// 首先组拼文本类型的参数，即Post参数
//		StringBuilder sb = new StringBuilder();
//		for (Map.Entry<String, String> entry : params.entrySet()) {
//			sb.append(PREFIX);
//			sb.append(BOUNDARY);
//			sb.append(LINEND);
//			sb.append("Content-Disposition: form-data; name=\""
//					+ entry.getKey() + "\"" + LINEND);
//			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
//			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
//			sb.append(LINEND);
//			sb.append(entry.getValue());
//			sb.append(LINEND);
//		}
//
//		try {
//			// conn.getOutputStream()会隐含的进行conn.connect()，根据HttpURLConnection对象的配置值生成http头
//			outStream = new DataOutputStream(conn.getOutputStream());
//		} catch (IOException e) {
//			System.out.print("获取输出流失败");
//			return null;
//		}
//		outStream.write(sb.toString().getBytes());
//		System.out.print(sb.toString());
//
//		if (files != null) {
//			Iterator<Entry<String, Pair<String, String>>> iter = files
//					.entrySet().iterator();
//			while (iter.hasNext()) {
//				// 发送数据
//				Entry<String, Pair<String, String>> file = iter.next();
//				StringBuilder sb1 = new StringBuilder();
//				sb1.append(PREFIX);
//				sb1.append(BOUNDARY);
//				sb1.append(LINEND);
//				sb1.append("Content-Disposition: form-data; name=" + "\""
//						+ file.getKey() + "\"; filename=\""
//						+ file.getValue().first + "\"" + LINEND);
//				sb1.append("Content-Type: application/octet-stream; charset="
//						+ CHARSET + LINEND);
//				sb1.append(LINEND);
//				outStream.write(sb1.toString().getBytes());
//				System.out.print(sb1.toString());
//
//				FileInputStream fis = new FileInputStream(
//						file.getValue().second);
//				int len = 0;
//				byte[] buffer = new byte[1024];
//				int hasUpload = 0;
//				int totalUpload = fis.available();
//				while ((len = fis.read(buffer)) != -1) {
//					outStream.write(buffer, 0, len);
//					hasUpload = hasUpload + len;
//					if (callback != null) {
//						callback.notifyProgressChanged(hasUpload, totalUpload,
//								"正在上传 " + file.getKey());
//					}
//				}
//				fis.close();
//				outStream.write(LINEND.getBytes());
//				outStream.write(LINEND.getBytes());
//			}
//		}
//
//		// 请求结束标志
//		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//		outStream.write(end_data);
//		outStream.flush();// 根据输入的内容生成http正文
//
//		int res = conn.getResponseCode();// 得到响应码
//		String ackOk = "0";
//		InputStreamReader inputreader = null;
//		if (res == HttpStatus.SC_OK) {
//			// 在getInputStream()函数调用的时候，就会把准备好的http请求(http头+http正文)发送到服务器，然后返回一个输入流，用于读取服务器对于此次http请求的返回信息
//			inputreader = new InputStreamReader(conn.getInputStream(), CHARSET);
//			int ch;
//			StringBuilder sb2 = new StringBuilder();
//			while ((ch = inputreader.read()) != -1) {
//				sb2.append((char) ch);
//			}
//			ackOk = sb2.toString();
//		}
//		outStream.close();
//
//		String returnValue = null;
//		if (inputreader == null) {
//			returnValue = null;
//		} else {
//			inputreader.close();
//			conn.disconnect();// 连接完成之后关闭HttpURLConnection连接
//			returnValue = ackOk;
//		}
//
//		return returnValue;
//	}
}
