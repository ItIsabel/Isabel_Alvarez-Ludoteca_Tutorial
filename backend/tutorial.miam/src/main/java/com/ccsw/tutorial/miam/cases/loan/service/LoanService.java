package com.ccsw.tutorial.miam.cases.loan.service;

import com.ccsw.tutorial.miam.cases.loan.model.Loan;
import com.ccsw.tutorial.miam.cases.loan.model.LoanDto;
import com.ccsw.tutorial.miam.cases.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanService {


    /**
     * Recupera los prestamos filtrando opcionalmente por título , cliente y/o fecha de forma paginada
     *
     * @param searchDto con datos de paginacion y filtrado
     * @return {@link List} de {@link Loan}
     */
    Page<Loan> findFilteredPagedLoans(LoanSearchDto searchDto, Pageable pageable);


    /**
     * Guarda o modifica un prestamo, dependiendo de si el identificador está o no informado validando
     * si el usuario tiene juegos prestados (max. 2) y si el juego ya está prestado.
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, LoanDto dto) throws Exception;

    /**
     * Borra Préstamos
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;
}