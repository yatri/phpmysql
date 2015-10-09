package com.example.phpmysql;

public class FormData {
	private int userid;
	private String name;
	private int district_id;
	private int vdcid;
	private String tole;
	private int wardno;
	private int comment_id;
	private String comment;
	private String district_name;
	private String vdc_name;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}

	public int getVdcid() {
		return vdcid;
	}

	public void setVdcid(int vdcid) {
		this.vdcid = vdcid;
	}

	public String getTole() {
		return tole;
	}

	public void setTole(String tole) {
		this.tole = tole;
	}

	public int getWardno() {
		return wardno;
	}

	public void setWardno(int wardno) {
		this.wardno = wardno;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public String getVdc_name() {
		return vdc_name;
	}

	public void setVdc_name(String vdc_name) {
		this.vdc_name = vdc_name;
	}

	public FormData(int userid, String name, int district_id, int vdcid,
			String tole, int wardno, String comment) {
		super();
		this.userid = userid;
		this.name = name;
		this.district_id = district_id;
		this.vdcid = vdcid;
		this.tole = tole;
		this.wardno = wardno;
		this.comment = comment;
	}

	public FormData(int userid, String name, int district_id, int vdcid,
			String tole, int wardno, int comment_id, String comment) {
		super();
		this.userid = userid;
		this.name = name;
		this.district_id = district_id;
		this.vdcid = vdcid;
		this.tole = tole;
		this.wardno = wardno;
		this.comment_id = comment_id;
		this.comment = comment;
	}

	public FormData() {

	}

	@Override
	public String toString() {
		return "FormData [userid=" + userid + ", name=" + name
				+ ", district_id=" + district_id + ", vdcid=" + vdcid
				+ ", tole=" + tole + ", wardno=" + wardno + ", comment="
				+ comment + "]";
	}

}
