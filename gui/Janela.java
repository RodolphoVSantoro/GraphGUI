package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;

public class Janela extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {

		Janela Janela = new Janela();

		Janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Janela.setVisible(true);
		Janela.setLocationRelativeTo(null);
	}

	private JToolBar Barra_De_Ferramenta = new JToolBar();
	private JLabel L1 = new JLabel("Adicionar:");
	private JButton addVertice = new JButton("Vertice");
	private JButton addAresta = new JButton("Aresta");
	private JLabel L2 = new JLabel("Remover:");
	private JButton rmvVertice = new JButton("Vertice");
	private JButton rmvAresta = new JButton("Aresta");
	private JLabel L3 = new JLabel(" | ");
	private JButton emparelha = new JButton("Emparelhar");
	
	private Desenho Panel_Desenho;

	private JLabel Status = new JLabel("  Adicionar Vertices");

	public Janela() {
		setSize(720, 576);
		Panel_Desenho = new Desenho();
		Barra_De_Ferramenta.add(L1);
		Barra_De_Ferramenta.add(addVertice);
		Barra_De_Ferramenta.add(addAresta);
		Barra_De_Ferramenta.add(L2);
		Barra_De_Ferramenta.add(rmvVertice);
		Barra_De_Ferramenta.add(rmvAresta);
		Barra_De_Ferramenta.add(L3);
		Barra_De_Ferramenta.add(emparelha);
		
		Status.setBackground(Color.WHITE);

		add(Barra_De_Ferramenta, BorderLayout.NORTH);
		add(Panel_Desenho, BorderLayout.CENTER);
		add(Status, BorderLayout.SOUTH);

		Eventos E = new Eventos();
		addVertice.addActionListener(E);
		addAresta.addActionListener(E);
		rmvVertice.addActionListener(E);
		rmvAresta.addActionListener(E);
		emparelha.addActionListener(E);
		
	}

	private class Eventos implements ActionListener {

		char Ferramenta = 0;

		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == addVertice) {
				Ferramenta = 0;
				Status.setText("  Adicionar Vertices");
				repaint();
			}

			if (event.getSource() == addAresta) {
				Ferramenta = 1;
				Status.setText("  Adicionar Arestas(Pressione e arraste o mouse)");
				repaint();
			}

			if (event.getSource() == rmvVertice) {
				Ferramenta = 2;
				Status.setText("  Remover Vertices");
				repaint();
			}

			if (event.getSource() == rmvAresta) {
				Ferramenta = 3;
				Status.setText("  Remover Arestas");
				repaint();
			}

			if(event.getSource() == emparelha){
				Panel_Desenho.emparelha();
				repaint();
			}
			else
				Panel_Desenho.setFerramenta(Ferramenta);
		}
	}
}