package com.baca.boot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baca.boot.dao.BacaShoeRepo;
import com.baca.boot.dao.ShoeRepo;
import com.baca.boot.model.BacaShoe;
import com.baca.boot.model.BacaShoeAndHandPK;
import com.baca.boot.model.Shoe;
import com.mak.casino.Baccarat.Outcome;
import com.mak.simulator.AbstractBacaSimulator;

import application.mak.btc.BtcScoreCalc;
import application.mak.btc.BtcScoreCard;

@Controller
public class BacaShoeController extends AbstractBacaSimulator {

	@Autowired
	BacaShoeRepo repo;

	@Autowired
	ShoeRepo sRepo;

	@RequestMapping("/ShoeGenerator")
	public String home(){
		return "ShoeGenerator.jsp";
	}

	List<BtcScoreCard> btcList = null;

	public BacaShoeController() {
		/*prepareGame(78);
		game.prepareShoe();
		btcList = new ArrayList<>();
*/	}

	/*@RequestMapping("/Home")
	public ModelAndView game(){
		return new ModelAndView("Baca.jsp","btcList",btcList);
	}*/

	@RequestMapping("/generateShoe")
	public ModelAndView generateShoe(){
		Shoe shoe = new Shoe();
		sRepo.save(shoe);
		BacaShoe result = new BacaShoe();
		prepareGame(78);
		game.prepareShoe();
		int handNumber = 0;
		while(game.hasNextHand()){
			handNumber++;
			result = new BacaShoe();
			result.setOutcome(game.dealOneHand());
			//result.setShoe(shoe);
			BacaShoeAndHandPK sh = new BacaShoeAndHandPK();
			//sh.setShoeNumber(shoeNumber);
			sh.setHandNumber(handNumber);
			sh.setShoeNumber(shoe.getShoeNumber());
			result.setSh(sh);
			System.out.println(result);
			repo.save(result);
		}
		List<BacaShoe> list= new ArrayList<>();
		for(BacaShoe bs:repo.findAll()){
			list.add(bs);
		}
		return new ModelAndView("ShoeGenerator.jsp","shoeList",list);
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

	/*@RequestMapping(value="/processForm", params="Deal", method=RequestMethod.POST)
	public ModelAndView deal(String wageredOn, int unit){
		Outcome outcome = null;
		if(game.hasNextHand()){
			outcome = game.dealOneHand();
		}
		if(outcome == Outcome.tie){
			return new ModelAndView("Baca.jsp","btcList",btcList);
		}

		if(wageredOn.equalsIgnoreCase("player")){
			calc.recordOutcome(outcome, unit, true);
		}else if(wageredOn.equalsIgnoreCase("banker")){
			calc.recordOutcome(outcome, unit, false);
		}else{
			calc.recordOutcome(outcome, 0, false);
		}
		BtcScoreCard card = calc.getScoreCard();
		btcList.add(card);
		return new ModelAndView("Baca.jsp","btcList",btcList);
	}

	@RequestMapping(value="/processForm", params="New", method=RequestMethod.POST)
	public ModelAndView newShoe(){
		prepareGame(78);
		game.prepareShoe();
		calc = new BtcScoreCalc(false);
		btcList = new ArrayList<>();
		return new ModelAndView("Baca.jsp","btcList",btcList);
	}
*/}
