package com.excilys.cdb.controller.web;

public class Page {
	private int numero;
	private int nbElem;
	private String search;
	private String colonne;
	private int nbOrdiPage;
	private String mode;
	
	public Page() {
		this.nbOrdiPage = 10;
		this.numero = 1;
		this.setSearch("");
		this.setColonne("");
		this.mode = "";
	}
	
	public Page(int numero, int nbElem) {
		this.nbElem = nbElem;
		this.numero = numero;
		this.setSearch("");
		this.setColonne("");
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getColonne() {
		return colonne;
	}

	public void setColonne(String colonne) {
		this.colonne = colonne;
	}

	public int getNbOrdiPage() {
		return nbOrdiPage;
	}

	public void setNbOrdiPage(int nbOrdiPage) {
		this.nbOrdiPage = nbOrdiPage;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}


