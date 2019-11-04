import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Solution {

	static int[] meetingPlanner(int[][] slotsA, int[][] slotsB, int dur) {
//    System.out.println("wat");
		List<Integer> hours = new ArrayList<>();
		Map<Integer, Integer> hours2Idx = new HashMap<>();
		List<Integer> pc = new ArrayList<>();
		// XXX: https://www.javatpoint.com/how-to-merge-two-arrays-in-java
		Object[] mergedSlots =  Stream.of(slotsA, slotsB).flatMap(Stream::of).toArray();
		for (Object slotObject : mergedSlots) {
			int[] slot=(int[])slotObject;
			hours.add(slot[0]);
			hours.add(slot[1]);
			hours.add(slot[1] - 1);
			pc.add(0);
			pc.add(0);
			pc.add(0);
		}
		Collections.sort(hours);
		for (int i = 0; i < hours.size(); i++) {
			hours2Idx.put(hours.get(i), i);
		}
		for (Object slotObject : mergedSlots){
			int[] slot=(int[]) slotObject;
			Integer i = hours2Idx.get(slot[0]);
			Integer j = hours2Idx.get(slot[1] - 1);
			for (; i <= j; i++) {
				pc.set(i, pc.get(i) + 1);
			}
		}
		int i = 0;
		while (i < pc.size()) {
			if (pc.get(i) == 2) {
				int j = i;
				while (pc.get(j) == 2) {
					j++;
				}
				Integer hour_initial = hours.get(i);
				Integer hour_final = hours.get(j);
				if ((hour_final - hour_initial) >= dur) {
					return new int[] { hour_initial, hour_initial + dur };
				}
				i = j;
			}
			i++;
		}
		return new int[] {};
	}

	public static void main(String[] args) {
		int[] durs = { 8, 12 };
		int[][] exp = { { 60, 68 }, {} };
		int i = 0;
		int[][][][] tcs = { { { { 10, 50 }, { 60, 120 }, { 140, 210 } }, { { 0, 15 }, { 60, 70 } } },
				{ { { 10, 50 }, { 60, 120 }, { 140, 210 } }, { { 0, 15 }, { 60, 70 } } } };
		for (int[][][] tc : tcs) {
			int d = durs[i];
			int[] r = meetingPlanner(tc[0], tc[1], d);
			System.out.println(Arrays.toString(r));
			i++;
		}

	}

}
// Create starts/ends array of size (slotsA.length+slotsB.length)*3
// Create array of people count
// Insert start times,ends and markes for decrementing people count, 
// Sort that last array and map each value to its index 
// ITerate the slots so that the start and end are mapped to indexes in the people count array and in that range 
// increment 1
// Find ranges of value 2 in the people count array and get the corresponding time range in the hours array, see if it accomodates the duration, if so ends.
// If there is not common range for the desired duration, return empty

// 10,60,140
// 0, 60
// Go thru each start and increment # of people in that index

// 0,10,15,16,50,51, 60,70,71,120, 121, 140, 210, 211
// 1, 2, 2, 1, 1,0  ,2   2, 1, 1,   0     0   0    0
// 0,10,60,140

// 15,50,70,120,210
// [0,4]
//[10,50],[51-100]
//[10,100]
// ......
//     iii
// 10...62
// 0...52
// 0...210
// ..... .. .....   ....   ...  
//  .. ....  ...   ....   . ..... .
// 12  3    45     67     89a     b