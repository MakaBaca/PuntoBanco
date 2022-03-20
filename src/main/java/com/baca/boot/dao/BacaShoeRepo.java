package com.baca.boot.dao;

import org.springframework.data.repository.CrudRepository;

import com.baca.boot.model.BacaShoe;
import com.baca.boot.model.BacaShoeAndHandPK;

public interface BacaShoeRepo extends CrudRepository<BacaShoe, BacaShoeAndHandPK>{

}
