package com.baca.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baca.boot.dao.BacaShoeRepo;
import com.baca.boot.dao.PlayedHandsRepo;
import com.baca.boot.dao.PlayerRepo;
import com.baca.boot.dao.ShoeRepo;
import com.baca.boot.error.PlayerNotFoundException;
import com.baca.boot.model.BacaShoe;
import com.baca.boot.model.BacaShoeAndHandPK;
import com.baca.boot.model.PlayedHandPK;
import com.baca.boot.model.PlayedHands;
import com.baca.boot.model.Shoe;
import com.mak.casino.Baccarat.Outcome;

import application.mak.btc.BtcScoreCalc;
import application.mak.btc.FiveDScoreCard;

@Controller
public class RecordGameController {
	
	@Autowired
	BacaShoeRepo bsRepo;

	@Autowired
	ShoeRepo sRepo;
	
	@Autowired
	PlayedHandsRepo phRepo;
	
	@Autowired
	PlayerRepo pRepo;
	
	int playerId = 0;
	
	int shoeNumber;
	
	int handNumber = 0;
	
	List<BacaShoe> bsList;
	
	List<PlayedHands> phList;
	
	List<FiveDScoreCard> btcList;
	
	BtcScoreCalc calc;
	
	@RequestMapping("/recordGame/{playerId}")
	public ModelAndView intializeGame(@PathVariable("playerId")int playerId) throws PlayerNotFoundException{
		if(pRepo.findById(playerId).get() == null){
			throw new PlayerNotFoundException(playerId);
		}
		Shoe shoe = new Shoe();
		shoe.setSource("BW");
		sRepo.save(shoe);
		shoeNumber = shoe.getShoeNumber();
		this.playerId = playerId;
		bsList = new ArrayList<>();
		phList = new ArrayList<>();
		btcList = new ArrayList<>();
		calc = new BtcScoreCalc(false);
		return new ModelAndView("/recordGame.jsp", "btcList", btcList);
	}
	
	@RequestMapping(value="/recordHand", params="player", method=RequestMethod.POST)
	public ModelAndView recordPlayerOutcome(String wageredOn, int unit){
		return recordOutcome(wageredOn, Outcome.player, unit);
	}
	
	@RequestMapping(value="/recordHand", params="banker", method=RequestMethod.POST)
	public ModelAndView recordBankerOutcome(String wageredOn, int unit){
		return recordOutcome(wageredOn, Outcome.banker, unit);
	}
	
	@RequestMapping(value="/recordHand", params="tie", method=RequestMethod.POST)
	public ModelAndView recordTieOutcome(){
		handNumber++;
		recordHand(Outcome.tie);
		return new ModelAndView("/recordGame.jsp","btcList",btcList);
	}
	
	@RequestMapping(value="/recordHand", params="delete", method=RequestMethod.POST)
	public ModelAndView deleteLastHand(){
		handNumber--;
		btcList.remove(btcList.size()-1);
		bsList.remove(bsList.size()-1);
		phList.remove(phList.size()-1);
		return new ModelAndView("/recordGame.jsp","btcList",btcList);
	}
	
	@RequestMapping(value="/recordHand", params="save", method=RequestMethod.POST)
	public ModelAndView saveToRepository(){
		bsRepo.saveAll(bsList);
		phRepo.saveAll(phList);
		return new ModelAndView("redirect:" + "/playedShoes/"+playerId);
	}
	
	public ModelAndView recordOutcome(String wageredOn, Outcome outcome, int unit){
		boolean isVirtualBet = false;
        boolean isOnPlayer = false;
        boolean isOnBanker = false;
        if(unit == 0 && !wageredOn.equalsIgnoreCase("nb")){
            isVirtualBet = true;
        }
        handNumber++;
        if(wageredOn.equalsIgnoreCase("player")){
			calc.recordOutcome(outcome, unit, true);
			isOnPlayer = true;
		}else if(wageredOn.equalsIgnoreCase("banker")){
			calc.recordOutcome(outcome, unit, false);
			isOnBanker = true;
		}else{
			calc.recordOutcome(outcome, 0, false);
		}
        FiveDScoreCard card = calc.get5DScoreCard();
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
		return new ModelAndView("/recordGame.jsp","btcList",btcList);
	}
	
	/**
	 * Only for Ties and No Bets
	 * @param outcome
	 */
	private void recordHand(Outcome outcome){
		BacaShoe result = new BacaShoe();
		BacaShoeAndHandPK sh = new BacaShoeAndHandPK();
		sh.setHandNumber(handNumber);
		sh.setShoeNumber(shoeNumber);
		result.setSh(sh);
		result.setOutcome(outcome);
		bsList.add(result);
	}
	
	/**
	 * For virtual Bets and real bets
	 * @param outcome
	 * @param units
	 * @param scoreCard
	 */
	private void recordHand(Outcome outcome, int units, FiveDScoreCard scoreCard){
		BacaShoe result = new BacaShoe();
		BacaShoeAndHandPK sh = new BacaShoeAndHandPK();
		sh.setHandNumber(handNumber);
		sh.setShoeNumber(shoeNumber);
		result.setSh(sh);
		result.setOutcome(outcome);
		bsList.add(result);
		recordPlayerHand(sh, units, scoreCard, outcome);
	}
	
	private void recordPlayerHand(BacaShoeAndHandPK sh, int units, FiveDScoreCard scoreCard, Outcome outcome){
		PlayedHands ph = new PlayedHands();
		PlayedHandPK phPk = new PlayedHandPK();
		phPk.setPlayerId(playerId);
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
		phList.add(ph);
	}

}
