package com.ccsw.tutorial.miam.entities.author.service;

import com.ccsw.tutorial.miam.entities.author.AuthorRepository;
import com.ccsw.tutorial.miam.entities.author.model.Author;
import com.ccsw.tutorial.miam.entities.author.model.AuthorDto;
import com.ccsw.tutorial.miam.entities.author.model.AuthorSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Author get(Long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Author> findAll() {

        return (List<Author>) this.authorRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Author> findPage(AuthorSearchDto dto) {

        return this.authorRepository.findAll(dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, AuthorDto data) {

        Author author;

        if (id == null) {
            author = new Author();
        } else {
            author = this.authorRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(data, author, "id");

        this.authorRepository.save(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.authorRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }

        this.authorRepository.deleteById(id);
    }

}