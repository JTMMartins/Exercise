package jumiacardservice.controllers;

import jumiacardservice.converters.CardDetailsConverter;
import jumiacardservice.dto.ErrorResponse;
import jumiacardservice.dto.ValidateCardApiResponse;
import jumiacardservice.dto.ValidateCardResponse;
import jumiacardservice.exceptions.BinListCommunicationException;
import jumiacardservice.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CardVerifyController {
	
	private final CardService cardService;
	private final CardDetailsConverter cardDetailsConverter;
	
	@GetMapping("/card-scheme/verify/{cardnumber}")
	public ValidateCardResponse validate(@PathVariable("cardnumber") String cardNumber) {

			final ValidateCardApiResponse cardApiResponse = cardService.validateCard(cardNumber);
			return cardDetailsConverter.convert(cardApiResponse);

	}

	@ExceptionHandler(BinListCommunicationException.class)
	public ResponseEntity<ErrorResponse> handleException(final BinListCommunicationException e) {
		log.error("Card validation error {}", e);

		final ErrorResponse errorResponse = new ErrorResponse(false, e.getMessage(), HttpStatus.CONFLICT);
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}
}
