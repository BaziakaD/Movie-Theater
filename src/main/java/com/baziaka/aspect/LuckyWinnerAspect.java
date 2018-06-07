package com.baziaka.aspect;

import com.baziaka.domain.discount.Discount;
import com.baziaka.domain.discount.DiscountType;
import com.baziaka.service.crud.DiscountService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Aspect
@Component
public class LuckyWinnerAspect {

    private DiscountService discountService;
    private Random random;

    @Autowired
    public LuckyWinnerAspect(DiscountService discountService) {
        this.discountService = discountService;
        this.random = new Random();
    }

    @Pointcut("execution( * com.baziaka.service.crud.DiscountService.getDiscount(*) )")
    public void getDiscountMethodPointcut() {}

    @Around(value = "getDiscountMethodPointcut()")
    public Object getDiscountMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        Discount originalDiscount = (Discount) joinPoint.proceed();

        if (isLuckyWinner()) {
            return discountService.findByType(DiscountType.LUCKY_WINNER);
        }

        return originalDiscount;
    }

    private boolean isLuckyWinner() {
        return random.nextInt(100) == 77;
    }
}
