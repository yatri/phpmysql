package com.example.phpmysql;

public class District {
	private int district_id;
	private String district_name;
	
	public District(int district_id, String district_name) {
		super();
		this.district_id = district_id;
		this.district_name = district_name;
	}
	public int getDistrict_id() {
		return district_id;
	}
	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	@Override
	public String toString() {
		return getDistrict_name();
	}
	
	
	
}
