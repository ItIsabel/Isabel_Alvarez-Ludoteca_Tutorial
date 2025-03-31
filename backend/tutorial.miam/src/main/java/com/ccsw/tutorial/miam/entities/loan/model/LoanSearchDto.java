package com.ccsw.tutorial.miam.entities.loan.model;

import com.ccsw.tutorial.miam.common.pagination.PageableRequest;

public class LoanSearchDto {

    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
