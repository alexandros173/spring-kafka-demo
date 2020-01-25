package com.database.updatecurrency;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class Rest {

	@Autowired
	EntityManager em;

	@PostMapping(path="update/{symbol}/{rate}")
	public void post(@PathVariable String symbol, @PathVariable int rate){
		em.merge(new Currency(symbol, rate));
	}

	@PostMapping(path="delete/{symbol}")
	public void delete(@PathVariable String symbol){
		Currency found= em.find(Currency.class, symbol);
		em.remove(found);
	}
}
