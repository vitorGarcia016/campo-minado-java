package br.com.vitor.campo_minado.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.vitor.campo_minado.excecao.ExplosaoException;
import br.com.vitor.campo_minado.excecao.SairException;
import br.com.vitor.campo_minado.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner leitor = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;

		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;

			while (continuar) {
				loopJogo();
				System.out.println("Deseja continuar? (S/n) ");
				String respostaUsuario = leitor.nextLine();

				if (respostaUsuario.equalsIgnoreCase("n")) {
					continuar = false;
				} else {
					tabuleiro.reiniciarJogo();
				}
			}
		} catch (SairException e) {
			System.out.println("Tchau!!");
		} finally {
			leitor.close();
		}
	}

	private void loopJogo() {
		try {
			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);

				String digitado = ValorDigitado("Digite (x,y): ");

				Iterator<Integer> coordenadas = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim()))
						.iterator();

				digitado = ValorDigitado("1- Abrir 2- Marcar ou Desmarcar: ");

				if ("1".equals(digitado)) {
					tabuleiro.abrir(coordenadas.next(), coordenadas.next());
				} else if ("2".equals(digitado)) {
					tabuleiro.marcar(coordenadas.next(), coordenadas.next());
				}
			}

			System.out.println(tabuleiro);
			System.out.println("Você Ganhou");

		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você Perdeu");
		}

	}

	private String ValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = leitor.nextLine();

		if ("Sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
}
