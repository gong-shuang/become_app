package com.gs.learn.mixture.bean;

public class ClientScanResult {
	private String IpAddr;
	private String HWAddr;
	private String Device;
	private String HostName;
	private boolean IsReachable;

	public ClientScanResult(String ipAddr, String hWAddr, String device, boolean isReachable, String hostName) {
		super();
		IpAddr = ipAddr;
		HWAddr = hWAddr;
		Device = device;
		IsReachable = isReachable;
		HostName = hostName;
	}

	public String getIpAddr() {
		return IpAddr;
	}

	public void setIpAddr(String ipAddr) {
		IpAddr = ipAddr;
	}

	public String getHWAddr() {
		return HWAddr;
	}

	public void setHWAddr(String hWAddr) {
		HWAddr = hWAddr;
	}

	public String getDevice() {
		return Device;
	}

	public void setDevice(String device) {
		Device = device;
	}

	public String getHostName() {
		return HostName;
	}

	public void setHostName(String hostName) {
		HostName = hostName;
	}

	public void setReachable(boolean isReachable) {
		this.IsReachable = isReachable;
	}

	public boolean isReachable() {
		return IsReachable;
	}
}
