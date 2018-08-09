package jumiacardservice.service;

import jumiacardservice.dto.ValidateCardApiResponse;
import jumiacardservice.exceptions.BinListCommunicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CardService {

	public static final String BINLIST_BASE_ENDPOINT = "https://lookup.binlist.net/";

	public ValidateCardApiResponse validateCard(final String CardNumber) {
		

		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.getForObject(BINLIST_BASE_ENDPOINT + CardNumber, ValidateCardApiResponse.class);

		} catch (Exception e) {
			log.error("Error in RemoteService Request");
			throw new BinListCommunicationException(e.getMessage());
		}
	}
}
