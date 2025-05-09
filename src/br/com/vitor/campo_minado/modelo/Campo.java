package br.com.vitor.campo_minado.modelo;

import java.util.ArrayList;

import br.com.vitor.campo_minado.excecao.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	private ArrayList<Campo> vizinhos = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	public boolean adicionarVizinho(Campo campo) {
		boolean linhaDiferente = linha != campo.linha;
		boolean colunaDiferente = coluna != campo.coluna;

		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - campo.linha);
		int deltaColuna = Math.abs(coluna - campo.coluna);
		int deltaGeral = deltaLinha + deltaColuna;

		if (diagonal && deltaGeral == 2) {
			vizinhos.add(campo);
			return true;
		} else if (!diagonal && deltaGeral == 1) {
			vizinhos.add(campo);
			return true;
		} else {
			return false;
		}

	}

	void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
		}
	}

	boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;

			if (minado) {
				throw new ExplosaoException();
			}
			if (vizinhacaSegura()) {
				vizinhos.forEach(x -> x.abrir());
			}
			return true;

		} else {
			return false;
		}
	}

	boolean vizinhacaSegura() {
		return vizinhos.stream().noneMatch(x -> x.minado);
	}

	void minar() {
		minado = true;
	}

	public boolean isMarcado() {
		return marcado;
	}

	public boolean isMinado() {
		return minado;
	}

	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isAberto() {
		return aberto;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}

	long minasNaVizinhaca() {
		return vizinhos.stream().filter(x -> x.minado).count();
	}

	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}

	@Override
	public String toString() {

		if (marcado) {
			return "X";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhaca() > 0) {
			return Long.toString(minasNaVizinhaca());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}

	}
}
