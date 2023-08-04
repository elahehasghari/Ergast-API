package com.easghari.ergast.score;

import com.easghari.ergast.model.Driver;

import java.util.List;

public class CurrentScoreSystem extends ScoreSystem {

	public CurrentScoreSystem() {
		super(new int[]{25, 18, 15, 12, 10, 8, 6, 4, 2, 1});
	}


	@Override
	public void addOrUpdateScore(String driver, int position) {
		super.addOrUpdateScore(driver, position);
	}

	@Override
	public List<Driver> getFinalScoreBoard() {
		return super.getFinalScoreBoard();
	}
}
