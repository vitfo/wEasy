package cz.vitfo.database.model;

import java.util.Date;

public class TrackInfo {

	private String ip;
	private String url;
	private String session;
	private Date date;
	
	public TrackInfo(String ip, String url, String session) {
		this.date = new Date();
		this.ip = ip;
		this.url = url;
		this.session = session;
	}

	public String getIp() {
		return ip;
	}

	public String getUrl() {
		return url;
	}

	public String getSession() {
		return session;
	}

	public Date getDate() {
		return date;
	}
	
}
