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
 * Interface gráfica final do jogo. Responsavel por exibir a grade interna e os botões de interação em volta da grade
 * @author Leonardo Pedrozo
 */
public class PictoGrid extends Grid implements MouseListener, MouseMotionListener {

	private Image setaEsquerda;
	private Image setaEsquerdaSelecionada;
	private Image setaCima;
	private Image setaCimaSelecionada;
	private Image setaDireita;
	private Image setaDireitaSelecionada;
	private Image setaBaixo;
	private Image setaBaixoSelecionada;

	private int size;

	private int linhas;
	private int colunas;

	private boolean[] esquerda;
	private boolean[] cima;
	private boolean[] direita;
	private boolean[] baixo;

	private Grade grade; 
	private Grade solucao;
	private Runnable gameOver;

	/**
	 * @param size tamanho dos elementos do jogo
	 * @param grade grade interna do jogo, que será manipulada enquanto o jogo ocorre
	 * @param gameOver ação que será executada quando o jogo acabar
	 * @throws IOException caso algum dos arquivos de imagem não for encontrado
	 */
	public PictoGrid(int size, Grade grade, Grade solucao, Runnable gameOver) throws IOException {
		super(size, grade);

		this.size = size;
		this.linhas = grade.getLinhas();
		this.colunas = grade.getColunas();
		this.grade = grade;
		this.solucao = solucao;
		this.gameOver = gameOver;

		esquerda = new boolean[linhas];
		cima = new boolean[colunas];
		direita = new boolean[linhas];
		baixo = new boolean[colunas];


		setaEsquerda = ImageIO.read(new File("img/seta_esquerda.png"));
		setaDireita = ImageIO.read(new File("img/seta_direita.png"));
		setaCima = ImageIO.read(new File("img/seta_cima.png"));
		setaBaixo = ImageIO.read(new File("img/seta_baixo.png"));
		setaEsquerdaSelecionada = ImageIO.read(new File("img/seta_esquerda_selecionada.png"));
		setaDireitaSelecionada = ImageIO.read(new File("img/seta_direita_selecionada.png"));
		setaCimaSelecionada = ImageIO.read(new File("img/seta_cima_selecionada.png"));
		setaBaixoSelecionada = ImageIO.read(new File("img/seta_baixo_selecionada.png"));

		setaEsquerda = setaEsquerda.getScaledInstance(size, size, 0);
		setaEsquerdaSelecionada = setaEsquerdaSelecionada.getScaledInstance(size, size, 0);
		setaCima = setaCima.getScaledInstance(size, size, 0);
		setaCimaSelecionada = setaCimaSelecionada.getScaledInstance(size, size, 0);
		setaDireita = setaDireita.getScaledInstance(size, size, 0);
		setaDireitaSelecionada = setaDireitaSelecionada.getScaledInstance(size, size, 0);
		setaBaixo = setaBaixo.getScaledInstance(size, size, 0);
		setaBaixoSelecionada = setaBaixoSelecionada.getScaledInstance(size, size, 0);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Efetua todo o desenho do jogo, incluido as partes interativas
	 * @param g objeto onde será desenhado
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		int pos_x;
		int pos_y;
		

		/* DESENHA AS SETAS LATERAIS ESQUERDAS */
		pos_x = (getWidth() - size * colunas) / 2;
		pos_x -= size;

		pos_y = (getHeight() - size * linhas) / 2;

		for(int i = 0; i < linhas; i++) {

			if(esquerda[i])
				g2.drawImage(setaEsquerdaSelecionada, pos_x, pos_y, null);
			else
				g2.drawImage(setaEsquerda, pos_x, pos_y, null);

			pos_y += size;
		}


		/* DESENHA AS SETAS SUPERIORES */

		pos_x = (getWidth() - size * colunas) / 2;
		pos_y = (getHeight() - size * linhas) / 2;
		pos_y -= size;

		for(int i = 0; i < colunas; i++) {

			if(cima[i])
				g2.drawImage(setaCimaSelecionada, pos_x, pos_y, null);
			else
				g2.drawImage(setaCima, pos_x, pos_y, null);

			pos_x += size;
		}
 

		/* DESENHA AS SETAS LATERIAS DA DIREITA */

		pos_x = getWidth() - (getWidth() - size * colunas) / 2;
		pos_y = (getHeight() - size * linhas) / 2;

		for(int i = 0; i < linhas; i++) {

			if(direita[i])
				g2.drawImage(setaDireitaSelecionada, pos_x, pos_y, null);
			else
				g2.drawImage(setaDireita, pos_x, pos_y, null);

			pos_y += size;
		}


		/* DESENHA AS SETAS DE INFERIORES */

		pos_x = (getWidth() - size * colunas) / 2;
		pos_y = getHeight() - (getHeight() - size * linhas) / 2;

		for(int i = 0; i < colunas; i++) {

			if(baixo[i])
				g2.drawImage(setaBaixoSelecionada, pos_x, pos_y, null);
			else
				g2.drawImage(setaBaixo, pos_x, pos_y, null);

			pos_x += size;
		}
	}

	/**
	 * Quando o jogador clicar em algum dos botões do jogo
	 * @param e mouse event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		x -= (getWidth() - size * colunas) / 2;
		y -= (getHeight() - size * linhas) / 2;

		if(x > -size && x < 0 && y > 0 && y < size * linhas)
			grade.mover(y / size, Direcao.DIREITA);
		
		if(x > size * colunas && x < size * colunas + size && y > 0 && y < size * linhas)
			grade.mover(y / size, Direcao.ESQUERDA);

		if(y > -size && y < 0 && x > 0 && x < size * colunas)
			grade.mover(x / size, Direcao.BAIXO);

		if(y > size * linhas && y < size * linhas + size && x > 0 && x < size * colunas)
			grade.mover(x / size, Direcao.CIMA);
		
		repaint();

		if(grade.equals(solucao))
			gameOver.run();
	}

	/**
	 * Quando o mouse estiver em cima de algum dos botões do jogo
	 * @param e mouse event
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		int pos_x = (getWidth() - size * colunas) / 2;
		int pos_y = (getHeight() - size * linhas) / 2;

		int linha = (e.getY() - pos_y);
		int coluna = (e.getX() - pos_x);

		for(int i = 0; i < esquerda.length; i++) {

			if(coluna > -size && coluna < 0 && linha > 0 && linha / size == i)
				esquerda[i] = true;
			else
				esquerda[i] = false;

		}

		for(int i = 0; i < cima.length; i++) {

			if(linha > -size && linha < 0 && coluna > 0 && coluna / size == i)
				cima[i] = true;
			else
				cima[i] = false;

		}

		for(int i = 0; i < direita.length; i++) {

			if(coluna > colunas * size && coluna < colunas * size + size && linha > 0 && linha / size == i)
				direita[i] = true;
			else
				direita[i] = false;
		}


		for(int i = 0; i < baixo.length; i++) {

			if(linha > linhas * size && linha < linhas * size + size && coluna > 0 && coluna / size == i)
				baixo[i] = true;
			else
				baixo[i] = false;
		}
		
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}
}