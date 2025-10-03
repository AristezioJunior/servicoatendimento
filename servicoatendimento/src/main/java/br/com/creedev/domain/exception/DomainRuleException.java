package br.com.creedev.domain.exception;

public class DomainRuleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DomainRuleException(String message) {
        super(message);
    }

    public DomainRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}