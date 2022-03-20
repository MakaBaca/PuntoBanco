package com.baca.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baca.boot.dao.BacaShoeRepo;
import com.baca.boot.dao.PlayedHandsRepo;
import com.baca.boot.dao.PlayerRepo;
import com.baca.boot.dao.ShoeRepo;
import com.baca.boot.model.BacaShoe;
import com.baca.boot.model.BacaShoeAndHandPK;
import com.baca.boot.model.PlayedHandPK;
import com.baca.boot.model.PlayedHands;
import com.baca.boot.model.Player;
import com.baca.boot.model.Shoe;
import com.mak.casino.Baccarat.Outcome;
import com.mak.simulator.AbstractBacaSimulator;

import application.mak.btc.BtcScoreCalc;
import application.mak.btc.MrRafaelScoreCard;

@Controller
public class MrPlayController extends AbstractBacaSimulator {
	
	int handNumber = 0;
	
	int shoeNumber = 0;
	
	List<MrRafaelScoreCard> btcList = null;
	
	List<Prediction> lb = null;
	
	List<Prediction> lp = null;
	
	@Autowired
	BacaShoeRepo bsRepo;

	@Autowired
	ShoeRepo sRepo;
	
	@Autowired
	PlayedHandsRepo phRepo;
	
	@Autowired
	PlayerRepo pRepo;
	
	Player player;
	
	private void intializeGame(){
		prepareGame(78);
		//game.prepareShoe();
		calc = new BtcScoreCalc(false);
		btcList = new ArrayList<>();
		Shoe shoe = new Shoe();
		shoe.setSource("Baca");
		sRepo.save(shoe);
		shoeNumber = shoe.getShoeNumber();
		handNumber = 0;
	}
	
	@RequestMapping("/mr/{playerId}")
	public String game(@PathVariable("playerId") int id, Model model){
		player = pRepo.findById(id).get();
		intializeGame();
		model.addAttribute("btcList",btcList);
		return "mrbaca";
	}
	
	@RequestMapping(value="/mr/processForm", params="Deal", method=RequestMethod.POST)
	public String deal(String wageredOn, int unit, Model model){
		boolean isVirtualBet = false;
        boolean isOnPlayer = false;
        boolean isOnBanker = false;
        Outcome outcome = null;
        if(unit == 0 && !wageredOn.equalsIgnoreCase("nb")){
            isVirtualBet = true;
        }
        handNumber++;
		if(game.hasNextHand()){
			outcome = game.dealOneHand();
		}else{
			btcList.add(new MrRafaelScoreCard("--",0,0,0,false,false,false,false,false,false,false,false,null));
			model.addAttribute("btcList",btcList);
			return "mrbaca";
		}
		if(outcome == Outcome.tie){
			recordHand(outcome);
			model.addAttribute("btcList",btcList);
			if(lp != null && lb != null) {
				model.addAttribute("nextPlayer", lp);
				model.addAttribute("nextBanker", lb);
			}
			return "mrbaca";
		}

		if(wageredOn.equalsIgnoreCase("player")){
			calc.recordOutcome(outcome, unit, true);
			isOnPlayer = true;
		}else if(wageredOn.equalsIgnoreCase("banker")){
			calc.recordOutcome(outcome, unit, false);
			isOnBanker = true;
		}else{
			calc.recordOutcome(outcome, 0, false);
		}
		//BtcScoreCard card = calc.getScoreCard();
		MrRafaelScoreCard card = calc.getMrRafaelScoreCard();
		if (isVirtualBet) {
			if (isOnPlayer && outcome == Outcome.player) {
				card.setPlayer(card.getPlayer() + "  0");
				card.setWin(true);
			} else if (isOnPlayer && outcome == Outcome.banker) {
				card.setPlayer("0");
				card.setWin(false);
			} else if (isOnBanker && outcome == Outcome.banker) {
				card.setBanker(card.getBanker() + "  0");
				card.setWin(true);
			} else if (isOnBanker && outcome == Outcome.player) {
				card.setBanker("0");
				card.setWin(false);
			}
		}

		btcList.add(card);
		if(wageredOn.equalsIgnoreCase("nb")){
			recordHand(outcome);
		}else{
			recordHand(outcome, unit, card);
		}
		model.addAttribute("btcList",btcList);
		if(card.getNextBanker() != null && card.getNextPlayer() != null) {
			Prediction b = new Prediction();
			b.setPredictChart(card.getNextBanker());
			lb = new ArrayList<>();
			lb.add(b);
			model.addAttribute("nextBanker", lb);
			Prediction p = new Prediction();
			lp = new ArrayList<>();
			lp.add(p);
			p.setPredictChart(card.getNextPlayer());
			model.addAttribute("nextPlayer", lp);
		}
		return "mrbaca";
	}
	
	private void recordHand(Outcome outcome){
		BacaShoe result = new BacaShoe();
		BacaShoeAndHandPK sh = new BacaShoeAndHandPK();
		sh.setHandNumber(handNumber);
		sh.setShoeNumber(shoeNumber);
		result.setSh(sh);
		result.setOutcome(outcome);
		bsRepo.save(result);
	}
	
	private void recordHand(Outcome outcome, int units, MrRafaelScoreCard scoreCard){
		BacaShoe result = new BacaShoe();
		BacaShoeAndHandPK sh = new BacaShoeAndHandPK();
		sh.setHandNumber(handNumber);
		sh.setShoeNumber(shoeNumber);
		result.setSh(sh);
		result.setOutcome(outcome);
		bsRepo.save(result);
		recordPlayerHand(sh, units, scoreCard, outcome);
	}
	
	private void recordPlayerHand(BacaShoeAndHandPK sh, int units, MrRafaelScoreCard scoreCard, Outcome outcome){
		PlayedHands ph = new PlayedHands();
		PlayedHandPK phPk = new PlayedHandPK();
		phPk.setPlayerId(player.getPlayerId());
		phPk.setSh(sh);
		ph.setPk(phPk);
		ph.setBetAmount(units);
		ph.setDidWin(scoreCard.isWin());
		//ph.setPlayer(player);
		//ph.setBacaShoe(result);
		if(scoreCard.isWin()){
			if (outcome == Outcome.player){
				ph.setAmountWon(units);
			}
			else {
				ph.setAmountWon(units * 0.95);
			}
		}else{
			ph.setAmountWon(units * -1);
		}
		phRepo.save(ph);
	}

	@RequestMapping(value="/mr/processForm", params="New", method=RequestMethod.POST)
	public String newShoe(Model model){
		intializeGame();
		model.addAttribute("btcList",btcList);
		return "mrbaca";
	}
	
	class Prediction{
		String predictChart;

		public String getPredictChart() {
			return predictChart;
		}

		public void setPredictChart(String predictChart) {
			this.predictChart = predictChart;
		}
		
		
	}

	@Override
	public Bet whatToPlay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int unitsToWagger() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canPlay() {
		// TODO Auto-generated method stub
		return false;
	}

}
