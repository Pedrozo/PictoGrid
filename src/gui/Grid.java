package gui;

import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;

import game.*;

/**
 * Desenha as partes do jogo com Java 2D
 * @author Leonardo Pedrozo
 */
public class Grid extends JPanel {

	private Image gray;
	private Image cyan;
	private Image orange;
	private Image border;

	private int size;

	private int linhas;
	private int colunas;

	private Grade grade;

	/**
	 * @param size tamanho dos blocos
	 * @param grade grade (esqueleto) do jogo
	 * @throws IOException se estiver faltando algum dos arquivos de imagem
	 */
	public Grid(int size, Grade grade) throws IOException {
		super();

		this.size = size;
		this.linhas = grade.getLinhas();
		this.colunas = grade.getColunas();
		this.grade = grade;
		
		gray = ImageIO.read(new File("img/gray.png"));
		cyan = ImageIO.read(new File("img/cyan.png"));
		orange = ImageIO.read(new File("img/orange.png"));
		border = ImageIO.read(new File("img/border.png"));

		gray = gray.getScaledInstance(size, size, 0);
		cyan = cyan.getScaledInstance(size, size, 0);
		orange = orange.getScaledInstance(size, size, 0);
		border = border.getScaledInstance(size, size, 0);
	}

	/**
	 * @param size tamanho dos blocos
	 * @param linhas quantidade de linhas da grade
	 * @param colunas quantidade de colunas da grade
	 * @throws IOException se estiver faltando algum dos arquivos de imagem
	 */
	public Grid(int size, int linhas, int colunas) throws IOException {
		this(size, new Grade(linhas, colunas));
	}

	/**
	 * Desenha o jogo com Java 2D. Desenha as linhas da grade e coloca imagens das peças
	 * @param g objeto onde será realizado o desenho
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;

		/* DESENHA A GRADE DE FUNDO DO JOGO E AS PEÇAS */

		int pos_x;
		int pos_y = (getHeight() - size * linhas) / 2;

		for(int i = 0; i < linhas; i++) {

			pos_x = (getWidth() - size * colunas) / 2;

			for(int j = 0; j < colunas; j++) {

				Image atual = null;

				switch(grade.getPeca(i,j)) {

					case VAZIO:
						atual = border;
						break;

					case CINZA:
						atual = gray;
						break;

					case CIANO:
						atual = cyan;
						break;

					case LARANJA:
						atual = orange;
						break;
				}

				g2.drawImage(atual, pos_x, pos_y, null);
				pos_x += size;
			}

			pos_y += size;
		}
	}

	/**
	 * Getter para a grade interna do desenho
	 * @return grade intera que esta sendo desenhada
	 */
	public Grade getGrade() {
		return grade;
	}
}