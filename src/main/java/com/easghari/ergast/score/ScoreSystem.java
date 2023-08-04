package com.easghari.ergast.score;

import com.easghari.ergast.model.Driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreSystem {
	private int[] scores;
	final private Map<String, Integer> scoreBoard;

	public ScoreSystem(int[] value) {
		scores = value;
		scoreBoard = new HashMap<>();
	}

	private int getsScore(int position) {
		return (position <= scores.length) ? scores[position - 1] : 0;
	}

	public void addOrUpdateScore(String driver, int position) {
		int score = getsScore(position);
		if (scoreBoard.containsKey(driver)) {
			scoreBoard.put(driver, scoreBoard.get(driver) + score);
		} else scoreBoard.put(driver, score);
	}

	public List<Driver> getFinalScoreBoard() {
		List<Driver> driverList = new ArrayList<>();
		scoreBoard.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.map(entry -> driverList.add(new Driver(entry.getKey(), entry.getValue())))
				.collect(Collectors.toList());
		return driverList;
	}
}
