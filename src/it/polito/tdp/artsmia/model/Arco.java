package it.polito.tdp.artsmia.model;

public class Arco {

	private Mostra source;
	private Mostra target;
	
	public Mostra getSource() {
		return source;
	}
	public void setSource(Mostra source) {
		this.source = source;
	}
	public Mostra getTarget() {
		return target;
	}
	public void setTarget(Mostra target) {
		this.target = target;
	}
	public Arco(Mostra source, Mostra target) {
		super();
		this.source = source;
		this.target = target;
	}
	
}
