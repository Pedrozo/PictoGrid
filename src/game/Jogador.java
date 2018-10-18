package game;

/**
 * Representa um jogador
 * @author Leonardo Pedrozo
*/
public class Jogador implements Comparable<Jogador>, java.io.Serializable {

	private String nome;
	private String nick;
	private int vitorias;

	/**
	 * @param nome nome do jogador
	 * @param nick apelido do jogador
	 * @param vitorias quantidade de vitorias do jogador
	*/
	public Jogador(String nome, String nick, int vitorias) {
		setNome(nome);
		setNick(nick);
		setVitorias(vitorias);
	}
	
	/**
	 * @param nome nome do jogador
	 * @param nick apelido do jogador
	*/
	public Jogador(String nome, String nick) {
		this(nome, nick, 0);	
	}

	public String getNome() {
		return nome;
	}

	public String getNick() {
		return nick;
	}

	public int getVitorias() {
		return vitorias;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}

	public void addVitoria() {
		vitorias++;
	}

	@Override
	public int compareTo(Jogador jogador) {
		
		if(vitorias == jogador.vitorias)
			return nick.compareTo(jogador.nick);
		else
			return jogador.vitorias - vitorias;
	}

	@Override
	public String toString() {
		return nick + " " + vitorias;
	}
}