package com.zahir.task;

public class HTTPTaskException extends Exception {


	private static final long serialVersionUID = -6531169591003214067L;

	public HTTPTaskException() {
        super();  
    }

    public HTTPTaskException(String s) {
        super(s);  
    }

    public HTTPTaskException(Throwable throwable) {
        super(throwable);   
    }
    
    public HTTPTaskException(String s, Throwable throwable) {
        super(s, throwable);    
    }
    
}
