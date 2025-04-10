package com.ccsw.tutorial.miam.author;

import com.ccsw.tutorial.miam.entities.author.AuthorRepository;
import com.ccsw.tutorial.miam.entities.author.model.Author;
import com.ccsw.tutorial.miam.entities.author.service.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorTest {

    public static final Long EXISTS_AUTHOR_ID = 1L;
    public static final Long NOT_EXISTS_AUTHOR_ID = 0L;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void getExistsAuthorIdShouldReturnAuthor() {

        Author author = mock(Author.class);
        when(author.getId()).thenReturn(EXISTS_AUTHOR_ID);
        when(authorRepository.findById(EXISTS_AUTHOR_ID)).thenReturn(Optional.of(author));

        Author authorResponse = authorService.get(EXISTS_AUTHOR_ID);

        assertNotNull(authorResponse);

        assertEquals(EXISTS_AUTHOR_ID, authorResponse.getId());
    }

    @Test
    public void getNotExistsAuthorIdShouldReturnNull() {
        Author author = authorService.get(NOT_EXISTS_AUTHOR_ID);

        assertNull(author);
    }

}