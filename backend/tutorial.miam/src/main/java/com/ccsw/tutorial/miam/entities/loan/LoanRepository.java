package com.ccsw.tutorial.miam.entities.loan;

import com.ccsw.tutorial.miam.entities.loan.model.Loan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    @EntityGraph(attributePaths = {"customer", "game"})
    List<Loan> findByCustomerId(long id);

    @EntityGraph(attributePaths = {"customer", "game"})
    List<Loan> findByGameId(long id);

}
