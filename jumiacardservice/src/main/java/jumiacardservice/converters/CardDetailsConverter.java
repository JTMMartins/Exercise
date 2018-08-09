package jumiacardservice.converters;


import java.util.Optional;
import jumiacardservice.dto.ValidateCardApiResponse;
import jumiacardservice.dto.ValidateCardResponse;
import jumiacardservice.dto.ValidateCardResponse.Payload;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CardDetailsConverter implements Converter<ValidateCardApiResponse, ValidateCardResponse> {

    @Override
    public ValidateCardResponse convert(final ValidateCardApiResponse apiResponse) {

        return ValidateCardResponse.builder()
            .success(true)
            .payload(Payload.builder()
                .scheme(apiResponse.getScheme())
                .type(apiResponse.getType())
                .bank(Optional.ofNullable(apiResponse.getBank())
                    .orElse(null)
                    .getName())
                .build())
            .build();
    }
}
