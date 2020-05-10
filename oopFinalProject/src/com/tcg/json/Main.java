package com.tcg.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
	static tickets[] data;
	static code_destinations[] destination;
	static int order_num;//訂單編號
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		order_num = 0;
		read_Input();
		search_available("馬達加斯加", "2020-03-26");
		book_travel("Shine", 100,"2020-03-26", "2020-04-04", 5);
		book_cancel("Shine", 0, 2);//Overload 多第三個參數代表減少人數
		search_booking("Shine", 0);
		book_cancel("Shine", 0);//沒有第三個參數代表取消訂單
		
		
	}
	
	
	private static void search_booking(String user_id, int order) {
		// TODO Auto-generated method stub
		int id = -1;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].user_data.size(); i++) {
				if(data[i].user_data.get(j).getID().contentEquals(user_id) && data[i].user_data.get(j).getOrder_number() == order)
				{
					System.out.println("行程編號: " + order);
					System.out.println("入住人數: " + data[i].user_data.get(j).getNum_people());
					System.out.println("出發日期: " + data[i].getStart_date() + " ,抵台日期: " + data[i].getEnd_date());
					System.out.println("總價: " + (data[i].getPrice())*(data[i].user_data.get(j).getNum_people()));
				}
			}
		}
		if(id == -1) {
			System.out.println("您輸入的身分識別碼/訂單編號有誤,請重新輸入");
		}
	}


	private static void book_cancel(String user_id, int order) {
		// TODO Auto-generated method stub
		int id = -1;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].user_data.size(); i++) {
				if(data[i].user_data.get(j).getID().contentEquals(user_id) && data[i].user_data.get(j).getOrder_number() == order)
				{
					int tmp = data[i].getCurrent_num();
					int n = data[i].user_data.get(j).getNum_people();
					data[i].setCurrent_num(tmp-n);
					data[i].user_data.remove(j);
					System.out.println("退訂成功,已取消您的預訂紀錄");
					return;
				}
			}
		}
		if(id == -1) {
			System.out.println("退訂/修改失敗,此訂位代號不存在");
			return;
		}
	}

	private static void book_cancel(String user_id, int order, int cancel_num) {
		// TODO Auto-generated method stub
		int id = -1;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].user_data.size(); i++) {
				if(data[i].user_data.get(j).getID().contentEquals(user_id) && data[i].user_data.get(j).getOrder_number() == order)
				{
					int tmp = data[i].getCurrent_num();
					int n = data[i].user_data.get(j).getNum_people();
					if((tmp-cancel_num) >= 0 && (n-cancel_num) > 0) {
						data[i].setCurrent_num(tmp-cancel_num);
						data[i].user_data.get(j).setNum_people(n-cancel_num);
					}
					else{
						System.out.println("修改過多人數!");
						return;
					}
					System.out.println("修改成功,已將您的人數變更為" + data[i].getCurrent_num());
					return;
				}
			}
		}
		if(id == -1) {
			System.out.println("退訂/修改失敗,此訂位代號不存在");
			return;
		}
	}


	private static void book_travel(String user_id, int travel_code,String start_date, String end_date, int num) {
		// TODO Auto-generated method stub
		int id = -1;
		for(int i = 0; i < data.length; i++) {
			if(data[i].getTravel_code() == travel_code && data[i].getStart_date().equals(start_date) && data[i].getEnd_date().equals(end_date)) {
				id = i;
				break;
			}
		}
		if(id == -1) {
			System.out.println("沒有這個行程!");
			return;
		}
		if((data[id].getCurrent_num() + num) >= data[id].getLower_bound()) {
			System.out.println("失敗 行程日期:" + start_date + " 到 " + end_date + "之 " + data[id].getTitle() + " 行程剩餘數量不足/機位已售罄");
			return;
		}
		
		user tmp = new user();
		tmp.setID(user_id);
		tmp.setNum_people(num);
		tmp.setOrder_number(order_num);
		data[id].user_data.add(tmp);
		int temp = data[id].getCurrent_num();
		data[id].setCurrent_num(temp + num);
		
		System.out.println("訂單編號: " + tmp.getOrder_number() + " ,使用者ID: " + tmp.getID());
		System.out.println("出發日期: " + data[id].getStart_date() + " ,抵台日期: " + data[id].getEnd_date());
		System.out.println("總價: " + (data[id].getPrice())*(num));
		System.out.println("入住人數: " + num + "人");
		
		order_num++;
		return;
	}


	public static void read_Input() {
		String json = JSONUtils.getJSONStringFromFile("./assets/travel_code.json");
		JSONParser parser = new JSONParser();
		int i = 0;
		try {
			org.json.simple.JSONArray tmpArr = (org.json.simple.JSONArray)parser.parse(json);
			destination = new code_destinations[103];
			for(Object obj : tmpArr) {
				org.json.simple.JSONObject tmpObj = (org.json.simple.JSONObject) obj;
				destination[i] = new code_destinations();
				destination[i].setTravel_code(Integer.valueOf((String)tmpObj.get("travel_code")));
				String tmp = (String) tmpObj.get("travel_code_name");
				destination[i].setPlace(tmp.split("．"));
		        i++;
			}
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		try{
			InputStreamReader isr = new InputStreamReader(new FileInputStream("./assets/trip_data_all.csv"));
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			i = 0;
			line = reader.readLine();
			data = new tickets[10139];
			while((line = reader.readLine()) != null) {
				String item[] = line.split(",");
				//System.out.println(item.length);
				//title,travel_code,product_key,price,start_date,end_date,lower_bound,upper_bound
				data[i] = new tickets();
				data[i].setTitle(item[0].trim());
				data[i].setTravel_code(Integer.valueOf(item[1].trim()));
				//System.out.println(data[i].getTravel_code());
				data[i].setProduct_key(item[2].trim());
				data[i].setPrice(Integer.valueOf(item[3].trim()));
				data[i].setStart_date(item[4].trim());
				data[i].setEnd_date(item[5].trim());
				data[i].setLower_bound(Integer.valueOf(item[6].trim()));
				data[i].setUpper_bound(Integer.valueOf(item[7].trim()));
				data[i].setCurrent_num(0);
				data[i].user_data = new ArrayList<user>();
				i++;
			}
			reader.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void search_available(String location, String start_date) {
		// TODO Auto-generated method stub
		int travel_code = -1;
		for(int i = 0; i < destination.length; i++) {
			String tmp[] = destination[i].getPlace();
			if(travel_code != -1)
				break;
			
			for(int j = 0; j < tmp.length; j++) {
				if(tmp[j].equals(location) == true) {
					System.out.println(destination[i].getTravel_code());
					travel_code = destination[i].getTravel_code();
					break;
				}
			}
		}
		
		for(int i = 0; i < data.length;i++) {
			int code = data[i].getTravel_code();
			String date = data[i].getStart_date();
			if(code == travel_code) {
				if(date.equals(start_date)) {
					System.out.println("行程:"+data[i].getTitle());
					System.out.println("價格:"+data[i].getPrice());
					System.out.println("最少出團人數:"+data[i].getLower_bound());
					System.out.println("最多出團人數:"+data[i].getUpper_bound());
					System.out.println("出發日期:"+data[i].getStart_date());
					System.out.println("回台抵達日期:"+data[i].getEnd_date());
				}
			}
		}
	}
}
