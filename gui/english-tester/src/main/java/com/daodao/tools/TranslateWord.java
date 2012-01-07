package com.daodao.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daodao.dao.DictionaryDAO;
import com.daodao.model.DictionaryDO;
import com.daodao.service.TesterService;

public class TranslateWord {

	private static final String YOUDAO_URL = "http://fanyi.youdao.com/openapi.do?keyfrom=englishtester&key=578714415&type=data&doctype=json&version=1.1&q=";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		args = new String[] { "new_concept.txt", "新概念", "false" };
		String fileName = "sample_IELTS.txt";
		String sourceType = "IELTS";
		boolean externalTool = true;
		if (args != null && args.length >= 3) {
			fileName = args[0];
			sourceType = args[1];
			externalTool = Boolean.valueOf(args[2]);
		}

		translate(fileName, sourceType, externalTool);
		// synchronizeData();
	}

	public static void synchronizeData() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		DictionaryDAO dictionaryDAO = (DictionaryDAO) context
				.getBean("dictionaryDAO");
		DataSource dataSource = (DataSource) context.getBean("dataSource1");
		try {
			Connection conn = dataSource.getConnection();
			ResultSet rs = conn.createStatement().executeQuery(
					"select en,source,zh from app.dictionary");
			while (rs.next()) {
				DictionaryDO dictionaryDO = new DictionaryDO();
				dictionaryDO.setAccurate(0);
				dictionaryDO.setEn(rs.getString("en"));
				dictionaryDO.setSource(rs.getString("source"));
				dictionaryDO.setZh(rs.getString("zh"));
				dictionaryDAO.saveEntity(dictionaryDO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void translate(String fileName, String sourceType,
			boolean externalTool) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		TesterService testerService = (TesterService) context
				.getBean("testerService");
		HttpClient client = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					TranslateWord.class.getClassLoader().getResourceAsStream(
							fileName)));
			String word = null;
			List<DictionaryDO> dictionaryDOs = new ArrayList<>();
			while ((word = reader.readLine()) != null) {
				if (word.trim().length() == 0) {
					continue;
				}
				DictionaryDO dictionaryDO = null;
				if (externalTool) {

					dictionaryDO = translateFromYouDao(word);
				} else {
					dictionaryDO = translateFromLocal(word);
				}
				if (dictionaryDO != null) {
					dictionaryDO.setSource(sourceType);
					dictionaryDOs.add(dictionaryDO);
				}
				if (dictionaryDOs.size() >= 100) {
					testerService.batchSaveDictionaryWords(dictionaryDOs);
					dictionaryDOs.clear();
				}
			}
			if (dictionaryDOs.size() >= 0) {
				System.out.println(testerService.batchSaveDictionaryWords(
						dictionaryDOs).size());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static DictionaryDO translateFromYouDao(String word) {

		HttpClient client = new DefaultHttpClient();
		if (word.indexOf(" ") != -1) {
			word = word.replaceAll(" ", "%20");
		}
		HttpGet request = new HttpGet(YOUDAO_URL + word);
		try {
			HttpResponse response = client.execute(request);
			String content = EntityUtils.toString(response.getEntity());
			JSONObject jsonAll = JSONObject.fromObject(content);
			// String phonetic = basicObj.getString("phonetic");
			StringBuilder explainStr = new StringBuilder();
			if (jsonAll.containsKey("basic")) {
				JSONObject basicObj = jsonAll.getJSONObject("basic");
				JSONArray explains = basicObj.getJSONArray("explains");
				for (int index = 0; index < explains.size(); index++) {
					explainStr.append(explains.get(index));
				}
			} else {
				explainStr.append(jsonAll.getString("translation"));
			}
			// System.out.println(word + "\t" + explainStr.toString());
			DictionaryDO dictionaryDO = new DictionaryDO();
			dictionaryDO.setAccurate(0);
			dictionaryDO.setEn(word);
			dictionaryDO.setZh(explainStr.toString());
			// api request limit under 1000 times
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return dictionaryDO;
			}
			return dictionaryDO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.err.println("Error in " + word);
			return null;
		}

	}

	private static DictionaryDO translateFromLocal(String word) {

		try {
			String en = CommonTool.removeChinese(word);
			String cn = null;
			if (en != null && en.length() > 0) {
				cn = word.substring(en.length());
				if (cn != null) {
					cn = cn.trim();
				}
			}
			DictionaryDO dictionaryDO = new DictionaryDO();
			dictionaryDO.setAccurate(0);
			dictionaryDO.setEn(en);
			dictionaryDO.setZh(cn);
			return dictionaryDO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.err.println("Error in " + word);
			return null;
		}

	}
}
