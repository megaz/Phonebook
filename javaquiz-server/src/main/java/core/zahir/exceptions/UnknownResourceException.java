package core.zahir.exceptions;

public class UnknownResourceException extends Exception {


	private static final long serialVersionUID = -6531169591003214067L;

	public UnknownResourceException() {
        super();  
    }

    public UnknownResourceException(String s) {
        super(s);  
    }

    public UnknownResourceException(Throwable throwable) {
        super(throwable);   
    }
    
    public UnknownResourceException(String s, Throwable throwable) {
        super(s, throwable);    
    }
    
}
