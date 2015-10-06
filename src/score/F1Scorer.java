package score ;

import java.util.*;

public class F1Scorer{
	/**
	 * @param	test	prediction result
	 * @param	answer	ground truth
	 * @param	k_value	top k
	 * */
	public static double getR( Map<Long,Double> test , Map<Long,Double> answer, int k_value){
		double recall = RecallScorer.getR(test, answer, k_value);
		double precision = PrecisionScorer.getR(test, answer, k_value);

		
		// for test
		System.out.println("recall: "+ recall + " precision: " + precision );		
		if(precision + recall != 0)
			return 2.0 * precision * recall / (precision + recall);
		else
			return 0.0;
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
			sortedMap.put(entry.getKey(), order + 1);
			order++;
		}
		return sortedMap;
	}
}
