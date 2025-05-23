package com.ccsw.tutorial.miam.cases.loan.service;

import com.ccsw.tutorial.miam.cases.customer.service.CustomerService;
import com.ccsw.tutorial.miam.cases.game.service.GameService;
import com.ccsw.tutorial.miam.cases.loan.LoanRepository;
import com.ccsw.tutorial.miam.cases.loan.LoanSpecification;
import com.ccsw.tutorial.miam.cases.loan.model.Loan;
import com.ccsw.tutorial.miam.cases.loan.model.LoanDto;
import com.ccsw.tutorial.miam.cases.loan.model.LoanSearchDto;
import com.ccsw.tutorial.miam.common.criteria.SearchCriteria;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author miam
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    CustomerService customerService;


    /**
     * {@inheritDoc}
     */
    public Page<Loan> findFilteredPagedLoans(LoanSearchDto searchDto, Pageable pageable) {
        LoanSpecification titleGameSpec = new LoanSpecification(new SearchCriteria("game.title", ":", searchDto.getTitleGame()));
        LoanSpecification nameCustomerSpec = new LoanSpecification(new SearchCriteria("customer.name", ":", searchDto.getNameCustomer()));
        Specification<Loan> spec = Specification.where(titleGameSpec).and(nameCustomerSpec);

        if (searchDto.getRequestDate() != null) {
            Specification<Loan> dateSpec = LoanSpecification.dateBetween(searchDto);
            spec = Specification.where(titleGameSpec).and(nameCustomerSpec).and(dateSpec);
        }
        return this.loanRepository.findAll(spec, pageable);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) throws Exception {

        List<Loan> currentGameLoans = findCurrentGameLoans(dto);

        //Si el cliente tiene 2 prestamos o mas
        if (!customerHasLessThan2Loans(dto)) {
            throw new Exception("El usuario ya tiene 2 préstamos en vigor. No se le pueden prestar más juegos");
        }

        //Si el juego está en la lista, quiere decir que ya está prestado
        if (!currentGameLoans.isEmpty()) {
            throw new Exception("El juego no está disponible. Estará disponible a partir del " + currentGameLoans.get(0).getFinishDate());
        }

        // Si el usuario tiene menos de dos juegos prestados y el juego no se está prestando
        else {
            Loan loan;
            if (id == null) {
                loan = new Loan();
            } else {
                loan = this.loanRepository.findById(id).orElseThrow(() -> new Exception("Loan not found"));
            }

            BeanUtils.copyProperties(dto, loan, "id", "titleGame", "nameCustomer");

            loan.setGame(gameService.get(dto.getGame().getId()));
            loan.setCustomer(customerService.get(dto.getCustomer().getId()));

            this.loanRepository.save(loan);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        if (loanRepository.findById(id) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }

    /**
     * Método para recuperar un listado de {@link Loan}
     *
     * @param dto recoge el cliente, el juego y las fechas qen las que quiere el prestamo. {@link LoanDto}
     * @return TRUE si el cliente tiene menos de dos prestamos
     */
    private boolean customerHasLessThan2Loans(LoanDto dto) {
        List<Loan> prestamosPorCliente = this.loanRepository.findByCustomerId(dto.getCustomer().getId());
        List<Loan> prestamosFiltrados = prestamosPorCliente.stream().filter(e ->
                        !e.getStartDate().isAfter(dto.getFinishDate()) && !e.getFinishDate().isBefore(dto.getStartDate()))
                .collect(Collectors.toList());
        return prestamosFiltrados.size() < 2;
    }

    /**
     * Método para recuperar un listado de {@link Loan}
     *
     * @return una lista vacía si el juego no está prestado o con un juego en caso de que ya esté prestado
     * para saber cual es la fecha de retorno y cuando estará el juego disponible nuevamente.
     */
    private List<Loan> findCurrentGameLoans(LoanDto dto) {
        List<Loan> listGameLoans = loanRepository.findByGameId(dto.getGame().getId());

        return listGameLoans.stream()
                .filter(e -> !e.getStartDate().isAfter(dto.getFinishDate()) && !e.getFinishDate().isBefore(dto.getStartDate()))
                .collect(Collectors.toList());
    }
}