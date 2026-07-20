package com.hitss.product.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyUsingOf() {
        Money money = Money.of(1500.0);

        assertEquals(BigDecimal.valueOf(1500.0), money.getAmount());
    }

    @Test
    void shouldCreateMoneyUsingFrom() {
        BigDecimal amount = BigDecimal.valueOf(250.50);

        Money money = Money.from(amount);

        assertEquals(amount, money.getAmount());
    }

    @Test
    void shouldAddTwoMoneyValues() {
        Money first = Money.of(100);
        Money second = Money.of(50);

        Money result = first.add(second);

        assertEquals(BigDecimal.valueOf(150.0), result.getAmount());
    }

    @Test
    void shouldSubtractTwoMoneyValues() {
        Money first = Money.of(100);
        Money second = Money.of(30);

        Money result = first.subtract(second);

        assertEquals(BigDecimal.valueOf(70.0), result.getAmount());
    }

    @Test
    void shouldReturnTrueWhenMoneyIsNegative() {
        Money money = Money.of(-10);

        assertTrue(money.isNegative());
    }

    @Test
    void shouldReturnFalseWhenMoneyIsZeroOrPositive() {
        assertFalse(Money.of(0).isNegative());
        assertFalse(Money.of(100).isNegative());
    }
}