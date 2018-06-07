package com.baziaka.repository;

import com.baziaka.domain.discount.DiscountType;
import com.baziaka.domain.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Discount findByDiscountType(DiscountType type);
}
