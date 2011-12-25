package com.daodao.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class SearchWord {

	/**
	 * @param args
	 */
	public static void main(String[] args) {/*
											 * List<String> prefixes = new
											 * ArrayList<String>();
											 * prefixes.add("dis");
											 * prefixes.add("un");
											 * prefixes.add("im");
											 * prefixes.add("in");
											 * prefixes.add("de");
											 * prefixes.add("ant");
											 * prefixes.add("non");
											 * prefixes.add("ir");
											 * prefixes.add("il"); new
											 * SearchWord
											 * ().matchWord("C:/雅思词汇TXT版.txt",
											 * "C:/雅思词汇TXT版_result1.txt",
											 * prefixes, true); // new
											 * SearchWord
											 * ().matchWord("C:/新概念英语全四册单词大全.txt"
											 * , //
											 * "C:/新概念英语全四册单词大全_result1.txt",
											 * prefixes, true);
											 * prefixes.clear();
											 * prefixes.add("ion");
											 * prefixes.add("ance");
											 * prefixes.add("ence");
											 * prefixes.add("ness");
											 * prefixes.add("ment");
											 * prefixes.add("al");
											 * prefixes.add("age");
											 * prefixes.add("ure");
											 * prefixes.add("th");
											 * prefixes.add("ty");
											 * prefixes.add("ity");
											 * prefixes.add("dom");
											 * prefixes.add("ship");
											 * prefixes.add("hood");
											 * prefixes.add("ism");
											 * prefixes.add("ic"); new
											 * SearchWord
											 * ().matchWord("C:/雅思词汇TXT版.txt",
											 * "C:/雅思词汇TXT版_result2.txt",
											 * prefixes, false); // new
											 * SearchWord
											 * ().matchWord("C:/新概念英语全四册单词大全.txt"
											 * , //
											 * "C:/新概念英语全四册单词大全_result2.txt",
											 * prefixes, false);
											 * prefixes.clear();
											 * prefixes.add("er");
											 * prefixes.add("or");
											 * prefixes.add("ar");
											 * prefixes.add("ist");
											 * prefixes.add("air");
											 * prefixes.add("eer");
											 * prefixes.add("ate");
											 * prefixes.add("ier");
											 * prefixes.add("ese");
											 * prefixes.add("ish");
											 * prefixes.add("an");
											 * prefixes.add("ian");
											 * prefixes.add("ess"); new
											 * SearchWord
											 * ().matchWord("C:/雅思词汇TXT版.txt",
											 * "C:/雅思词汇TXT版_result3.txt",
											 * prefixes, false); // new
											 * SearchWord
											 * ().matchWord("C:/新概念英语全四册单词大全.txt"
											 * , //
											 * "C:/新概念英语全四册单词大全_result3.txt",
											 * prefixes, false);
											 */
		new SearchWord()
				.sendHttpReq("http://fanyi.youdao.com/openapi.do?keyfrom=englishtester&key=578714415&type=data&doctype=json&version=1.1&"
						+ "q=我");
	}

	public void sendHttpReq(String url) {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}

	}

	public void matchWord(String inputFileName, String outputFileName,
			List<String> prefixes, boolean isPrefix) {
		PrintWriter writer = null;
		try {
			Map<String, List<String>> result = new HashMap<String, List<String>>();
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					inputFileName)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				for (String prefix : prefixes) {
					if (isPrefix) {
						if (findWordByPrefix(line, prefix)) {
							if (!result.containsKey(prefix)) {
								result.put(prefix, new ArrayList<String>());
							}
							result.get(prefix).add(line);
						}
					} else {
						if (findWordByPostfix(line, prefix)) {
							if (!result.containsKey(prefix)) {
								result.put(prefix, new ArrayList<String>());
							}
							result.get(prefix).add(line);
						}

					}
				}
			}
			File outputFile = new File(outputFileName);
			if (outputFile.exists()) {
				outputFile.delete();
			}
			writer = new PrintWriter(new FileWriter(outputFile));
			for (Entry<String, List<String>> entry : result.entrySet()) {
				writer.println(entry.getKey());
				int index = 1;
				writer.println("=========================");
				for (String word : entry.getValue()) {
					writer.print(word);
					writer.print("  ");
					index++;
					if (index % 8 == 0) {
						writer.println();
					}
				}
				writer.println("count:" + entry.getValue().size());
				writer.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public boolean findWordByPostfix(String word, String prefix) {
		if (word.endsWith(prefix)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean findWordByPrefix(String word, String postfix) {
		if (word.startsWith(postfix)) {
			return true;
		} else {
			return false;
		}
	}
}
