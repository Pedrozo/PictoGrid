package gui;

import java.util.Set;
import java.util.TreeSet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import game.*;

/**
 * Interface grafica do ranking do jogo
 * @author Leonardo Pedrozo
*/
public class PanelRanking extends JPanel {

	private Set<Jogador> ranking;
	private JTable tbRanking;
	private JButton btJogar;

	/**
	 * @param jogador jogador atual, seus dados serão exibidos fora da tela do ranking
	 * @param ranking ranking do jogo, seus dados serão exibidos em uma tabela
	 */
	public PanelRanking(Jogador jogador, Set<Jogador> ranking) {
		super(new BorderLayout());

		this.ranking = ranking;

		JPanel superior = new JPanel(new BorderLayout());

		JLabel lbNick = new JLabel("Jogador: " + jogador.getNick());
		JLabel lbVitorias = new JLabel("Vitorias: " + jogador.getVitorias());

		lbNick.setFont(new Font("Monospaced", 1, 24));
		lbVitorias.setFont(new Font("Monospaced", 1, 24));

		superior.add(lbNick, BorderLayout.LINE_START);
		superior.add(lbVitorias, BorderLayout.LINE_END);
		superior.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.PAGE_END);

		add(superior, BorderLayout.PAGE_START);


		JPanel central = new JPanel();

		Object[][] conteudo = new Object[ranking.size()][2];

		int i = 0;
		for(Jogador j : ranking) {

			conteudo[i][0] = j.getNick();
			conteudo[i][1] = j.getVitorias();

			i++;
		}

		tbRanking = new JTable(conteudo, new String[] {"Nick", "Vitorias"});

		central.add(new JScrollPane(tbRanking));

		add(central, BorderLayout.CENTER);

		JPanel inferior = new JPanel();

		btJogar = new JButton("Configurar Partida");

		inferior.add(btJogar);

		add(inferior, BorderLayout.PAGE_END);
	}

	/**
	 * Define qual é a ação que ocorrera quando pressionarem o botão "jogar"
	 * @param a listener responsavel pela ação
	*/
	public void addActionListener(ActionListener a) {
		btJogar.addActionListener(a);
	}
}