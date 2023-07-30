package bookstore.handler.sale;

import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import bookstore.domain.sale.Book;

@RepositoryEventHandler(Book.class)
public class BookEventHandler {

}
