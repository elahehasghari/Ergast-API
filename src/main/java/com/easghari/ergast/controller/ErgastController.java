package com.easghari.ergast.controller;

import com.easghari.ergast.service.ErgastService;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1", produces="application/json")
public class ErgastController {


	@Autowired
	private ErgastService ergastService;

	@Description("List of seasons")
	@GetMapping("/seasons")
	public ResponseEntity<Object> getSeasonList()  {
		Object response = ergastService.getSeasons();
		return  ResponseEntity.ok(response);
	}


	@Description("Listing the races for a specific season")
	@GetMapping("/season/{season}/races")
	public ResponseEntity<Object> getRaceListForSeason(@PathVariable("season") String season) {
		Object response = ergastService.getRacesBySeason(season);
		return ResponseEntity.ok(response);
	}

	@Description("Listing the final standing of drivers with pilot, points and constructors for a specific season")
	@GetMapping("/season/{season}/finals")
	public ResponseEntity<Object> getFinalStandingForSeason(@PathVariable("season") String season) {
		Object response = ergastService.getFinalStandingsBySeason(season);
		return ResponseEntity.ok(response);
	}

	@Description("When selecting a race: Show the starting grid with the position, pilot name and qualifying time for each pilot")
	@GetMapping("/season/{season}/race/{race}/qualifying")
	public ResponseEntity<Object> getQualifyingForRace(@PathVariable("season") String season, @PathVariable("race") String race) {
		Object response = ergastService.getQualifyingForRace(season,race);
		return ResponseEntity.ok(response);
	}

	@Description("When selecting a race: Show the result of the race with the position, pilot name and number of points for each pilot")
	@GetMapping("/season/{season}/race/{race}/results")
	public ResponseEntity<Object> getResultOfRace(@PathVariable("season") String season, @PathVariable("race") String race) {
		Object response = ergastService.getResultForRace(season,race);
		return ResponseEntity.ok(response);
	}
}
