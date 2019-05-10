package com.excilys.cdb.controller.web;

public class Page {
	private int numero;
	private int nbElem;
	
	public Page(int numero, int nbElem) {
		this.nbElem = nbElem;
		this.numero = numero;
	}
	public int getNbElem() {
		return nbElem;
	}
	public void setNbElem(int nbElem) {
		this.nbElem = nbElem;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
}


