package com.xxx.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.javatuples.Pair;

public class App {

	private final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		List<Integer> arr = createPoolArray();
		logger.info(arr.toString());
		logger.info("poolSize is :" + calculateTheVolume(arr));
	}

	public static List<Integer> createPoolArray() {
		List<Integer> arr = new ArrayList<>();
		int maxRange = 1000;
		Random random = new Random(maxRange);
		for (int i = 0; i < random.nextInt(maxRange); i++) {
			arr.add(random.nextInt(maxRange));
		}
		return arr;
	}

	public static int calculateTheVolume(List<Integer> arr) {
		List<Pair<Integer, Integer>> poolIndexes = getBarIndexes(arr);
		int totalWater = 0;
		for (Pair<Integer, Integer> pair : poolIndexes) {
			int smallBar = arr.get(pair.getValue0()) < arr.get(pair.getValue1()) ? pair.getValue0() : pair.getValue1();
			for (int index = pair.getValue0() + 1; index < pair.getValue1(); index++) {
				totalWater += arr.get(smallBar) - arr.get(index);
			}
		}
		return totalWater;
	}

	public static List<Pair<Integer, Integer>> getBarIndexes(List<Integer> arr) {
		List<Pair<Integer, Integer>> barIndexes = new ArrayList<>();
		int firstBarIndex = 0;
		while (firstBarIndex < arr.size() - 1) {
			int nextTallestIndex = firstBarIndex + 1;
			nextTallestIndex += getTallestBar(arr.subList(nextTallestIndex, arr.size()), arr.get(firstBarIndex));
			Pair<Integer, Integer> barIndex = new Pair<>(firstBarIndex, nextTallestIndex);
			barIndexes.add(barIndex);
			firstBarIndex = nextTallestIndex;
		}
		return barIndexes;
	}

	@SuppressWarnings("finally")
	private static int getTallestBar(List<Integer> arr, int firstPointValue) {
		int maxVal = 0;
		try {
			maxVal = arr.stream().filter(v -> v > firstPointValue).findFirst().orElseThrow();
		} catch (Exception e) {
			maxVal = arr.stream().mapToInt(v -> v).max().orElseThrow();
		} finally {
			return arr.indexOf(maxVal);
		}
	}
}
