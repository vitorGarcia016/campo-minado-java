package br.com.vitor.campo_minado.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	private ArrayList<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();
	
	public void adicionarObservador(CampoObservador o) {
		observadores.add(o);
	}
	
	private void avisarObservadores(CampoEvento evento) {
		observadores.stream().forEach(o -> o.eventoAconteceu(this, evento));
	}

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

	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
			
			if(marcado) {
				avisarObservadores(CampoEvento.MARCAR);
			}else {
				avisarObservadores(CampoEvento.DESMARCAR);
				
			}
		}
	}

	public boolean abrir() {
		if (!aberto && !marcado) {
			

			if (minado) {
				avisarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
			
			setAberto(true);
			
			if (vizinhacaSegura()) {
				vizinhos.forEach(x -> x.abrir());
			}
			return true;

		} else {
			return false;
		}
	}

	public boolean vizinhacaSegura() {
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
		
		if (aberto) {
			avisarObservadores(CampoEvento.ABRIR);
		}
		
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

	public int minasNaVizinhaca() {
		return (int) vizinhos.stream().filter(x -> x.minado).count();
	}

	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		avisarObservadores(CampoEvento.REINICIAR);
	}

	
}
