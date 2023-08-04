package com.easghari.ergast.service;

import com.easghari.ergast.score.ScoreSystem;
import com.easghari.ergast.score.ScoreSystemFactory;
import com.easghari.ergast.model.ErrorObject;
import com.easghari.ergast.model.Qualifying;
import com.easghari.ergast.model.Race;
import com.easghari.ergast.model.Result;
import com.easghari.ergast.score.ScoreSystemType;
import com.easghari.ergast.model.Season;
import com.easghari.ergast.model.Standing;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonHelper {
	private static final String MRDATA = "MRData";
	private static final String SEASON_TABLE = "SeasonTable";
	private static final String SEASONS = "Seasons";
	private static final String SEASON = "season";
	private static final String URL = "url";
	private static final String RACE_TABLE = "RaceTable";
	private static final String RACES = "Races";
	private static final String RACE_NAME = "raceName";
	private static final String ROUND = "round";
	private static final String CIRCUIT = "Circuit";
	private static final String CIRCUIT_NAME = "circuitName";
	private static final String LOCATION = "Location";
	private static final String LOCALITY = "locality";
	private static final String COUNTRY = "country";
	private static final String DATE = "date";
	private static final String STANDINGS_TABLE = "StandingsTable";
	private static final String STANDINGS_LIST = "StandingsLists";
	private static final String DRIVER_STANDING = "DriverStandings";
	private static final String DRIVER = "Driver";
	private static final String POSITION = "position";
	private static final String DRIVER_ID = "driverId";
	private static final String GIVEN_NAME = "givenName";
	private static final String FAMILY_NAME = "familyName";
	private static final String CONSTRUCTORS = "Constructors";
	private static final String POINTS = "points";
	private static final String NAME = "name";
	private static final String QUALIFYING_RESULT = "QualifyingResults";
	private static final String Q3 = "Q3";
	private static final String Q2 = "Q2";
	private static final String Q1 = "Q1";
	private static final String INVALID_ARGUMENT = "Invalid Arguments.";
	private static final String RESULTS = "Results";
	private static final String RETURN_EMPTY = "Return list is empty.";
	private static final String TOTAL = "total";

	private static ObjectMapper objectMapper;

	public static Object getSeasonsFromJson(String jsonString) {
		objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(jsonString);
			if (jsonNode.get(MRDATA).get(TOTAL).asInt() > 0) {

				jsonNode = jsonNode.get(MRDATA).get(SEASON_TABLE).get(SEASONS);
				List<Season> seasonList = new ArrayList<>();
				jsonNode.forEach(s -> seasonList.add(new Season(s.get(SEASON).asText(), s.get(URL).asText())));

				return seasonList;
			} else return new ErrorObject(RETURN_EMPTY);
		} catch (Exception ex) {
			return new ErrorObject(ex.getMessage());
		}
	}

	public static Object getRacesBySeasonFromJson(String jsonString) {
		objectMapper = new ObjectMapper();

		try {
			JsonNode jsonNode = objectMapper.readTree(jsonString);
			if (jsonNode.get(MRDATA).get(TOTAL).asInt() > 0) {

				jsonNode = jsonNode.get(MRDATA).get(RACE_TABLE).get(RACES);
				List<Race> raceList = new ArrayList<>();

				jsonNode.forEach(item -> {
					Race race = new Race();
					JsonNode circuit = item.get(CIRCUIT);
					JsonNode location = circuit.get(LOCATION);
					race.setRaceName(item.get(RACE_NAME).asText());
					race.setRound(item.get(ROUND).asText());
					race.setUrl(item.get(URL).asText());
					race.setCircuitName(circuit.get(CIRCUIT_NAME).asText());
					race.setLocality(location.get(LOCALITY).asText());
					race.setCountry(location.get(COUNTRY).asText());
					race.setDate(item.get(DATE).asText());

					raceList.add(race);
				});

				return raceList;
			} else return new ErrorObject(RETURN_EMPTY);

		} catch (Exception ex) {
			return new ErrorObject(ex.getMessage());
		}
	}

	public static Object getFinalStandingsBySeasonFromJson(String jsonString) {
		objectMapper = new ObjectMapper();

		try {

			JsonNode jsonNode = objectMapper.readTree(jsonString);
			if (jsonNode.get(MRDATA).get(TOTAL).asInt() > 0) {

				jsonNode = jsonNode.get(MRDATA).get(STANDINGS_TABLE).get(STANDINGS_LIST);
				if (jsonNode.size() > 0) {

					jsonNode = jsonNode.get(0).get(DRIVER_STANDING);

					List<Standing> standingList = new ArrayList<>();
					jsonNode.forEach(item -> {
						JsonNode driver = item.get(DRIVER);
						Standing standing = new Standing();
						standing.setPosition(item.get(POSITION).asText());
						standing.setDriverId(driver.get(DRIVER_ID).asText());
						standing.setDriverName(generateFullName(driver.get(GIVEN_NAME).asText(), driver.get(FAMILY_NAME).asText()));
						standing.setConstructor(item.get(CONSTRUCTORS).get(0).get(NAME).asText());
						standing.setPoints(item.get(POINTS).asText());

						standingList.add(standing);
					});

					return standingList;
				} else return new ErrorObject(INVALID_ARGUMENT);
			} else return new ErrorObject(RETURN_EMPTY);

		} catch (Exception ex) {
			return new ErrorObject(ex.getMessage());
		}
	}

	public static Object getQualifyingForRaceFromJson(String jsonString) {
		objectMapper = new ObjectMapper();

		try {

			JsonNode jsonNode = objectMapper.readTree(jsonString);
			if (jsonNode.get(MRDATA).get(TOTAL).asInt() > 0) {

				jsonNode = jsonNode.get(MRDATA).get(RACE_TABLE).get(RACES);
				if (jsonNode.size() > 0) {

					jsonNode = jsonNode.get(0).get(QUALIFYING_RESULT);

					List<Qualifying> qualifyingList = new ArrayList<>();
					jsonNode.forEach(item -> {
						JsonNode driver = item.get(DRIVER);
						Qualifying qualifying = new Qualifying();
						qualifying.setPosition(item.get(POSITION).asText());
						qualifying.setDriverName(generateFullName(driver.get(GIVEN_NAME).asText() , driver.get(FAMILY_NAME).asText()));

						if (item.findValue(Q3) != null)
							qualifying.setQualifyingTime(item.get(Q3).asText());
						else if (item.findValue(Q2) != null)
							qualifying.setQualifyingTime(item.get(Q2).asText());
						else qualifying.setQualifyingTime(item.get(Q1).asText());

						qualifyingList.add(qualifying);
					});

					return qualifyingList;
				} else return new ErrorObject(INVALID_ARGUMENT);
			} else return new ErrorObject(RETURN_EMPTY);
		} catch (Exception ex) {
			return new ErrorObject(ex.getMessage());
		}
	}

	public static Object getResultForRaceFromJson(String jsonString) {
		objectMapper = new ObjectMapper();

		try {

			JsonNode jsonNode = objectMapper.readTree(jsonString);
			if (jsonNode.get(MRDATA).get(TOTAL).asInt() > 0) {

				JsonNode node = jsonNode.get(MRDATA).get(RACE_TABLE).get(RACES);
				if (node.size() > 0) {
					node = node.get(0).get(RESULTS);

					List<Result> resultList = new ArrayList<>();
					node.forEach(item -> {
						JsonNode driver = item.get(DRIVER);
						Result result = new Result();
						result.setPosition(item.get(POSITION).asText());
						result.setDriverName(generateFullName(driver.get(GIVEN_NAME).asText(), driver.get(FAMILY_NAME).asText()));
						result.setPoints(item.get(POINTS).asText());

						resultList.add(result);
					});

					return resultList;
				} else return new ErrorObject(INVALID_ARGUMENT);
			} else return new ErrorObject(RETURN_EMPTY);
		} catch (Exception ex) {
			return new ErrorObject(ex.getMessage());
		}
	}

	public static Object getScoresBasedOnScoreSystemFromJson(String jsonString, ScoreSystemType scoreSystemType){
		objectMapper = new ObjectMapper();

		try {

			JsonNode jsonNode = objectMapper.readTree(jsonString);
			if (jsonNode.get(MRDATA).get(TOTAL).asInt() > 0) {

				ScoreSystem scoreSystem = ScoreSystemFactory.createScoreSystem(scoreSystemType);

				JsonNode node = jsonNode.get(MRDATA).get(RACE_TABLE).get(RACES);
				node.forEach(race -> race.get(RESULTS).forEach(result->{
					int position = result.get(POSITION).asInt();
					String driver = result.get(DRIVER).get(DRIVER_ID).asText();
					scoreSystem.addOrUpdateScore(driver, position);
				}));
				return scoreSystem.getFinalScoreBoard();
			} else return new ErrorObject(RETURN_EMPTY);
		} catch (Exception ex) {
			return new ErrorObject(ex.getMessage());
		}
	}

	private static String generateFullName(String firstName, String lastName){
		return firstName + " " + lastName;
	}
}
