package com.ccsw.tutorial.miam.entities.customer.model;

import com.ccsw.tutorial.miam.entities.loan.model.Loan;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author miam
 */

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    /**
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new value of {@link #getName}.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @param loan add value of {@link #loans}.
     */
    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setCustomer(this);
    }

    /**
     * @param loan delete value of {@link #loans}.
     */
    public void deleteLoan(Loan loan) {
        loans.remove(loan);
    }
}
