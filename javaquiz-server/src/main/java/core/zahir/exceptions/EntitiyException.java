package core.zahir.exceptions;

public class EntitiyException extends RuntimeException {

	private static final long serialVersionUID = 353014994338286091L;

	public EntitiyException() {
		super();
	}

	public EntitiyException(String s) {
		super(s);
	}

	public EntitiyException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public EntitiyException(Throwable throwable) {
		super(throwable);
	}
}
