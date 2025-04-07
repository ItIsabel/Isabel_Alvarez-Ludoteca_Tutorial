package com.ccsw.tutorial.miam.entities.loan;

import com.ccsw.tutorial.miam.entities.loan.model.Loan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    @EntityGraph(attributePaths = {"customer", "game"})
    List<Loan> findByCustomerId(long id);

    @EntityGraph(attributePaths = {"customer", "game"})
    List<Loan> findByGameId(long id);

}
