package it.polito.tdp.artsmia.model;

public class Evento implements Comparable<Evento>{

	private Mostra mostra;
	private int studente;
	
	public Mostra getMostra() {
		return mostra;
	}
	public void setMostra(Mostra mostra) {
		this.mostra = mostra;
	}
	public int getStudente() {
		return studente;
	}
	public void setStudente(int studente) {
		this.studente = studente;
	}
	public Evento(Mostra mostra, int studente) {
		super();
		this.mostra = mostra;
		this.studente = studente;
	}
	
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.mostra.getBegin() - o.mostra.getBegin();
	}
	
}
