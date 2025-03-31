package com.ccsw.tutorial.miam.entities.loan.service;

import com.ccsw.tutorial.miam.entities.loan.model.Loan;
import com.ccsw.tutorial.miam.entities.loan.model.LoanDto;
import com.ccsw.tutorial.miam.entities.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {


    /**
     * Recupera los juegos filtrando opcionalmente por título , categoría y/o fecha
     *
     * @param titleGame    título del juego
     * @param nameCustomer nombre del Cliente
     * @param requestDate  fecha para buscar
     * @return {@link List} de {@link Loan}
     */
    List<Loan> find(String titleGame, String nameCustomer, LocalDate requestDate);


    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPage(LoanSearchDto dto);


    /**
     * Guarda o modifica un juego, dependiendo de si el identificador está o no informado validando
     * si el usuario tiene juegos prestados (max. 2) y si el juego ya está prestado.
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, LoanDto dto) throws Exception;


}
