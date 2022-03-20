package com.baca.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baca.boot.dao.PPSCustomRepo;
import com.baca.boot.model.PlayerWinRatio;

@RestController
@RequestMapping("/rest")
public class PlayerStatsRestController {
	
	@Autowired
	PPSCustomRepo repo;
	
	@RequestMapping(value="/playerRatio/{playerId}", method = RequestMethod.GET)
	public ResponseEntity<List<PlayerWinRatio>> getPlayerWinRatio(@PathVariable("playerId") int playerId){
		List<PlayerWinRatio> resultList = repo.getPlayerWinRatio(playerId);
		if(resultList.isEmpty()){
			return new ResponseEntity<List<PlayerWinRatio>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<PlayerWinRatio>>(resultList, HttpStatus.OK);
	}
}
