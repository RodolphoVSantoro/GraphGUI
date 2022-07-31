package gui;

import java.awt.Point;
import java.util.ArrayList;
import grafo.Grafo;
import grafo.Aresta;
import grafo.Vertice;

public class Grafo_tela {

	private int D,L;
	
	public class Vertice {
		// x e y do centro do vertice na tela
		@SuppressWarnings("unused")
		private int id;
		private Point centro;

		public Vertice(int id, int x, int y) {
			this.id = id;
			this.centro = new Point(x, y);
		}

		public Point getCentro() {
			return centro;
		}

		public int getX() {
			return centro.x;
		}

		public int getY() {
			return centro.y;
		}
	}

	public class Aresta {
		@SuppressWarnings("unused")
		private int id;
		private Vertice v1, v2;

		public Aresta(int id, Vertice v1, Vertice v2) {
			this.id = id;
			this.v1 = v1;
			this.v2 = v2;
		}

		public Vertice getv1() {
			return v1;
		}

		public Vertice getv2() {
			return v2;
		}
	}

	private ArrayList<Vertice> vertices;
	private ArrayList<Aresta> arestas;

	public Grafo_tela() {
		vertices = new ArrayList<Vertice>();
		arestas = new ArrayList<Aresta>();
	}
	
	public void setTamanhos(int d, int l){
		D = d;
		L = l;
	}
	
	public ArrayList<Vertice> getVertices() {
		return vertices;
	}

	public ArrayList<Aresta> getArestas() {
		return arestas;
	}

	public Vertice getVertice(int ind) {
		return this.vertices.get(ind);
	}

	public Aresta getAresta(int ind) {
		return this.arestas.get(ind);
	}

	public void addVertice(int x, int y) {
		Vertice v = new Vertice(vertices.size(), x, y);
		this.vertices.add(v);
	}

	public void addAresta(int id1, int id2) {
		Aresta a = new Aresta(arestas.size(), vertices.get(id1), vertices.get(id2));
		this.arestas.add(a);
	}

	public int buscaVertice(Point p) {
		Vertice v;
		for (int i = 0; i < vertices.size(); i++) {
			v = vertices.get(i);
			if (v.centro.distance(p) <= D / 2)
				return i;
		}
		return -1;
	}
	
	public ArrayList<Aresta> buscaAresta(Point p) {
		Aresta a;
		Point p1, p2;
		ArrayList<Aresta> resultado = new ArrayList<Aresta>();
		for (int i = 0; i < arestas.size(); i++) {
			a = arestas.get(i);
			p1 = a.v1.centro;
			p2 = a.v2.centro;
			if ((p1.distance(p) + p2.distance(p)) <= p1.distance(p2) + L
					&& (p1.distance(p) + p2.distance(p)) >= p1.distance(p2) - L)
				resultado.add(a);
		}
		return resultado;
	}

	public ArrayList<Aresta> buscaAresta(Vertice v) {
		Aresta a;
		ArrayList<Aresta> resultado = new ArrayList<Aresta>();
		for (int i = 0; i < arestas.size(); i++) {
			a = arestas.get(i);
			if (a.v1 == v || a.v2 == v)
				resultado.add(a);
		}
		return resultado;
	}

	public int buscaAresta(Vertice v1, Vertice v2) {
		Aresta a;
		for (int i = 0; i < arestas.size(); i++) {
			a = arestas.get(i);
			if ((a.v1 == v1 && a.v2 == v2) || (a.v1 == v2 && a.v2 == v1))
				return i;
		}
		return -1;
	}

	public void removeVertice(Point p) {
		int in = buscaVertice(p);
		if (in != -1) {
			Vertice v = vertices.get(in);
			ArrayList<Aresta> remover = buscaAresta(v);
			for (int i = 0; i < remover.size(); i++)
				arestas.remove(remover.get(i));
			vertices.remove(in);
		}
	}

	public void removeAresta(Point p) {
		ArrayList<Aresta> remover = buscaAresta(p);
		for (int i = 0; i < remover.size(); i++)
			arestas.remove(remover.get(i));
	}
	
	public void emparelha(){
		Grafo g = new Grafo();
		grafo.Vertice v;
		grafo.Aresta a;
		int v1,v2;
		
		for(int i=0;i<vertices.size();i++){
			v = new grafo.Vertice();
			v.id=vertices.get(i).id;
			g.vertices.add(v);
		}
		for(int i=0;i<arestas.size();i++){
			a = new grafo.Aresta();
			a.vertice1 = arestas.get(i).v1.id;
			a.vertice2 = arestas.get(i).v2.id;
			a.id = arestas.get(i).id;
			g.arestas.add(a);
		}
		
		g=g.montarGrafoEmparelhamentos();
		
		this.arestas = new ArrayList<Grafo_tela.Aresta>();
		for(int i=0;i<g.arestas.size();i++){
			v1 = g.arestas.get(i).vertice1;
			v2 = g.arestas.get(i).vertice2;
			addAresta(v1,v2);
		}
	}
}