package br.com.dio.service;

import br.com.dio.dao.MoneyDAO;

import java.math.BigDecimal;

public class MoneyService {
    private final MoneyDAO dao;

    public MoneyService(final MoneyDAO dao) {
        this.dao = dao;
    }

    /**
     * Adds a specified amount of money to the current total.
     *
     * @param money the amount of money to add
     * @return the updated total amount of money
     */
    public BigDecimal add(final BigDecimal money) {
        return dao.add(money);
    }

    /**
     * Returns the current total amount of money.
     *
     * @return the total amount of money
     */
    public BigDecimal getMoney() {
        return dao.getMoney();
    }

    /**
     * Subtracts a specified amount of money from the current total.
     *
     * @param money the amount of money to subtract
     * @return the updated total amount of money
     */
    public BigDecimal remove(final BigDecimal money) {
        return dao.add(money.multiply(new BigDecimal(-1)));
    }
}
