package com.example.phpmysql;

public class Vdc {
	private int vdc_id;
	private String vdc_name;
	private int district_id;

	public int getVdc_id() {
		return vdc_id;
	}

	public void setVdc_id(int vdc_id) {
		this.vdc_id = vdc_id;
	}

	public String getVdc_name() {
		return vdc_name;
	}

	public void setVdc_name(String vdc_name) {
		this.vdc_name = vdc_name;
	}

	public int getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}
	

	public Vdc(int vdc_id, String vdc_name, int district_id) {
		super();
		this.vdc_id = vdc_id;
		this.vdc_name = vdc_name;
		this.district_id = district_id;
	}

	@Override
	public String toString() {
		return getVdc_name();
	}
	
}
