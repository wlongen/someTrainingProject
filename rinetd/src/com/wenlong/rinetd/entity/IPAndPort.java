package com.wenlong.rinetd.entity;

public class IPAndPort {

	private String jumpIP;
	private String jumpPort;
	private String targetIP;
	private String targetPort;


	public IPAndPort(String jumpIP, String jumpPort, String targetIP, String targetPort) {
		super();
		this.jumpIP = jumpIP;
		this.jumpPort = jumpPort;
		this.targetIP = targetIP;
		this.targetPort = targetPort;
	}

	public String getJumpIP() {
		return jumpIP;
	}

	public void setJumpIP(String jumpIP) {
		this.jumpIP = jumpIP;
	}

	public String getJumpPort() {
		return jumpPort;
	}

	public void setJumpPort(String jumpPort) {
		this.jumpPort = jumpPort;
	}

	public String getTargetIP() {
		return targetIP;
	}

	public void setTargetIP(String targetIP) {
		this.targetIP = targetIP;
	}

	public String getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(String targetPort) {
		this.targetPort = targetPort;
	}

	@Override
	public String toString() {
		return "IPAndPort [jumpIP=" + jumpIP + ", jumpPort=" + jumpPort + ", targetIP=" + targetIP + ", targetPort="
				+ targetPort + "]";
	}


}
