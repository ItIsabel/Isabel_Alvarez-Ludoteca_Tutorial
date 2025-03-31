package com.ccsw.tutorial.miam.loan;

import com.ccsw.tutorial.miam.common.pagination.PageableRequest;
import com.ccsw.tutorial.miam.config.ResponsePage;
import com.ccsw.tutorial.miam.entities.customer.model.CustomerDto;
import com.ccsw.tutorial.miam.entities.game.model.GameDto;
import com.ccsw.tutorial.miam.entities.loan.model.LoanDto;
import com.ccsw.tutorial.miam.entities.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {
    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";
    
    private static final String TITLE_PARAM = "titleGame";
    private static final String CUSTOMER_PARAM = "nameCustomer";
    private static final String DATE_PARAM = "requestDate";
    private static final String EXISTS_TITLE = "On Mars";
    private static final int TOTAL_LOANS = 6;
    private static final int PAGE_SIZE = 5;

    ParameterizedTypeReference<List<LoanDto>> responseTypeList = new ParameterizedTypeReference<List<LoanDto>>() {
    };

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<LoanDto>> responseType = new ParameterizedTypeReference<List<LoanDto>>() {
    };
    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    private String getUrlWithParams() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(TITLE_PARAM, "{" + TITLE_PARAM + "}")
                .queryParam(CUSTOMER_PARAM, "{" + CUSTOMER_PARAM + "}")
                .queryParam(DATE_PARAM, "{" + DATE_PARAM + "}")
                .encode()
                .toUriString();
    }

    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {

        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoansInDB() {

        int LOANS_WITH_FILTER = 3;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, null);
        params.put(CUSTOMER_PARAM, null);
        params.put(DATE_PARAM, null);
        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().size());
    }

    @Test
    public void findExistsTitleShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, EXISTS_TITLE);
        params.put(CUSTOMER_PARAM, null);
        params.put(DATE_PARAM, null);


        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsCustomerShouldReturnLoans() {

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, null);
        params.put(CUSTOMER_PARAM, "Isabel Alvarez");
        params.put(DATE_PARAM, null);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);

        assertNotNull(response);
        assertEquals(4, response.getBody().size());
    }

    @Test
    public void findDateShouldReturnCurrentLoans() {
        int LOANS_WITH_FILTER = 1;

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, null);
        params.put(CUSTOMER_PARAM, null);
        params.put(DATE_PARAM, "2025-03-15");

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsTitleCustomerAndDateShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;
        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, "On Mars");
        params.put(CUSTOMER_PARAM, "Isabel Alvarez");
        params.put(DATE_PARAM, "2025-03-15");

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void saveWithoutIdShouldCreateNewLoan() {

        LoanDto dto = new LoanDto();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        dto.setFinishDate(LocalDate.parse("2025-03-15", formatter));
        dto.setStartDate(LocalDate.parse("2025-03-15", formatter));
        dto.setCustomer(customerDto);
        dto.setGame(gameDto);

        Map<String, Object> params = new HashMap<>();
        params.put(TITLE_PARAM, "On Mars");
        params.put(CUSTOMER_PARAM, "Isabel Alvarez");
        params.put(DATE_PARAM, "2025-03-15");


        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
    }
}

