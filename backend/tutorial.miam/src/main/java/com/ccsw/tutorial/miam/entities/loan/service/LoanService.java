package com.ccsw.tutorial.miam.entities.loan.service;

import com.ccsw.tutorial.miam.entities.loan.model.Loan;
import com.ccsw.tutorial.miam.entities.loan.model.LoanDto;
import com.ccsw.tutorial.miam.entities.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoanService {


    /**
     * Recupera los juegos filtrando opcionalmente por título , categoría y/o fecha
     *
     * @param searchDto con datos de paginacion y filtrado
     * @return {@link List} de {@link Loan}
     */
    List<Loan> findFilteredLoans(LoanSearchDto searchDto);


    /**
     * Método para recuperar un listado paginadoy filtrado de {@link Loan}
     *
     * @param searchDto con datos de paginacion y filtrado
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPagedLoans(LoanSearchDto searchDto);


    /**
     * Guarda o modifica un juego, dependiendo de si el identificador está o no informado validando
     * si el usuario tiene juegos prestados (max. 2) y si el juego ya está prestado.
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, LoanDto dto) throws Exception;


}