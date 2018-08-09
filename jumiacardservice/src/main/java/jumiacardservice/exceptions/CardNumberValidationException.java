package jumiacardservice.exceptions;

public class CardNumberValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public CardNumberValidationException(String message) {
		super(message);
	}
}
