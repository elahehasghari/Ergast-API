package com.easghari.ergast.score;

import com.easghari.ergast.model.Driver;

import java.util.List;

public class Old0309ScoreSystem extends ScoreSystem{

	public Old0309ScoreSystem() {
		super(new int[]{10, 8, 6, 5, 4, 3, 2, 1});
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
