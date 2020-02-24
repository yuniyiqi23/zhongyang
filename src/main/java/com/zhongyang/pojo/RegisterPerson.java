package com.zhongyang.pojo;

public class RegisterPerson {
	private String mobilephone;
	private String pwd;
	
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "RegisterPerson [mobilephone=" + mobilephone + ", pwd=" + pwd
				+ "]";
	}

	
}
