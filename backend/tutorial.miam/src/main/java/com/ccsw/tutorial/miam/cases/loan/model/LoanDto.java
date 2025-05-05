package com.ccsw.tutorial.miam.cases.loan.model;

import com.ccsw.tutorial.miam.cases.customer.model.CustomerDto;
import com.ccsw.tutorial.miam.cases.game.model.GameDto;

import java.time.LocalDate;

/**
 * @author miam
 */
public class LoanDto {

    private Long id;

    private GameDto game;

    private CustomerDto customer;

    private LocalDate startDate;

    private LocalDate finishDate;

    /**
     * @return id
     */
    public Long getId() {

        return id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return {@link GameDto}
     */
    public GameDto getGame() {

        return game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(GameDto game) {

        this.game = game;
    }

    /**
     * @return {@link CustomerDto}
     */
    public CustomerDto getCustomer() {

        return customer;
    }

    /**
     * @param customer new value of {@link #getCustomer}
     */
    public void setCustomer(CustomerDto customer) {

        this.customer = customer;
    }

    /**
     * @return date when the loan starts
     */
    public LocalDate getStartDate() {

        return startDate;
    }

    /**
     * @param startDate new value of {@link #getStartDate}.
     */
    public void setStartDate(LocalDate startDate) {

        this.startDate = startDate;
    }

    /**
     * @return date when the loan finishes
     */
    public LocalDate getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate new value of {@link #getFinishDate}.
     */
    public void setFinishDate(LocalDate finishDate) {

        this.finishDate = finishDate;
    }
}



