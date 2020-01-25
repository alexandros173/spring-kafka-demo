package com.database.updatecurrency;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Currency {
	@Id
	String symbol;

	int rate;

	public Currency(){
		super();
	}

	public Currency(String symbol, int rate){
		this.symbol= symbol;
		this.rate= rate;
	}
}
