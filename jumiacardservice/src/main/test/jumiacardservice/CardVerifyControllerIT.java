package jumiacardservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jumiacardservice.dto.ErrorResponse;
import jumiacardservice.dto.ValidateCardResponse;
import jumiacardservice.starter.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CardVerifyControllerIT {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cardValidationRequest_invalidCard_returns409() throws Exception {

        final String cardNumber = "123";

        final MvcResult result = mockMvc.perform(
            get("/card-scheme/verify/{cardNumber}", cardNumber)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andReturn();

        final ErrorResponse response = mapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(response.isSuccess()).isFalse();
    }
    
    @Test
    public void cardValidationRequest_invalidCardWithLetters_returns409() throws Exception {

        final String cardNumber = "552213A001463839";

        final MvcResult result = mockMvc.perform(
            get("/card-scheme/verify/{cardNumber}", cardNumber)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andReturn();

        final ErrorResponse response = mapper.readValue(result.getResponse().getContentAsByteArray(), ErrorResponse.class);

        assertThat(response.isSuccess()).isFalse();
    }
    @Test
    public void cardValidationRequest_validCard_returns200ValidateCardResponse() throws Exception {

        final String cardNumber = "5522139001463839";

        final MvcResult result = mockMvc.perform(
            get("/card-scheme/verify/{cardNumber}", cardNumber)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        final ValidateCardResponse response = mapper.readValue(result.getResponse().getContentAsByteArray(), ValidateCardResponse.class);

        assertThat(response.isSuccess()).isTrue();
    }
}
