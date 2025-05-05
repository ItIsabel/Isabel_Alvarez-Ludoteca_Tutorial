package com.ccsw.tutorial.miam.cases.customer.model;

/**
 * @author miam
 */
public class CustomerDto {

    private long id;

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
