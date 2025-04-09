package com.ccsw.tutorial.miam.loan;

import com.ccsw.tutorial.miam.common.pagination.PageableRequest;
import com.ccsw.tutorial.miam.config.ResponsePage;
import com.ccsw.tutorial.miam.entities.customer.model.CustomerDto;
import com.ccsw.tutorial.miam.entities.game.model.GameDto;
import com.ccsw.tutorial.miam.entities.loan.model.LoanDto;
import com.ccsw.tutorial.miam.entities.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class LoanIT2 {
    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    private static final String EXISTS_TITLE = "On Mars";
    private static final int TOTAL_LOANS = 6;
    private static final int PAGE_SIZE = 5;

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {
        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoansInDB() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(TOTAL_LOANS, response.getBody().getContent().size());
    }

    @Test
    public void findExistsTitleShouldReturnLoans() {
        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setTitleGame(EXISTS_TITLE);
        searchDto.setPageable(new PageableRequest(0, 10));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(LOANS_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void findExistsCustomerShouldReturnLoans() {
        int LOANS_WITH_FILTER = 4;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setNameCustomer("Isabel Alvarez");
        searchDto.setPageable(new PageableRequest(0, 10));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(LOANS_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void findDateShouldReturnCurrentLoans() {
        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setRequestDate(LocalDate.parse("2025-03-15"));
        searchDto.setPageable(new PageableRequest(0, 10));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(LOANS_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void findExistsTitleCustomerAndDateShouldReturnLoans() {
        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setTitleGame("On Mars");
        searchDto.setNameCustomer("Isabel Alvarez");
        searchDto.setRequestDate(LocalDate.parse("2025-03-15"));
        searchDto.setPageable(new PageableRequest(0, 10));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(LOANS_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void saveWithoutIdShouldCreateNewLoan() {
        // Esta prueba parece ser diferente y probablemente necesite otro endpoint
        // Creamos el LoanDto para guardar
        LoanDto dto = new LoanDto();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);

        dto.setFinishDate(LocalDate.parse("2025-03-15"));
        dto.setStartDate(LocalDate.parse("2025-03-15"));
        dto.setCustomer(customerDto);
        dto.setGame(gameDto);

        // Guardamos el nuevo préstamo
        ResponseEntity<LoanDto> saveResponse = restTemplate.postForEntity(
                LOCALHOST + port + SERVICE_PATH + "/save",
                dto,
                LoanDto.class
        );

        assertNotNull(saveResponse);
        assertNotNull(saveResponse.getBody().getId());

        // Verificamos que se ha guardado consultando con los filtros adecuados
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setTitleGame("On Mars");  // Asumiendo que este es el título asociado al GameId 1
        searchDto.setNameCustomer("Isabel Alvarez");  // Asumiendo que este es el nombre asociado al CustomerId 1
        searchDto.setRequestDate(LocalDate.parse("2025-03-15"));
        searchDto.setPageable(new PageableRequest(0, 10));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST,
                new HttpEntity<>(searchDto),
                responseTypePage
        );

        assertNotNull(response);
        assertTrue(response.getBody().getTotalElements() >= 1);
    }
}

