package com.tcg.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;

public class JSONUtils {

	public static String getJSONStringFromFile(String path) {
		File f = new File(path);
		Scanner scanner;
		try {
			scanner = new Scanner(f);
			String json = scanner.useDelimiter("\\Z").next();
			scanner.close();
			return json;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	
	}
	
	public static JSONObject getJSONObjectFromFile(String path) {
		return new JSONObject(getJSONStringFromFile(path));
	}
	
	public static boolean objectExists(JSONObject jsonObject, String key) {
		Object o;
		try {
			o = jsonObject.get(key);
		} catch(Exception e) {
			return false;
		}
		return o != null;
	}
	
}
