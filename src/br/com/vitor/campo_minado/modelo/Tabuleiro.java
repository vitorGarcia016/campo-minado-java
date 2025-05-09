package br.com.vitor.campo_minado.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.vitor.campo_minado.excecao.ExplosaoException;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private int minas;

	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarOsVizinhos();
		sortearMinas();
	}

	public void abrir(int linha, int coluna) {
		try {
			campos.stream().filter(x -> x.getLinha() == linha && x.getColuna() == coluna).findFirst()
					.ifPresent(c -> c.abrir());
			;
		} catch (ExplosaoException e) {
			campos.forEach(x -> x.setAberto(true));
			throw e;
		}
	}

	public void marcar(int linha, int coluna) {
		campos.stream().filter(x -> x.getLinha() == linha && x.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alternarMarcacao());
		;
	}

	private void gerarCampos() {
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				campos.add(new Campo(i, j));
			}
		}

	}

	private void associarOsVizinhos() {

		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}

	private void sortearMinas() {

		int minasArmadas = 0;

		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = (int) campos.stream().filter(x -> x.isMinado()).count();

		} while (minasArmadas < minas);
	}

	public boolean objetivoAlcancado() {

		return campos.stream().allMatch(x -> x.objetivoAlcancado());
	}

	public void reiniciarJogo() {
		campos.stream().forEach(x -> x.reiniciar());
		sortearMinas();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" ");
		for (int x = 0; x < colunas; x++) {
			sb.append(" ");
			sb.append(x);
			sb.append(" ");
		}
		sb.append("\n");

		int x = 0;

		for (int i = 0; i < linhas; i++) {
			sb.append(i);
			sb.append("");
			for (int j = 0; j < colunas; j++) {
				sb.append(" ");
				sb.append(campos.get(x));
				sb.append(" ");
				x++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
