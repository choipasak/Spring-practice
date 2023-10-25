package com.spring.myweb.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
	
	private final IWeatherMapper mapper;
	
	public void getShortTermForecase(String area1, String area2) {
		
		LocalDateTime ldt = LocalDateTime.now();
		String baseDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(ldt);
		log.info("baseDate: {}", baseDate);
		
		Map<String , String> map = mapper.getCoord(area1.trim(), area2.trim());
		log.info("좌표 결과:: {}", map);
		
		
		
		try {
			
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=erFQ5bYlGnGbsZNaWS5uiszZD7TT5UtOvPPDofAvur8z2Jbd8HwuH5dshJrQQkWzSMnu7oYCk0R4IEplMN9nAA%3D%3D"); /*Service key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("200", "UTF-8")); /*한 페이지 결과 수 = 조회할 행의 줄 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML, 응답받고자 하는 타입*/
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*발표 날짜*/
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0200", "UTF-8")); /*발표(정시단위)*/
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(map.get("NX")), "UTF-8")); /*예보지점의 X 좌표값*/
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(map.get("NY")), "UTF-8")); /*예보지점의 Y 좌표값*/
	        
	        log.info("완성된 url: {}", urlBuilder.toString());
	        
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString()); //StringBuilder의 toString()으로 받은 것을 알 수 있다.
	        
	        //StringBuilder 객체를 String 으로 변환
	        String jsonString = sb.toString();// json은 빌더를 안받음.
	        
	        JSONParser parser = new JSONParser();
	        
	        JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
	        // parse() -> 리턴 타입: Object -> JSONObject로 형 변환
			
	        //Parser온라인 웹에서 parse해준 결과에서 response부터 시작해서 접근해서 값을 가져와야한다.
	        //"response" 라는 이름의 키에 해당하는 JSON 데이터를 가져옵니다.
	        JSONObject response = (JSONObject) jsonObject.get("response");
	        
	        //response 안에서 body 키에 해당하는 JSON 데이터를 가져옵니다.
	        JSONObject body = (JSONObject) response.get("body");
	        
	        //items의 item 가져오기 -> 2번 들어감.
	        //body 안에서 item 키에 해당하는 JSON 데이터 중 item 키를 가진 JSON 데이터를 가져옵니다. -> 최종 목적지임 -> 타입도 JSONArray가 된다.
	        //item 키에 해당하는 JSON 데이터는 여러 값이기 때문에 배열의 문법을 제공하는 객체로 받습니다.
	        JSONArray itemArray = (JSONArray) ((JSONObject) body.get("items")).get("item");
	        
	        //item 내부의 각각의 객체에 대한 반복문을 작성합니다.
	        for(Object obj : itemArray) {
	        	//이쪽으로 덩어리가 하나씩 올 때마다
	        	
	        	//형 변환 먼저 진행
	        	JSONObject item = (JSONObject) obj;
	        	// "category" 키에 해당한는 단일 값을 가져옵니다.
	        	String category = (String) item.get("category");
	        	// "fcstValue" 키에 해당한는 단일 값을 가져옵니다.
	        	String fcstValue = (String) item.get("fcstValue");
	        	
	        	//덩어리중에 TMX가 들어간 덩어리도 1개고, TMN가 들어간 덩어리도 1개임.
	        	if(category.equals("TMX") || category.equals("TMN")) {
	        		log.info("category: {}, fcstValue: {}", category, fcstValue);
	        	}
	        	
	        }
	        
		} catch (Exception e) {
		}
		
	}

}
