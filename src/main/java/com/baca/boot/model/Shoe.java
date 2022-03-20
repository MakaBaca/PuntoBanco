package com.baca.boot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Shoe")
public class Shoe {
	
	@Id
	@GeneratedValue(generator="shoe_seq")
	@SequenceGenerator(name="shoe_seq",sequenceName="SHOE_NO_SEQ", allocationSize=1, initialValue=1000)
	@Column(name="shoeNumber")
	int shoeNumber;
	
	@Column(name="source", length = 64)
	String source;

	public int getShoeNumber() {
		return shoeNumber;
	}

	public void setShoeNumber(int shoeNumber) {
		this.shoeNumber = shoeNumber;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Shoe [shoeNumber=" + shoeNumber + ", source=" + source + "]";
	}
}
