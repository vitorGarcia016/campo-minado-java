package br.com.vitor.campo_minado.visao;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.vitor.campo_minado.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class Painel extends JPanel {

	public Painel(Tabuleiro t) {
		setLayout(new GridLayout(t.getLinhas(), t.getColunas()));

		t.paraCada(c -> add(new BotaoCampo(c)));

		t.registrarObservador(e -> {

			SwingUtilities.invokeLater(() -> {

				if (e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "Você Venceu!!");
				} else {
					JOptionPane.showMessageDialog(this, "Você Perdeu!!");
				}
				
				t.reiniciarJogo();
			});

		});
	}
}
