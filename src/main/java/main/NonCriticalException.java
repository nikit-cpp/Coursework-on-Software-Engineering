package main;
/**
 * Данный класс служит для некритических исключений, поймав которое, можно продолжать работу
 * @author Ник
 *
 */
public class NonCriticalException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NonCriticalException(String message) {
        super(message);
	}
}
