package com.ccsw.tutorial.miam.entities.loan.model;

import com.ccsw.tutorial.miam.entities.customer.model.Customer;
import com.ccsw.tutorial.miam.entities.game.model.Game;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author miam
 */
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
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
     * @return {@link Game}
     */
    public Game getGame() {

        return game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(Game game) {

        this.game = game;
    }

    /**
     * @return {@link Customer}
     */
    public Customer getCustomer() {

        return customer;
    }

    /**
     * @param customer new value of {@link #getCustomer}
     */
    public void setCustomer(Customer customer) {

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

