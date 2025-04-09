package com.ccsw.tutorial.miam.entities.loan.service;

import com.ccsw.tutorial.miam.entities.customer.service.CustomerService;
import com.ccsw.tutorial.miam.entities.game.service.GameService;
import com.ccsw.tutorial.miam.entities.loan.LoanRepository;
import com.ccsw.tutorial.miam.entities.loan.model.Loan;
import com.ccsw.tutorial.miam.entities.loan.model.LoanDto;
import com.ccsw.tutorial.miam.entities.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public List<Loan> findFilteredLoans(LoanSearchDto searchDto) {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPagedLoans(LoanSearchDto searchDto) {
        List<Loan> filteredLoans = this.findFilteredLoans(searchDto);
        Pageable pageable = PageRequest.of(
                searchDto.getPageable().getPageNumber(),
                searchDto.getPageable().getPageSize(),
                Sort.by("id").ascending());
        return new PageImpl<>(filteredLoans, pageable, filteredLoans.size());
    }

    /**
     * {@inheritDoc}
     */
    /*
    public List<Loan> findFilteredLoans(LoanSearchDto searchDto) {
        LoanSpecification titleGameSpec = new LoanSpecification(new SearchCriteria("game.title", ":", searchDto.getTitleGame()));
        LoanSpecification nameCustomerSpec = new LoanSpecification(new SearchCriteria("customer.name", ":", searchDto.getNameCustomer()));

        Specification<Loan> spec = Specification.where(titleGameSpec).and(nameCustomerSpec);

        if (searchDto.getRequestDate() != null) {
            //Sé que hay una parte de este codigo que deberia ir en el topredicate del LoanSpecification...y recibir por parametros el dateRequest,el startDate y el finishDate
            Specification<Loan> dateSpec = (root, query, cb) -> {
                return cb.and(
                        cb.lessThanOrEqualTo(root.get("startDate"), searchDto.getRequestDate()),
                        cb.greaterThanOrEqualTo(root.get("finishDate"), searchDto.getRequestDate())
                );
            };
            spec = Specification.where(titleGameSpec).and(nameCustomerSpec).and(dateSpec);
        }
        return this.loanRepository.findAll(spec);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) throws Exception {
        List<Loan> currentCustomerLoans = findCurrentCustomerLoans(dto);
        List<Loan> currentGameLoans = findCurrentGameLoans(dto);

        // Si el usuario tiene menos de dos juegos prestados y el juego no se está prestando

        if (currentCustomerLoans.size() < 2 && currentGameLoans.isEmpty()) {
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

            //Si la lista tiene 2 prestamos, quiere decir que el cliente ya tiene 2 prestamos

        } else if (currentCustomerLoans.size() >= 2) {
            throw new Exception("El usuario ya tiene 2 préstamos en vigor. No se le pueden prestar más juegos");


            //Si el juego está en la lista, quiere decir que ya está prestado

        } else if (!currentGameLoans.isEmpty()) {
            throw new Exception("El juego no está disponible. Estará disponible a partir del " + currentGameLoans.get(0).getFinishDate());
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
     * @return una lista con los préstamos actuales de un cliente especifico.
     */
    private List<Loan> findCurrentCustomerLoans(LoanDto dto) {
        List<Loan> listCustomerLoans = loanRepository.findByCustomerId(dto.getCustomer().getId());

        return listCustomerLoans.stream()
                .filter(e -> e.getStartDate().isBefore(LocalDate.now()) && e.getFinishDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
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
                .filter(e -> e.getStartDate().isBefore(LocalDate.now()) && e.getFinishDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }
}