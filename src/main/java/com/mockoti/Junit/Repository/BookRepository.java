package com.mockoti.Junit.Repository;

import com.mockoti.Junit.Enitity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
