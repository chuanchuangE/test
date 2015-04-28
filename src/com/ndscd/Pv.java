package com.ndscd;

public class Pv {
	private String ip;
	private String url;
	private String ts;
	private String param;
	private int clientType;
	private int test;
	public Pv(){
		
	}
	public Pv(String ip, String url, String ts,int clientType) {
		super();
		this.ip = ip;
		this.url = url;
		this.ts = ts;
		this.clientType=clientType;
	}
	public Pv(String ip, String url, String ts,String param) {
		super();
		this.ip = ip;
		this.url = url;
		this.ts = ts;
		this.param=param;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public int getClientType() {
		return clientType;
	}
	public void setClientType(int clientType) {
		this.clientType = clientType;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
	
}
