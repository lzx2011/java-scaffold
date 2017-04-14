package com.lzhenxing.javascaffold.util;

/**
 * Created by gary on 16/4/1.
 */
public class ProcessException extends RuntimeException {
    private static final long serialVersionUID = -7231689344132547461L;
    private String message;

    public ProcessException() {
        super();
    }

    public ProcessException(String message, Throwable cause) {
        super(cause);
        setMessage(message);
    }

    public ProcessException(String message) {
        super(message);
        setMessage(message);
    }

    public ProcessException(Throwable cause) {
        super(cause);
        setMessage(cause.getMessage());
    }

    public String getMessage() {
        if(message == null)
            return super.getMessage();
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void main(String[] args) {
        Exception e=new ProcessException("test ");
        //	System.out.println(e.getMessage());
        ProcessException pe = new ProcessException(e);
        System.out.println(pe.getMessage());
    }
}