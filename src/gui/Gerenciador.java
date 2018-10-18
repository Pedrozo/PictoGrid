package gui;

import java.util.*;
import java.util.function.Consumer;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import game.*;

/**
 * Janela principal, responsavel por gerenciar internamente as interfaces do jogo
 * @author Leonardo Pedrozo
 */
public class Gerenciador extends JFrame {

	private JPanel cards;

	/**
	 * Cria um gerenciador para um determinado jogador e um ranking, que serão exibidos na tela inicial
	 * @param jogador jogador que esta jogando
	 * @param ranking ranking atual do jogo, será exibido na tela inicial
	 */
	public Gerenciador(Jogador jogador, Set<Jogador> ranking) {
		super("PictoGrid");
		
		PanelRanking pRanking = new PanelRanking(jogador, ranking);
		PanelConfiguracao pConfig = new PanelConfiguracao(new Iniciador(jogador, ranking));

		cards = new JPanel(new CardLayout());
		cards.add(pRanking, "ranking");
		cards.add(pConfig, "config");

		pRanking.addActionListener(e -> {
			CardLayout layout = (CardLayout) cards.getLayout();
			layout.show(cards, "config");
		});
		

		add(cards);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,300);
		setVisible(true);
	}

	/**
	 * Configura partida e inicia o jogo (inicia processo de desenho)
	 * @author Leonardo Pedrozo
	*/
	private class Iniciador implements Consumer<Configuracao> {

		private Random random;
		private Jogador jogador;
		private Set<Jogador> ranking;

		/**
		 * @param jogador jogador que iniciara a partida
		 * @param ranking ranking do jogo
		*/
		public Iniciador(Jogador jogador, Set<Jogador> ranking) {
			this.jogador = jogador;
			this.ranking = ranking;
			random = new Random();
		}


		/**
		 * Método que inicia a partida, configura um jogo aleatório de acordo com as configurações desejadas
		 * @param config configuração do jogo para a partida que será gerada aleatóriamente
		*/
		@Override
		public void accept(Configuracao config) {

			Grade jogo = new Grade(config.linhas, config.colunas);
			Grade solucao;

			int n = 0;

			for(int i = 0; i < config.cinza; i++, n++) {
				jogo.setPeca(n / config.colunas, n % config.colunas, Peca.CINZA);
			}

			for(int i = 0; i < config.ciano; i++, n++) {
				jogo.setPeca(n / config.colunas, n % config.colunas, Peca.CIANO);
			}

			for(int i = 0; i < config.laranja; i++, n++) {
				jogo.setPeca(n / config.colunas, n % config.colunas, Peca.LARANJA);
			}

			for(int i = 0; i < config.linhas; i++) {
				for(int j = 0; j < config.colunas; j++) {

					int k = random.nextInt(config.linhas);
					int l = random.nextInt(config.colunas);

					Peca tmp = jogo.getPeca(k, l);
					jogo.setPeca(k, l, jogo.getPeca(i, j));
					jogo.setPeca(i, j, tmp);
				}
			}

			solucao = jogo.clone();

			for(int i = 0; i < config.linhas; i++) {
				for(int j = 0; j < config.colunas; j++) {

					int k = random.nextInt(config.linhas);
					int l = random.nextInt(config.colunas);

					if(solucao.getPeca(i, j) != Peca.LARANJA && solucao.getPeca(k, l) != Peca.LARANJA) {
						Peca tmp = solucao.getPeca(k, l);
						solucao.setPeca(k, l, solucao.getPeca(i, j));
						solucao.setPeca(i, j, tmp);
					}
				}
			}

			PictoGrid picto = null;
			Grid grid = null;
			try {
				grid = new Grid(15, solucao);
				picto = new PictoGrid(50, jogo, solucao, new GameOver(jogador, cards, ranking));
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(0);
			}

			grid.setPreferredSize(new Dimension(15 * config.colunas, 15 * config.linhas));

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(grid, BorderLayout.PAGE_START);
			panel.add(picto, BorderLayout.CENTER);
			cards.add(panel, "pictogrid");


			CardLayout layout = (CardLayout) cards.getLayout();
			layout.show(cards, "pictogrid");
		}
	}

	/**
	 * Representa o que sera executado quando o jogo acabar
	 * @author Leonardo Pedrozo
	 */
	private class GameOver implements Runnable {

		private JPanel cards;
		private Jogador jogador;
		private Set<Jogador> ranking;


		/**
		 * @param jogador jogador que venceu a partida
		 * @param cards painel que fara a transição para a tela de vitória
		 * @param ranking ranking do jogo
		 */
		public GameOver(Jogador jogador, JPanel cards, Set<Jogador> ranking) {
			this.cards = cards;
			this.jogador = jogador;
			this.ranking = ranking;
		}

		/**
		 * Apresenta o resultado do jogo e pergunta se o usuário quer jogar novamente
		 */
		@Override
		public void run() {
			
			jogador.addVitoria();

			try {
				saveGame();
			} catch(IOException e) {
				JOptionPane.showMessageDialog(null, "Nao foi possivel salvar o jogo", "IOException", JOptionPane.ERROR_MESSAGE);
			}


			PanelRanking pRanking = new PanelRanking(jogador, ranking);

			pRanking.addActionListener(e -> {
				CardLayout layout = (CardLayout) cards.getLayout();
				layout.show(cards, "config");
			});

			ranking.remove(jogador);
			ranking.add(jogador);

			JOptionPane.showMessageDialog(null, "You Win!");

			CardLayout layout = (CardLayout) cards.getLayout();

			switch(JOptionPane.showConfirmDialog(null, "Deseja jogar novamente ?", "Replay", JOptionPane.YES_NO_OPTION)) {

				case 0:
					layout.show(cards, "config");
					break;

				case 1:
					cards.add(pRanking, "ranking");
					layout.show(cards, "ranking");
					break;
			}
		}

		/**
		 * Salva o jogo em um arquivo
		 * @throws IOException caso exista algum erro de persistência
		 */
		public void saveGame() throws IOException {
			ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream("ranking"));
			saida.writeObject(ranking);
			saida.flush();
			saida.close();
		}
	}
}