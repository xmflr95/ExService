package Util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import VO.NewsVO;

public class JsonUtil {
	// JSON 처리 함수
	public ArrayList<String> getKeyword() throws Exception {
		ArrayList<String> keyList = new ArrayList<String>();
		
		JSONParser parser = new JSONParser();
		
		File jfile = new File("C:/Users/xmflr/Desktop/test/keyword.json");
		
		if (!jfile.exists()) {
			return null;
		} else {
			FileReader file = new FileReader(jfile);
			Object obj = parser.parse(file);
			
			JSONObject jsonObject = (JSONObject) obj;
			
			JSONArray keywordArray = (JSONArray) jsonObject.get("keyword");
			
			for (int i = 0; i < keywordArray.size(); i++) {
				JSONObject nameObject = (JSONObject) keywordArray.get(i);
				
				String companyName = nameObject.get("name").toString();
				
				keyList.add(companyName);
			}
		}
		
		return keyList;		
	}
	
	public void setKeyword(ArrayList<NewsVO> newsList) throws Exception {
		int size = newsList.size();
		
		if (size == 0) {
			System.out.println("내보낼 자료가 없습니다.");
		} else {
			JSONObject jsonObject = new JSONObject();
			
			JSONArray newsArray = new JSONArray();
			
			JSONObject newsInfo = new JSONObject();
			
			for (int i = 0; i < size; i++) {
				NewsVO news = newsList.get(i);
				
				newsInfo.put("title", news.getTitle());
				newsInfo.put("requestURL", news.getRequestURL());
				newsInfo.put("date", news.getDate());
				newsInfo.put("content", news.getDate());
				newsInfo.put("media", news.getMedia());
				newsInfo.put("keyword", news.getKeyword());
				
				newsArray.add(newsInfo);
				
				newsInfo = new JSONObject();
			}
			
			jsonObject.put("news", newsArray);
			
			String jsonInfo = jsonObject.toJSONString();
			
			System.out.println("☆★☆★☆★☆★☆★☆★☆★ << 작업 완료 >> ☆★☆★☆★☆★☆★☆★☆★");
			
			// JSON 생성
			FileWriter file = new FileWriter("C:/Users/xmflr/Desktop/test/news.json", false);
			file.write(jsonInfo);
			file.flush();
			file.close();
		}
	}
}
