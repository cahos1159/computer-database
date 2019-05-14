package com.excilys.cdb.model;

import org.springframework.stereotype.Component;

public abstract class Model {
	protected int id;
	
	public Model(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
