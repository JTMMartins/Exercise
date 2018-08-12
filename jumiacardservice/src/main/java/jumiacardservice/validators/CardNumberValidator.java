package jumiacardservice.validators;

import org.apache.commons.validator.routines.CreditCardValidator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jumiacardservice.exceptions.CardNumberValidationException;


@Component
public class CardNumberValidator {

	public boolean isValid(final String cardIn) {
		 CreditCardValidator cardValidator = new CreditCardValidator();
		 if(cardValidator.isValid(cardIn)) {
			 return true;
		 }else {
			throw new CardNumberValidationException("Invalid cardNumber");
		 }
	}
}
