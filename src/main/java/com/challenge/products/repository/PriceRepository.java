package com.challenge.products.repository;

import com.challenge.products.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("""
        select p from Price p
        where p.product.id = :productId
          and (
            (p.initDate <= :date and (p.endDate is null or p.endDate >= :date))
          )
        """)
    Optional<Price> findPriceByProductAndDate(@Param("productId") Long productId,
                                              @Param("date") LocalDate date);

    @Query("""
        select p from Price p
        where p.product.id = :productId
        order by p.initDate
        """)
    List<Price> findAllByProduct(@Param("productId") Long productId);
}
