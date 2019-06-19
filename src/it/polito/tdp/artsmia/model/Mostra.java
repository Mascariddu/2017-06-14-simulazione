package it.polito.tdp.artsmia.model;

public class Mostra{

	private int id;
	private String title;
	private int begin;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public Mostra(int id, String title, int begin) {
		super();
		this.id = id;
		this.title = title;
		this.begin = begin;
	}
	@Override
	public String toString() {
		return id + ", " + title + ", " + begin ;
	}

}
