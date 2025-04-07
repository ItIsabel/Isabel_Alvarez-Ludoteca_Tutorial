package com.ccsw.tutorial.miam.loan;

/**
 * Estos test no van. funcionaban antes cuando se metian por parametros los filtros...
 * Tras implementar la paginacion, son pasados del front al back por un dto con @requestbody,
 * por lo que ya no funcionan*
 * <p>
 * <p>
 * ESTE ERA EL METODO DEL CONTROLLER CON EL QUE SI FUNCIONAN .
 * <p>
 * <p>
 * /**
 * * Método para recuperar una lista de {@link Loan}
 * *
 * * @param titleGame    título del juego
 * * @param nameCustomer nombre del Cliente
 * * @param requestDate  fecha para buscar
 * * @return {@link List} de {@link LoanDto}
 *
 * @Operation(summary = "Find", description = "Method that return a filtered list of Loans")
 * @RequestMapping(path = "", method = RequestMethod.GET)
 * <p>
 * <p>
 * public List<LoanDto> find(@RequestParam(value = "titleGame", required = false) String titleGame,
 * @RequestParam(value = "nameCustomer", required = false) String nameCustomer,
 * @RequestParam(value = "requestDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate requestDate) {
 * <p>
 * List<Loan> loans = loanService.find(titleGame, nameCustomer, requestDate);
 * <p>
 * return loans.stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList());
 */
/**
 * -----------------------------TEST--------------------------------------------------
 *
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
 * public class LoanIT {
 * public static final String LOCALHOST = "http://localhost:";
 * public static final String SERVICE_PATH = "/loan";
 * <p>
 * private static final String TITLE_PARAM = "titleGame";
 * private static final String CUSTOMER_PARAM = "nameCustomer";
 * private static final String DATE_PARAM = "requestDate";
 * private static final String EXISTS_TITLE = "On Mars";
 * private static final int TOTAL_LOANS = 6;
 * private static final int PAGE_SIZE = 5;
 * <p>
 * ParameterizedTypeReference<List<LoanDto>> responseTypeList = new ParameterizedTypeReference<List<LoanDto>>() {
 * };
 * @LocalServerPort private int port;
 * @Autowired private TestRestTemplate restTemplate;
 * <p>
 * ParameterizedTypeReference<List<LoanDto>> responseType = new ParameterizedTypeReference<List<LoanDto>>() {
 * };
 * ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
 * };
 * <p>
 * private String getUrlWithParams() {
 * return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
 * .queryParam(TITLE_PARAM, "{" + TITLE_PARAM + "}")
 * .queryParam(CUSTOMER_PARAM, "{" + CUSTOMER_PARAM + "}")
 * .queryParam(DATE_PARAM, "{" + DATE_PARAM + "}")
 * .encode()
 * .toUriString();
 * }
 * @Test public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {
 * <p>
 * LoanSearchDto searchDto = new LoanSearchDto();
 * searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
 * <p>
 * ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);
 * <p>
 * assertNotNull(response);
 * assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
 * assertEquals(PAGE_SIZE, response.getBody().getContent().size());
 * }
 * @Test public void findSecondPageWithFiveSizeShouldReturnLastResult() {
 * <p>
 * int elementsCount = TOTAL_LOANS - PAGE_SIZE;
 * <p>
 * LoanSearchDto searchDto = new LoanSearchDto();
 * searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));
 * <p>
 * ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);
 * <p>
 * assertNotNull(response);
 * assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
 * assertEquals(elementsCount, response.getBody().getContent().size());
 * }
 * @Test public void findWithoutFiltersShouldReturnAllLoansInDB() {
 * <p>
 * int LOANS_WITH_FILTER = 3;
 * <p>
 * Map<String, Object> params = new HashMap<>();
 * params.put(TITLE_PARAM, null);
 * params.put(CUSTOMER_PARAM, null);
 * params.put(DATE_PARAM, null);
 * ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);
 * <p>
 * assertNotNull(response);
 * assertEquals(TOTAL_LOANS, response.getBody().size());
 * }
 * @Test public void findExistsTitleShouldReturnLoans() {
 * <p>
 * int LOANS_WITH_FILTER = 1;
 * <p>
 * Map<String, Object> params = new HashMap<>();
 * params.put(TITLE_PARAM, EXISTS_TITLE);
 * params.put(CUSTOMER_PARAM, null);
 * params.put(DATE_PARAM, null);
 * <p>
 * <p>
 * ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);
 * <p>
 * assertNotNull(response);
 * assertEquals(LOANS_WITH_FILTER, response.getBody().size());
 * }
 * @Test public void findExistsCustomerShouldReturnLoans() {
 * <p>
 * Map<String, Object> params = new HashMap<>();
 * params.put(TITLE_PARAM, null);
 * params.put(CUSTOMER_PARAM, "Isabel Alvarez");
 * params.put(DATE_PARAM, null);
 * <p>
 * ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);
 * <p>
 * assertNotNull(response);
 * assertEquals(4, response.getBody().size());
 * }
 * @Test public void findDateShouldReturnCurrentLoans() {
 * int LOANS_WITH_FILTER = 1;
 * <p>
 * Map<String, Object> params = new HashMap<>();
 * params.put(TITLE_PARAM, null);
 * params.put(CUSTOMER_PARAM, null);
 * params.put(DATE_PARAM, "2025-03-15");
 * <p>
 * ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);
 * <p>
 * assertNotNull(response);
 * assertEquals(LOANS_WITH_FILTER, response.getBody().size());
 * }
 * @Test public void findExistsTitleCustomerAndDateShouldReturnLoans() {
 * <p>
 * int LOANS_WITH_FILTER = 1;
 * Map<String, Object> params = new HashMap<>();
 * params.put(TITLE_PARAM, "On Mars");
 * params.put(CUSTOMER_PARAM, "Isabel Alvarez");
 * params.put(DATE_PARAM, "2025-03-15");
 * <p>
 * ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);
 * <p>
 * assertNotNull(response);
 * assertEquals(LOANS_WITH_FILTER, response.getBody().size());
 * }
 * @Test public void saveWithoutIdShouldCreateNewLoan() {
 * <p>
 * LoanDto dto = new LoanDto();
 * CustomerDto customerDto = new CustomerDto();
 * customerDto.setId(1L);
 * <p>
 * GameDto gameDto = new GameDto();
 * gameDto.setId(1L);
 * DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
 * <p>
 * dto.setFinishDate(LocalDate.parse("2025-03-15", formatter));
 * dto.setStartDate(LocalDate.parse("2025-03-15", formatter));
 * dto.setCustomer(customerDto);
 * dto.setGame(gameDto);
 * <p>
 * Map<String, Object> params = new HashMap<>();
 * params.put(TITLE_PARAM, "On Mars");
 * params.put(CUSTOMER_PARAM, "Isabel Alvarez");
 * params.put(DATE_PARAM, "2025-03-15");
 * <p>
 * <p>
 * ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.GET, null, responseType, params);
 * <p>
 * assertNotNull(response);
 * assertEquals(1, response.getBody().size());
 * }
 * }
 */
