package com.tcg.json;

import java.util.ArrayList;

public class tickets {
		private int price;
		private int lower_bound;
		private int upper_bound;
		private String start_date;
		private String end_date;
		private String product_key;
		private String title;
		private int travel_code;
		private int current_num;
		public ArrayList<user> user_data;
		
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public int getLower_bound() {
			return lower_bound;
		}
		public void setLower_bound(int lower_bound) {
			this.lower_bound = lower_bound;
		}
		public int getUpper_bound() {
			return upper_bound;
		}
		public void setUpper_bound(int upper_bound) {
			this.upper_bound = upper_bound;
		}
		public String getStart_date() {
			return start_date;
		}
		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}
		public String getEnd_date() {
			return end_date;
		}
		public void setEnd_date(String end_date) {
			this.end_date = end_date;
		}
		public String getProduct_key() {
			return product_key;
		}
		public void setProduct_key(String product_key) {
			this.product_key = product_key;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getTravel_code() {
			return travel_code;
		}
		public void setTravel_code(int travel_code) {
			this.travel_code = travel_code;
		}
		public int getCurrent_num() {
			return current_num;
		}
		public void setCurrent_num(int current_num) {
			this.current_num = current_num;
		}
}
