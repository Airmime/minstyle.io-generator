package io.minstyle.msgenerator.exception;

/**
 * Exception for wrong hex color.
 *
 * @author Rémi Marion
 * @version 0.0.1
 */
public class BadColorException extends RuntimeException{

	private static final long serialVersionUID = 973028448049059276L;

	public BadColorException() {
        super("A colors hexadecimal format is expected.");
    }
}
