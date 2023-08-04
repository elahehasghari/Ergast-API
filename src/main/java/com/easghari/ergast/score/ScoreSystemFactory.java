package com.easghari.ergast.score;

public class ScoreSystemFactory {
	public static ScoreSystem createScoreSystem(ScoreSystemType type) {
		return switch (type) {
			case CURRENT -> new CurrentScoreSystem();
			case OLD0309 -> new Old0309ScoreSystem();
			default -> throw new IllegalArgumentException("Unknown score system: " + type);
		};
	}
}
