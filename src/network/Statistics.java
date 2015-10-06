package network;

import java.util.*;
import edu.uci.ics.jung.algorithms.cluster.*;

public class Statistics {
	public static Set<Long> getGiantConnectedComponent(Network g) {
		Set<Set<Long>> components = getConnectedComponent(g);
		Set<Long> giant = null;
		int size = 0;
		for(Set<Long> component : components) {
			if(size < component.size()) {
				size = component.size();
				giant = component;
			}
		}
		return giant;
	}
	
	public static Set<Set<Long>> getConnectedComponent(Network g) {
		WeakComponentClusterer<Long, Long> wcc = new WeakComponentClusterer<Long, Long>();
		Set<Set<Long>> components = wcc.transform(g);
		return components;
	}
}
