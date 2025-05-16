package br.com.vitor.campo_minado.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tabuleiro implements CampoObservador {

	private final int linhas;
	private final int colunas;
	private final int minas;
	
	

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

	public void registrarObservador(Consumer<ResultadoEvento> o) {
		observadores.add(o);
	}
	
	public void paraCada(Consumer<Campo> x) {
		campos.forEach(x);
	}

	public void notificarObservadores(boolean resultado) {
		observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarOsVizinhos();
		sortearMinas();
	}

	@Override
	public void eventoAconteceu(Campo c, CampoEvento e) {
		if (e == CampoEvento.EXPLODIR) {
			exibirMinas();
			notificarObservadores(false);
			
		} else if (objetivoAlcancado()) {
			System.out.println("ganhou");
			notificarObservadores(true);
		}

	}

	public void abrir(int linha, int coluna) {

		campos.stream().filter(x -> x.getLinha() == linha && x.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.abrir());

	}

	private void exibirMinas() {
		campos
		.stream()
		.filter(x -> x.isMinado())
		.filter(x -> !x.isMarcado())
		.forEach(x -> x.setAberto(true));

	}

	public void marcar(int linha, int coluna) {
		campos.stream().filter(x -> x.getLinha() == linha && x.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alternarMarcacao());
		;
	}

	private void gerarCampos() {
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				Campo campo = new Campo(i, j);
				campo.adicionarObservador(this);
				campos.add(campo);
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

}
