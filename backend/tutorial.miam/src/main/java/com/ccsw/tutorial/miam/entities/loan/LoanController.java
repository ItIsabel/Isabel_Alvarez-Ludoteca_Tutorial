package com.ccsw.tutorial.miam.entities.loan;

import com.ccsw.tutorial.miam.entities.loan.model.Loan;
import com.ccsw.tutorial.miam.entities.loan.model.LoanDto;
import com.ccsw.tutorial.miam.entities.loan.model.LoanSearchDto;
import com.ccsw.tutorial.miam.entities.loan.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author miam
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar una lista de {@link Loan}
     *
     * @param searchDto un Dto que incluye los filtros.
     * @return {@link List} de {@link LoanDto}
     */
    @Operation(summary = "Find", description = "Method that return a page of Loans")
    @RequestMapping(path = "", method = {RequestMethod.POST})
    public Page<LoanDto> find(@RequestBody(required = false) LoanSearchDto searchDto) {

        Page<Loan> page = loanService.findFilteredPagedLoans(searchDto, searchDto.getPageable().getPageable());
        System.out.println(page);
        return new PageImpl<>(page.getContent().stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList()),
                page.getPageable(), page.getTotalElements());
    }

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Loan")
    @RequestMapping(path = {"", "/{id}"}, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody LoanDto dto) throws Exception {
        loanService.save(id, dto);
    }

    /**
     * Método para borrar un {@link Loan}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.loanService.delete(id);
    }


}