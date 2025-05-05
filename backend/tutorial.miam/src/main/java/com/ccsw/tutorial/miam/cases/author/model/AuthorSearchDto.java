package com.ccsw.tutorial.miam.cases.author.model;

import com.ccsw.tutorial.miam.common.pagination.PageableRequest;

/**
 * @author ccsw
 */
public class AuthorSearchDto {

    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}