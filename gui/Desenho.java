package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import gui.Grafo_tela.*;

public class Desenho extends JPanel implements MouseListener, MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// D = Diametro do circulo que representa cada Vertice
	// L = espessura da linha que representa cada Aresta
	public int D, L;
	private Dimension Dimensao = Toolkit.getDefaultToolkit().getScreenSize();
	private boolean p = false;
	private int px;
	private int py;
	private char valor;
	private int x;
	private int y;
	private Grafo_tela Grafo;

	public Desenho() {
		Grafo = new Grafo_tela();
		atualizaTamanho();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	private void atualizaTamanho(){
		D=(int)(Math.round(2*Math.sqrt(getSize().getWidth()*getSize().getHeight()*0.00037/Math.PI)));
		L=(int)Math.round(D/7.0);
		Grafo.setTamanhos(D, L);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int) Dimensao.getWidth(), (int) Dimensao.getHeight());
		Vertice V = null, V2 = null;
		g.setColor(Color.BLACK);
		atualizaTamanho();
		for (int i = 0; i < Grafo.getVertices().size(); i++) {
			V = Grafo.getVertice(i);
			g.fillOval(V.getX() - D / 2, V.getY() - D / 2, D, D);
		}
		Aresta A;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(L));
		for (int i = 0; i < Grafo.getArestas().size(); i++) {
			A = Grafo.getAresta(i);
			V = A.getv1();
			V2 = A.getv2();
			g2.drawLine(V.getX(), V.getY(), V2.getX(), V2.getY());
		}
		if (p == true)
			g2.drawLine(x, y, px, py);
		p = false;
		g.dispose();
	}

	public void setFerramenta(char newValor) {
		valor = newValor;
	}

	public void criaVertice() {
		if (Grafo.buscaVertice(new Point(x, y)) == -1)
			Grafo.addVertice(x, y);
	}

	public void criaAresta(int x2, int y2) {
		int i1 = Grafo.buscaVertice(new Point(x, y)), i2 = Grafo.buscaVertice(new Point(x2, y2));
		if (i1 != -1 && i2 != -1) {
			Vertice v1 = Grafo.getVertice(i1), v2 = Grafo.getVertice(i2);
			if (Grafo.buscaAresta(v1, v2) == -1)
				Grafo.addAresta(i1, i2);
		}
	}

	public void rmvVertice() {
		int in = Grafo.buscaVertice(new Point(x, y));
		if (in != -1) 
			Grafo.removeVertice(new Point(x, y));
	}

	public void rmvAresta() {
		Grafo.removeAresta(new Point(x, y));
	}
	
	public void emparelha(){
		Grafo.emparelha();
	}

	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		if (valor == 0)
			criaVertice();
		if (valor == 2)
			rmvVertice();
		if (valor == 3)
			rmvAresta();
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		if (valor == 1)
			criaAresta(e.getX(), e.getY());
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
		if (Grafo.buscaVertice(new Point(x, y)) != -1) {
			p = true;
			px = e.getX();
			py = e.getY();
			repaint();
		}
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}
}