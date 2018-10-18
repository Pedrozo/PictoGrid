package game;

/**
 * Exception de configuração invalida
 * @author Leonardo Pedrozo
*/
public class ConfiguracaoException extends Exception {

	public ConfiguracaoException() {}

	public ConfiguracaoException(String message) {
		super(message);
	}

	public ConfiguracaoException(Throwable cause) {
		super(cause);
	}

	public ConfiguracaoException(String message, Throwable cause) {
		super(message, cause);
	}
}