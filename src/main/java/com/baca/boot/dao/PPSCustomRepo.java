package com.baca.boot.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SqlResultSetMapping;

import org.springframework.stereotype.Service;

import com.baca.boot.model.PlayerPlayedShoes;
import com.baca.boot.model.PlayerScore;
import com.baca.boot.model.PlayerShoe;
import com.baca.boot.model.PlayerWinRatio;

@Service
public class PPSCustomRepo {
	
	@PersistenceContext
	EntityManager em;
	
	public List<PlayerShoe> findDistictPlayerShoe(int playerId){
		List<PlayerShoe> result = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = em.createNamedQuery("pps.distinct.shoe")
							.setParameter("playerId", playerId)
							.getResultList();
		for(Object[] obj : list){
			result.add(new PlayerShoe((Integer)obj[0],(Integer)obj[1]));
		}
		return result;
	}
	
	public List<PlayerPlayedShoes> findSpecificPlayedShoes(int shoeNumber, int playerId){
		List<PlayerPlayedShoes> result = em.createNamedQuery("pps.player.played.shoe", PlayerPlayedShoes.class)
				.setParameter("shoeNumber", shoeNumber)
				.setParameter("playerId", playerId)
				.getResultList();
		return result;
	}
	
	public List<PlayerShoe> findDistictPlayerShoeAndScore(int playerId){
		List<PlayerShoe> result = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = em.createNamedQuery("pps.player.played.shoe.score")
							.setParameter("playerId", playerId)
							.getResultList();
		for(Object[] obj : list){
			result.add(new PlayerShoe((Integer)obj[0],(Integer)obj[1],(Double)obj[2]));
		}
		return result;
	}
	
	public Map<Integer, Double> findPlayerPerfomance(int playerId) {
		Map<Integer, Double> result = new HashMap<>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = em.createNamedQuery("pps.player.played.shoe.score").setParameter("playerId", playerId)
				.getResultList();
		for (Object[] obj : list) {
			result.put((Integer) obj[0], (Double) obj[2]);
		}
		return result;
	}
	
	public PlayerScore refreshPlayerScore(int playerId){
		PlayerScore ps = null;
		@SuppressWarnings("unchecked")
		List<Object[]> list = em.createNamedQuery("ph.player.refresh.score")
				.setParameter("playerId", playerId)
				.getResultList();
		for(Object[] obj : list){
			ps = new PlayerScore((Double) obj[0],(Long) obj[1]);
		}
		return ps;
	}
	
	public List<PlayerWinRatio> getPlayerWinRatio(int playerId){
		@SuppressWarnings("unchecked")
		List<PlayerWinRatio> resultList = em.createNativeQuery("SELECT did_win, ROUND(((COUNT(*) * 100)/CAST((SELECT COUNT(*) from played_hands WHERE player_id = :playerId) AS decimal)), 2) as percentage FROM played_hands WHERE player_id = :playerId"
				+ " GROUP BY did_win", "WinRatioResultMapping")
				.setParameter("playerId", playerId)
				.getResultList();
		return resultList;
	}
	
}
