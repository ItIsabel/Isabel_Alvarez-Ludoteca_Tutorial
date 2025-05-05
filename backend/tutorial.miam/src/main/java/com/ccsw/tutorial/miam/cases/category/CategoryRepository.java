package com.ccsw.tutorial.miam.cases.category;

import com.ccsw.tutorial.miam.cases.category.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}