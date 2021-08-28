package score ;

import java.util.*;

public class RecallScorer{
	/**
	 * @param	test	prediction result
	 * @param	answer	ground truth
	 * @param	k_value	top k
	 * */
	@SuppressWarnings("unchecked")
	public static double getR( Map<Long,Double> test , Map<Long,Double> answer, int k_value){
		Map<Long, Integer> test_order = sortMap(test);
		Map<Long, Integer> answer_order = sortMap(answer);
		int right = 0;
		int i = 0;
		
		
		for(Long key: test_order.keySet()){
			if(i == k_value)
				break;
			
			if(answer_order.get(key) < k_value)
				right++;
			i++;
			
		}
		return ((double) right) * 1.0 / (double) k_value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static Map sortMap(Map map){
		List list = new LinkedList(map.entrySet());

		//sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry)(o2)).getValue()).compareTo(((Map.Entry)(o1)).getValue());
			}
		});
		// put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		int order = 0;
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
			sortedMap.put(entry.getKey(), order );
			order++;
		}
		return sortedMap;
	}
}
