package br.com.dio.dao;

import java.math.BigDecimal;

public class MoneyDAO {
    private BigDecimal money = BigDecimal.ZERO;

    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Adds a specified amount of money to the current total.
     *
     * @param money the amount of money to add
     * @return the updated total amount of money
     */
    public BigDecimal add(final BigDecimal money) {
        this.money = this.money.add(money);
        return this.money;
    }
}
