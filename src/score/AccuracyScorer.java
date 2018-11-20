package score ;

import java.util.*;

public class AccuracyScorer{
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
		for(Long key: answer_order.keySet()){
//			System.out.println(i + "\t" + key + "\t" + answer_order.get(key) + "\t" + test_order.get(key));
			if(test_order.get(key) < k_value && answer_order.get(key) < k_value)
				right++;
			else if(test_order.get(key) >= k_value && answer_order.get(key) >= k_value)
				right++;

		}
		return right * 1.0 / answer.size();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
			sortedMap.put(entry.getKey(), order + 1);
			order++;
		}
		return sortedMap;
	}
}
