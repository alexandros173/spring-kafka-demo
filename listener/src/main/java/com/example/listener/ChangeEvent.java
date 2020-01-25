package com.example.listener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeEvent {

	private String database;
	private String table;
	private Type type;
	private Currency data;


	public enum Type {
		insert, update, delete
	}

	@Getter
	@Setter
	public static class Currency {

		private String symbol;

		private int rate;
	}
}
