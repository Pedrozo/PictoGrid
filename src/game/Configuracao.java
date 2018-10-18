package game;

/**
 * Representa uma possível configuração do jogo
 * @author Leonardo Pedrozo
 */
public class Configuracao {

	public final int linhas;
	public final int colunas;
	public final int cinza;
	public final int ciano;
	public final int laranja;

	/**
	 * @param linhas quantidade de linhas
	 * @param colunas quantidade de colunas
	 * @param cinza quantidade de blocos cinzas
	 * @param ciano quantidade de blocos cianos
	 * @param laranja quantidade de blocos laranjas
	 * @throws ConfiguracaoException caso não existam blocos ou a quantidade de blocos exceda o maximo
	*/
	public Configuracao(int linhas, int colunas, int cinza, int ciano, int laranja) throws ConfiguracaoException {

		if(linhas * colunas <= cinza + ciano + laranja)
			throw new ConfiguracaoException("A quantidade de blocos excede o tamanho da grade");

		if(cinza == 0 && ciano == 0)
			throw new ConfiguracaoException("Deve haver pelo menos 1 bloco cinza/ciano");

		this.linhas = linhas;
		this.colunas = colunas;
		this.cinza = cinza;
		this.ciano = ciano;
		this.laranja = laranja;
	}
}