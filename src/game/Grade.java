package game;

/**
 * Esqueleto do jogo. Representação mais básica da grade do jogo
 * @author Leonardo Pedrozo
 */
public class Grade implements Cloneable {

	private int linhas;
	private int colunas;
	private Peca[][] grade;

	/**
	 * Cria uma grade sem peças
	 * @param linhas quantidade de linhas da grade
	 * @param colunas quantidade de colunas da grade
	*/
	public Grade(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		grade = new Peca[linhas][colunas];

		for(int i = 0; i < linhas; i++)
			for(int j = 0; j < colunas; j++)
				grade[i][j] = Peca.VAZIO;
	}

	/**
	 * @return quantidade de linhas do jogo
	 */
	public int getLinhas() {
		return linhas;
	}

	/**
	 * @return quantidade de colunas do jogo
	 */
	public int getColunas() {
		return colunas;
	}

	/**
	 * Recupera uma peça de uma determinada posição do jogo
	 * @param linha linha que se encontra a peça
	 * @param coluna coluna que se encontra a peça
	 * @return peça naquela posição (vazio é uma possibilidade)
	*/
	public Peca getPeca(int linha, int coluna) {
		return grade[linha][coluna];
	}

	/**
	 * Atribui uma peça a uma determinada posição do jogo
	 * @param linha linha que se deseja colocar a peça
	 * @param coluna coluna que se deseja colocar a peça
	 * @param peca peça que será colocada
	*/
	public void setPeca(int linha, int coluna, Peca peca) {
		grade[linha][coluna] = peca;
	}

	/**
	 * Move todas as peças de uma linha ou coluna para uma determinada direção
	 * @param indice indice da linha/coluna que será movimentada 
	 * @param direcao direção que as peças serão movimentadas
	*/
	public void mover(int indice, Direcao direcao) {

		int vazio;

		if(direcao == Direcao.DIREITA) {

			vazio = -1;

			for(int i = 0; i < colunas; i++) {

				if(grade[indice][i] == Peca.VAZIO) {
					vazio = i;
					break;
				}
			}

			if(vazio == -1)
				return;

			for(int i = 0; i < colunas - 1; i++) {

				if(grade[indice][vazio] == Peca.VAZIO) {

					if(vazio == 0 && grade[indice][colunas - 1] == Peca.CIANO) {

						grade[indice][0] = Peca.CIANO;
						grade[indice][colunas - 1] = Peca.VAZIO;

					} else if(vazio != 0 && (grade[indice][vazio - 1] == Peca.CINZA || grade[indice][vazio - 1] == Peca.CIANO)) {

						Peca tmp = grade[indice][vazio];
						grade[indice][vazio] = grade[indice][vazio - 1];
						grade[indice][vazio - 1] = tmp;
					}

				}

				vazio--;

				if(vazio < 0)
					vazio = colunas - 1;
			}
		}

		if(direcao == Direcao.ESQUERDA) {

			vazio = -1;

			for(int i = 0; i < colunas; i++) {

				if(grade[indice][i] == Peca.VAZIO) {
					vazio = i;
					break;
				}
			}

			if(vazio == -1)
				return;

			for(int i = 0; i < colunas - 1; i++) {

				if(grade[indice][vazio] == Peca.VAZIO) {

					if(vazio == colunas - 1 && grade[indice][0] == Peca.CIANO) {

						grade[indice][0] = Peca.VAZIO;
						grade[indice][colunas - 1] = Peca.CIANO;

					} else if(vazio != colunas - 1 && (grade[indice][vazio + 1] == Peca.CINZA || grade[indice][vazio + 1] == Peca.CIANO)) {

						Peca tmp = grade[indice][vazio];
						grade[indice][vazio] = grade[indice][vazio + 1];
						grade[indice][vazio + 1] = tmp;
					}
				}

				vazio++;

				if(vazio == colunas)
					vazio = 0;
			}
		}

		if(direcao == Direcao.BAIXO) {

			vazio = -1;

			for(int i = 0; i < linhas; i++) {
				if(grade[i][indice] == Peca.VAZIO) {
					vazio = i;
					break;
				}
			}

			if(vazio == -1)
				return;

			for(int i = 0; i < linhas - 1; i++) {

				if(grade[vazio][indice] == Peca.VAZIO) {

					if(vazio == 0 && grade[linhas - 1][indice] == Peca.CIANO) {
						grade[0][indice] = Peca.CIANO;
						grade[linhas - 1][indice] = Peca.VAZIO;
					} else if(vazio != 0 && (grade[vazio - 1][indice] == Peca.CINZA || grade[vazio - 1][indice] == Peca.CIANO)) {

						Peca tmp = grade[vazio][indice];
						grade[vazio][indice] = grade[vazio - 1][indice];
						grade[vazio - 1][indice] = tmp;
					}

				}

				vazio--;

				if(vazio < 0)
					vazio = linhas - 1;
			}

		}

		if(direcao == Direcao.CIMA) {

			vazio = -1;

			for(int i = 0; i < linhas; i++) {
				if(grade[i][indice] == Peca.VAZIO) {
					vazio = i;
					break;
				}
			}

			if(vazio == -1)
				return;

			for(int i = 0; i < linhas - 1; i++) {

				if(grade[vazio][indice] == Peca.VAZIO) {

					if(vazio == linhas - 1 && grade[0][indice] == Peca.CIANO) {

						grade[0][indice] = Peca.VAZIO;
						grade[linhas - 1][indice] = Peca.CIANO;

					} else if(vazio != linhas - 1 && (grade[vazio + 1][indice] == Peca.CINZA || grade[vazio + 1][indice] == Peca.CIANO)) {

						Peca tmp = grade[vazio][indice];
						grade[vazio][indice] = grade[vazio + 1][indice];
						grade[vazio + 1][indice] = tmp;
					}

				}

				vazio++;

				if(vazio == linhas)
					vazio = 0;
			}

		}
	}

	/**
	 * @return um objeto cópia do atual
	 */
	@Override
	public Grade clone() {

		Grade copia = new Grade(linhas, colunas);
		
		for(int i = 0; i < linhas; i++)
			for(int j = 0; j < colunas; j++)
				copia.grade[i][j] = grade[i][j];

		return copia;
	}

	/** 
	 * @param obj objeto que será comparado
	 * @return se os objetos são do tipo grade e são identicos
	 */
	@Override
	public boolean equals(Object obj) {

		if(obj instanceof Grade) {

			Grade grade = (Grade) obj;

			for(int i = 0; i < linhas; i++)
				for(int j = 0; j < colunas; j++)
					if(getPeca(i,j) != grade.getPeca(i,j))
						return false;

			return true;
		}

		return false;
	}
}