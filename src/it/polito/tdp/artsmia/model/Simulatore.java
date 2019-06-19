package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Simulatore {
	
	ArtsmiaDAO dao;
	SimpleDirectedGraph<Mostra,DefaultEdge> grafo;
	Mostra prima = null;
	PriorityQueue<Evento> queue;
	HashMap<ArtObject, ArrayList<Integer>> punteggi;
	HashMap<Integer, ArtObject> idMap;
	HashMap<Integer, ArrayList<Mostra>> visitate;
	List<Mostra> mostre;
	List<ArtObject> oggetti;
	private Mostra next;

	public Simulatore() {
		
		dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer, ArtObject>();
		oggetti = new ArrayList<ArtObject>(dao.listObject(idMap));
		queue = new PriorityQueue<Evento>();
		punteggi = new HashMap<ArtObject, ArrayList<Integer>>();
		visitate = new HashMap<Integer, ArrayList<Mostra>>();
		
	}

	public void init(int n, int anno, SimpleDirectedGraph<Mostra, DefaultEdge> grafo2, HashMap<Integer, Mostra> idMap2) {
		// TODO Auto-generated method stub
		this.grafo = new SimpleDirectedGraph<Mostra, DefaultEdge>(DefaultEdge.class);
		grafo = grafo2;
		visitate = new HashMap<Integer, ArrayList<Mostra>>();
		mostre = new ArrayList<Mostra>(dao.getMostre(anno));
		
		for(Mostra mostra : mostre) {
			
			Random random = new Random(1);
			if(random.nextDouble() > 0.5) {
				prima = mostra;
				break;
			}
		}
		
		for(ArtObject artObject : oggetti) {
			
			ArrayList<Integer> vuota = new ArrayList<Integer>();
			vuota.add(0);
			
			punteggi.put(artObject, vuota);
			System.out.println("AGGIUNGO!");

		}
		
		for(int i = 1; i <= n; i++) {
			
			queue.add(new Evento(prima,i));
			visitate.put(i, new ArrayList<Mostra>());
			visitate.get(i).add(prima);
			
		}
		
		
	}

	public void run() {
		// TODO Auto-generated method stub
		Evento e;
		
		while((e = queue.poll()) != null) {
			
			System.out.println("EVENTO!");
			System.out.println(e.getMostra().toString() + " "+e.getStudente());
			for(ArtObject artObject : oggetti) {
				
				System.out.println(e.getStudente());
				if(!punteggi.get(artObject).contains(e.getStudente())) {
					System.out.println("Aggiorna punti!");
					punteggi.get(artObject).add(e.getStudente());
				}
			}
			
			if(grafo.containsVertex(e.getMostra())) {
			if(grafo.outDegreeOf(e.getMostra()) > 0) {
			for(DefaultEdge edge : grafo.outgoingEdgesOf(e.getMostra())) {
				
				Random random = new Random(1);
				if(random.nextDouble() > 0.5) {
					System.out.println("SCELTA!");
					next = grafo.getEdgeTarget(edge);
					break;
				}
			}
			}
			}
			
			if(!visitate.get(e.getStudente()).contains(next)) {
				System.out.println("SCHEDULO NUOVA MOSTRA!");
				queue.add(new Evento(prima, e.getStudente()));
				visitate.get(e.getStudente()).add(prima);
			}
			
		}
	}

	public HashMap<ArtObject,Integer> getClassifica() {
		// TODO Auto-generated method stub
		HashMap<ArtObject, Integer> classifica = new HashMap<ArtObject, Integer>();
		
		for (ArtObject artObject : punteggi.keySet()) {
			
			punteggi.get(artObject).remove(0);
		}
		
		for(ArtObject artObject : punteggi.keySet())
			classifica.put(artObject, punteggi.get(artObject).size());
		
		return classifica;
	}
}
