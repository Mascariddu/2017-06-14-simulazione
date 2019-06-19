package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.omg.CORBA.PUBLIC_MEMBER;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	ArtsmiaDAO dao;
	List<Mostra> vertex;
	SimpleDirectedGraph<Mostra, DefaultEdge> grafo;
	HashMap<Integer, Mostra> idMap;
	List<Arco> edges;
	ConnectivityInspector<Mostra,DefaultEdge> conan;
	HashMap<ArtObject, Integer> classifica;
	
	public Model() {
		
		dao = new ArtsmiaDAO();
		
	}

	public List<Integer> getAnni() {
		// TODO Auto-generated method stub
		return dao.getAnni();
	}

	public void creaGrafo(int anno) {
		// TODO Auto-generated method stub
		idMap = new HashMap<Integer, Mostra>();
		this.grafo = new SimpleDirectedGraph<Mostra, DefaultEdge>(DefaultEdge.class);
		vertex = new ArrayList<Mostra>(dao.getVertex(idMap,anno));
		edges = dao.getArchi(idMap,anno);
		
		Graphs.addAllVertices(grafo, vertex);
		
		for(Arco arco : edges) {
			
			System.out.println("aggiungo");
			grafo.addEdge(arco.getSource(), arco.getTarget());
			
		}
		
		System.out.println("#vertici: "+grafo.vertexSet().size());
		System.out.println("#archi: "+grafo.edgeSet().size());
	}

	public int getConnessi() {
		// TODO Auto-generated method stub
		conan = new ConnectivityInspector<Mostra, DefaultEdge>(grafo);
		
		return conan.connectedSets().size();
	}

	public Mostra getMassima() {
		// TODO Auto-generated method stub
		Mostra best = new Mostra(0, "", 0);
		int bestVal = 0;
		int n = 0;
		
		for(Mostra mostra : grafo.vertexSet()) {
			
			if(( n = dao.getOpere(mostra.getId())) > bestVal) {
				
				bestVal = n;
				best = mostra;
				
			}
			
		}
		
		return best;
	}

	public HashMap<ArtObject, Integer> getClassifica() {
		// TODO Auto-generated method stub
		return this.classifica;
	}

	public void simula(int n, int anno) {
		// TODO Auto-generated method stub
		Simulatore sim = new Simulatore();
		
		sim.init(n,anno,this.grafo,this.idMap);
		
		sim.run();
		
		classifica = new HashMap<ArtObject, Integer>(sim.getClassifica());
	}

	public int getPunti(ArtObject artObject) {
		// TODO Auto-generated method stub
		return this.classifica.get(artObject);
	}

}
