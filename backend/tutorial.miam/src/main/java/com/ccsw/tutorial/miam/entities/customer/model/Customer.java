package com.ccsw.tutorial.miam.entities.customer.model;

import jakarta.persistence.*;

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
}
