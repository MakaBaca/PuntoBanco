package com.baca.boot.dao;

import org.springframework.data.repository.CrudRepository;

import com.baca.boot.model.Player;

public interface PlayerRepo extends CrudRepository<Player, Integer>{

}