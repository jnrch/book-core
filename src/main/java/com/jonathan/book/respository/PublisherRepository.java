package com.jonathan.book.respository;

import com.jonathan.book.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Page<Publisher> findByBookId(Long bookId, Pageable pageable);
}
