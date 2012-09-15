package com.monkey.videocamera.service;

import java.util.List;
import java.util.Vector;

public class SeriviceManager {

	private static List<Service> serviceList;
	
	private SeriviceManager() {
		serviceList = new Vector<Service>();
	}
	
	public static void registService(Service svc) {
		synchronized(serviceList) {
			serviceList.add(svc);
		}
	}
	
	public static void stopServices() {
		synchronized(serviceList) {
			for ( Service s : serviceList ) {
				s.stopService();
			}
		}
	}
}
