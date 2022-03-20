package com.baca.boot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.baca.boot.model.PlayerPlayedShoes;
import com.baca.boot.model.PlayerShoe;

public interface PlayerPlayedShoeRepo  {

/*	@Query(value="SELECT shoe_number, hand_number, player_id, outcome, amount_won, bet_amount, did_win FROM player_played_shoes WHERE shoe_number = :shoeNumber and player_id = :playerId", nativeQuery=true)
	List<PlayerPlayedShoes> findPlaysInShoes(@Param("shoeNumber") int shoeNumber, @Param("playerId") int playerId);

	@Query(value="SELECT DISTINCT shoe_number FROM player_played_shoes WHERE player_id = :playerId", nativeQuery=true)
	List<PlayerShoe> findPlayedShoes(@Param("playerId") int playerId);
*/
}
