package com.baca.boot.dao;

import org.springframework.data.repository.CrudRepository;

import com.baca.boot.model.PlayedHandPK;
import com.baca.boot.model.PlayedHands;

public interface PlayedHandsRepo extends CrudRepository<PlayedHands, PlayedHandPK>{

}
