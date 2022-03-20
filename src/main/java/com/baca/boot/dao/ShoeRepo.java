package com.baca.boot.dao;

import org.springframework.data.repository.CrudRepository;

import com.baca.boot.model.Shoe;

public interface ShoeRepo extends CrudRepository<Shoe, Integer> {

}
