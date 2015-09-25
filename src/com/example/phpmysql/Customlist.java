package com.example.phpmysql;

public class Customlist {
	private int fruit_resource;
	private String fruit_name;

	public Customlist(int fruit_resource, String fruit_name) {
		super();
		this.setFruit_resource(fruit_resource);
		this.setFruit_name(fruit_name);

	}
	public int getFruit_resource() {
		return fruit_resource;
	}

	public void setFruit_resource(int fruit_resource) {
		this.fruit_resource = fruit_resource;
	}

	public String getFruit_name() {
		return fruit_name;
	}

	public void setFruit_name(String fruit_name) {
		this.fruit_name = fruit_name;
	}

}