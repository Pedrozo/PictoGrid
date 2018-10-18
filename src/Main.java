import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import game.*;
import gui.*;

/**
 * Jogo PictoGrid
 * @author Leonardo Pedrozo
 */
public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {}

		Set<Jogador> ranking = null;
			
		try {

			ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("ranking"));

			ranking = (Set<Jogador>) entrada.readObject();

			entrada.close();

		} catch(FileNotFoundException fnfe) {
			ranking = new TreeSet<>();
		} catch(IOException ioe) {
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch(ClassNotFoundException cnfe) {
			JOptionPane.showMessageDialog(null, "Classe nao encontrada", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		String nick, nome;

		do {
			nick = JOptionPane.showInputDialog(null, "Qual o seu apelido ? (de 3 a 10 caracteres)");
		} while(nick.length() < 3 || nick.length() > 10);

		
		Jogador jogador = null;

		for(Jogador j : ranking) {

			if(j.getNick().equals(nick)) {
				jogador = j;
				break;
			}
		}


		if(jogador == null) {

			do {
				nome = JOptionPane.showInputDialog(null, "Qual o seu nome completo ? (de 5 a 30 caracteres)");
			} while(nome.length() < 5 || nome.length() > 30);

			jogador = new Jogador(nome, nick);
			ranking.add(jogador);
		}

		try {
			ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream("ranking"));
			saida.writeObject(ranking);
			saida.close();
		} catch(IOException ioe) {
			JOptionPane.showMessageDialog(null, ioe.getMessage(), "IOException", JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
			System.exit(0);
		}

		new Gerenciador(jogador, ranking);
	}

}