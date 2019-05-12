package com.hit.driver;

import java.io.IOException;
import java.net.UnknownHostException;

import com.hit.client.CacheUnitClientObserver;
import com.hit.view.CacheUnitView;

public class CacheUnitClientDriver {
	// Our main function for the Client
	public static void main(String[] args) throws UnknownHostException, IOException {
			CacheUnitClientObserver cacheUnitClientObserver = new CacheUnitClientObserver();
			CacheUnitView view = new CacheUnitView();
			view.addPropertyChangeListener(cacheUnitClientObserver);
			view.start();
	}
} 
 