package com.ccsw.tutorial.miam.loan;

import com.ccsw.tutorial.miam.entities.customer.model.CustomerDto;
import com.ccsw.tutorial.miam.entities.game.model.GameDto;
import com.ccsw.tutorial.miam.entities.game.service.GameServiceImpl;
import com.ccsw.tutorial.miam.entities.loan.LoanRepository;
import com.ccsw.tutorial.miam.entities.loan.model.Loan;
import com.ccsw.tutorial.miam.entities.loan.model.LoanDto;
import com.ccsw.tutorial.miam.entities.loan.service.LoanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanTest {
    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long EXISTS_CUSTOMER_ID = 1L;
    public static final Long NOT_EXISTS_CUSTOMER_ID = 0L;
    public static final String EXIST_CUSTOMER_NAME = "Isabel Alvarez";

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @InjectMocks
    private GameServiceImpl gameService;


    @Test
    public void saveLoanShouldEnsureCustomerDoesNotHaveMoreThanTwoGames() {
        CustomerDto customer = new CustomerDto();
        customer.setId(EXISTS_CUSTOMER_ID);

        //creo el prestamo que intentaré introducir con un usuario
        LoanDto loanDto = new LoanDto();
        loanDto.setGame(new GameDto());
        loanDto.setCustomer(customer);
        loanDto.setStartDate(LocalDate.now());
        loanDto.setFinishDate(LocalDate.now().plusDays(7));

        //testing con un array de 2 prestamos ya existentes con el mismo usuario.
        Loan loan = mock(Loan.class);
        when(loanRepository.findByCustomerId(EXISTS_CUSTOMER_ID)).thenReturn(Arrays.asList(new Loan(), new Loan()));
        assertThrows(Exception.class, () -> {
            loanService.save(null, loanDto);
        });
    }

    @Test
    public void saveLoanShouldEnsureGameIsNotLoaned() {
        GameDto game = new GameDto();
        game.setId(EXISTS_GAME_ID);

        // Creo el préstamo con el juego que intentaré introducir
        LoanDto loanDto = new LoanDto();
        loanDto.setGame(game);
        loanDto.setCustomer(new CustomerDto());
        loanDto.setStartDate(LocalDate.now());
        loanDto.setFinishDate(LocalDate.now().plusDays(7));

        // Mockeo el repositorio y el servicio
        Loan loan = mock(Loan.class);
        when(loanRepository.findByGameId(EXISTS_GAME_ID)).thenReturn(Arrays.asList(new Loan()));

        // Verifico que se lanza una excepción si el juego ya está prestado
        assertThrows(Exception.class, () -> {
            loanService.save(null, loanDto);
        });
    }

}





