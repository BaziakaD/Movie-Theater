package com.baziaka.service.crud;

import com.baziaka.domain.discount.DiscountType;
import com.baziaka.domain.User;
import com.baziaka.domain.discount.Discount;
import com.baziaka.repository.DiscountRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService extends CRUDService<Discount> {

    private DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        super(discountRepository);
        this.discountRepository = discountRepository;
    }

    public Discount getDiscount(User user) {

        if (isUserNextTicketAliquotTen(user)) {
            return findByType(DiscountType.TENTH_TICKET);
        } else if (isUserBirthdayToday(user)) {
            return findByType(DiscountType.BIRTHDAY);
        } else {
            return findByType(DiscountType.NONE);
        }
    }

    public Discount findByType(DiscountType discountType) {
        return discountRepository.findByDiscountType(discountType);
    }

    private boolean isUserNextTicketAliquotTen(User user) {
        return (user.getTickets().size() + 1) % 10 == 0;
    }

    private boolean isUserBirthdayToday(User user) {
        DateTime today = new DateTime();

        if (today.getMonthOfYear() == user.getBirthday().getMonthOfYear()) {
            return today.getDayOfMonth() == user.getBirthday().getDayOfMonth();
        }

        return false;
    }
}
