package br.com.vitor.campo_minado.modelo;

public class ResultadoEvento {

	private final boolean ganhou;
	

	public boolean isGanhou() {
		return ganhou;
	}
	
	public ResultadoEvento(boolean ganhou) {
		this.ganhou = ganhou;
	}
}
