package com.easghari.ergast.service;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ErgastService {

	private static final String BASIC_URL = "http://ergast.com/api/f1";
	private static final String CACHE_SEASON = "seasons";
	private static final String CACHE_RACE_BY_SEASON = "race_by_season";
	private static final String CACHE_FINAL_STANDING_BY_SEASON = "final_standing_by_season";
	private static final String CACHE_QUALIFYING_FOR_RACE = "qualifying_for_race";
	private static final String CACHE_RESULT_FOR_RACE = "result_for_race";

	private final WebClient webClient;

	public ErgastService() {
		this.webClient = WebClient
				.builder()
				.baseUrl(BASIC_URL)
				.build();
	}

	@Cacheable(CACHE_SEASON)
	public Object getSeasons() {

		String responseString = getApiResult("/seasons.json");
		return JsonHelper.getSeasonsFromJson(responseString);

	}

	@Cacheable(CACHE_RACE_BY_SEASON)
	public Object getRacesBySeason(String season) {

		String responseString = getApiResult(String.format("/%s.json", season));
		return JsonHelper.getRacesBySeasonFromJson(responseString);
	}

	@Cacheable(CACHE_FINAL_STANDING_BY_SEASON)
	public Object getFinalStandingsBySeason(String season) {

		String responseString = getApiResult(String.format("/%s/driverStandings.json", season));
		return JsonHelper.getFinalStandingsBySeasonFromJson(responseString);

	}

	@Cacheable(CACHE_QUALIFYING_FOR_RACE)
	public Object getQualifyingForRace(String season, String race) {

		String responseString = getApiResult(String.format("/%s/%s/qualifying.json", season, race));
		return JsonHelper.getQualifyingForRaceFromJson(responseString);
	}

	@Cacheable(CACHE_RESULT_FOR_RACE)
	public Object getResultForRace(String season, String race) {

		String responseString = getApiResult(String.format("/%s/%s/results.json", season, race));
		return JsonHelper.getResultForRaceFromJson(responseString);
	}

	private String getApiResult(String url) {
		return webClient.get()
				.uri(url)
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(String.class)
				.block();

	}

}
