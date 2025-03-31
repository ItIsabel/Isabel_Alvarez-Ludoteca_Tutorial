package com.ccsw.tutorial.miam.entities.category;

import com.ccsw.tutorial.miam.entities.category.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}