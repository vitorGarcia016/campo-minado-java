package br.com.vitor.campo_minado.visao;

import javax.swing.JFrame;

import br.com.vitor.campo_minado.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 30);
		Painel painel = new Painel(tabuleiro);
		
		add(painel);
		
		setTitle("Campo Minado");
		setLocationRelativeTo(null);
		setSize(690, 438);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new TelaPrincipal();
		
	}
}
