package com.ssafy.house.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


@RequestMapping("/api/searchs")
@RestController
public class SearchController{
	
	static final String myID = "aG462FfNUrdac4EIKawC";
	static final String mySecret ="X54dAN58aG";
	@GetMapping()
	private ResponseEntity<?> getBlog(){
	//private String getBlog(){
		String clientId = myID;
        String clientSecret = mySecret;


        String text = null;
        try {
            text = URLEncoder.encode("고깃집", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseString = get(apiURL,requestHeaders);
        
        String[] fields = {"title", "link", "description"};
        Map<String, Object> result = getResult(responseString, fields);
        
        if(result.size() > 0) System.out.println("total -> " + result.get("total"));
        
		List<Map<String, Object>> items = (List<Map<String, Object>>) result.get("result");
        for(Map<String, Object> item : items) {
        	System.out.println("====================================================");
        	for(String field : fields) System.out.println(field + "->" + item.get(field));     	
        }
        //return responseString;
        if(result != null) {
        	return ResponseEntity.ok(result);
        } else {
        	return ResponseEntity.noContent().build();
        }
	}
	
	
	 private static String get(String apiUrl, Map<String, String> requestHeaders){
	        HttpURLConnection con = connect(apiUrl);
	        try {
	            con.setRequestMethod("GET");
	            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
	                con.setRequestProperty(header.getKey(), header.getValue());
	            }


	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
	                return readBody(con.getInputStream());
	            } else { // 오류 발생
	                return readBody(con.getErrorStream());
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("API 요청과 응답 실패", e);
	        } finally {
	            con.disconnect();
	        }
	    }


	    private static HttpURLConnection connect(String apiUrl){
	        try {
	            URL url = new URL(apiUrl);
	            return (HttpURLConnection)url.openConnection();
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
	        } catch (IOException e) {
	            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
	        }
	    }


	    private static String readBody(InputStream body){
	        InputStreamReader streamReader = new InputStreamReader(body);


	        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
	            StringBuilder responseBody = new StringBuilder();


	            String line;
	            while ((line = lineReader.readLine()) != null) {
	                responseBody.append(line);
	            }


	            return responseBody.toString();
	        } catch (IOException e) {
	            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
	        }
	    }
	    
	    public Map<String, Object> getResult(String response, String[] fields) {
			Map<String, Object> rtnObj = new HashMap<> ();
			
			try {
				JSONParser parser = new JSONParser();
				JSONObject result = (JSONObject) parser.parse(response);
				
				rtnObj.put("total", (long) result.get("total"));
				
				JSONArray items = (JSONArray) result.get("items");
				List<Map<String, Object>> itemList = new ArrayList<> ();
				
				for(int i = 0; i < items.size(); i++) {
					JSONObject item = (JSONObject) items.get(i);
					Map<String, Object> itemMap = new HashMap<> ();
					
					for(String field : fields) {
						itemMap.put(field, item.get(field));
					}
					itemList.add(itemMap);
				}
				
				rtnObj.put("result", itemList);
			} catch (Exception e) {
				System.out.println("getResult error -> " + "파싱 실패, " + e.getMessage());			
			}
			
			return rtnObj;
		}
	
}

