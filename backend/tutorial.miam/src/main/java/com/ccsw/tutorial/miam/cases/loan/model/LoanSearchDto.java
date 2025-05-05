package com.ccsw.tutorial.miam.cases.loan.model;

import com.ccsw.tutorial.miam.common.pagination.PageableRequest;

import java.time.LocalDate;

public class LoanSearchDto {
    private PageableRequest pageable;
    private String titleGame;
    private String nameCustomer;
    private LocalDate requestDate;

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getTitleGame() {
        return titleGame;
    }

    public void setTitleGame(String titleGame) {
        this.titleGame = titleGame;
    }

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}


