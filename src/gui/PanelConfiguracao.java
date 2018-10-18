package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.function.*;
import javax.swing.*;
import javax.swing.event.*;

import game.*;

/**
 * Interface gráfica do painel de configuração de um novo jogo
 * @author Leonardo Pedrozo
 */
public class PanelConfiguracao extends JPanel implements ActionListener {

	private JButton btJogar;
	private JSpinner spLinhas;
	private JSpinner spColunas;
	private JSpinner spCinza;
	private JSpinner spCiano;
	private JSpinner spLaranja;
	private Consumer<Configuracao> consumer;

	/**
 	 * @param con consumer que é responsavel por iniciar o jogo
	 */
	public PanelConfiguracao(Consumer<Configuracao> con) {
		super(new GridBagLayout());

		btJogar = new JButton("Jogar");
		spLinhas = new JSpinner(new SpinnerNumberModel(3, 3, 20, 1));
		spColunas = new JSpinner(new SpinnerNumberModel(3, 3, 20, 1));
		spCinza = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		spCiano = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		spLaranja = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
		consumer = con;

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.LINE_END;

		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("Linhas:"), c);

		c.gridy++;
		add(new JLabel("Colunas:"), c);

		c.gridy++;
		add(new JLabel("Cinza:"), c);

		c.gridy++;
		add(new JLabel("Ciano:"), c);

		c.gridy++;
		add(new JLabel("Laranja"), c);

		c.gridx++;
		c.gridy++;
		add(btJogar, c);

		c.gridy = 0;
		add(spLinhas, c);

		c.gridy++;
		add(spColunas, c);

		c.gridy++;
		add(spCinza, c);

		c.gridy++;
		add(spCiano, c);

		c.gridy++;
		add(spLaranja, c);

		btJogar.addActionListener(this);
	}

	/**
	 * Quando o botão de iniciar partida for pressionado
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			Configuracao config = new Configuracao((Integer) spLinhas.getValue(), (Integer) spColunas.getValue(),
				                                   (Integer) spCinza.getValue(), (Integer) spCiano.getValue(),
				                                   (Integer) spLaranja.getValue());

			consumer.accept(config);
		} catch(ConfiguracaoException ce) {
			JOptionPane.showMessageDialog(null, ce.getMessage(), "ConfiguracaoException", JOptionPane.ERROR_MESSAGE);
		}
	}
}