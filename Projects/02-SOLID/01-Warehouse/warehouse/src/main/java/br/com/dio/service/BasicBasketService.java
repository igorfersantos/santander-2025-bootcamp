package br.com.dio.service;

import br.com.dio.BasicBasket;
import br.com.dio.Box;
import br.com.dio.dao.BasicBasketDAO;
import br.com.dio.model.StockInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class BasicBasketService {

    private final BasicBasketDAO dao;
    private final MoneyService moneyService;

    public BasicBasketService(final BasicBasketDAO dao, final MoneyService moneyService) {
        this.dao = dao;
        this.moneyService = moneyService;
    }

    /**
     * Receives a Box and generates a list of BasicBaskets, each with a price that includes
     * a 20% discount. The number of BasicBaskets is determined by the amount of the Box, and
     * the date of each {@link BasicBasket} is set to the validation date of the Box.
     *
     * @param box The Box containing the details of the BasicBaskets to be received.
     * @return A list of BasicBaskets with each BasicBasket having a price that includes a 20%
     * discount and a date that matches the Box's validation date.
     * @see BasicBasket
     * @see Box
     */
    public List<BasicBasket> receive(final Box box) {
        var pricePerUnit = box.pricePerUnit();
        var totalPrice = pricePerUnit.add(pricePerUnit.multiply(new BigDecimal("0.20")));
        var baskets = Stream.generate(() -> new BasicBasket(box.validUntil(), totalPrice))
                .limit(box.amount())
                .toList();
        return dao.addBatch(baskets);
    }

    /**
     * Removes a specified amount of BasicBaskets from the stock and adds the total price of those
     * BasicBaskets to the current total.
     *
     * @param amount The number of BasicBaskets to sell.
     * @return The total price of the BasicBaskets that were sold.
     * @see BasicBasket
     * @see MoneyService
     */
    public BigDecimal sell(final int amount) {
        // Retrieve the specified number of BasicBaskets from the stock
        var basketsToSell = dao.remove(amount);

        // Calculate the total price of the BasicBaskets to be sold
        var totalPrice = basketsToSell.stream()
                .map(BasicBasket::price) // Map each BasicBasket to its price
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum the prices

        // Add the total price to the current total
        moneyService.add(totalPrice);

        // Remove the sold baskets from stock
        basketsToSell.clear();

        return totalPrice;
    }

    /**
     * Returns information about the stock, including the total number of BasicBaskets in stock and the number of
     * BasicBaskets that are out of date.
     *
     * @return A {@link StockInfo} object containing the total number of BasicBaskets in stock and the number of
     * BasicBaskets that are out of date.
     * @see BasicBasketDAO#getStockInfo()
     */
    public StockInfo getStockInfo() {
        return dao.getStockInfo();
    }

    /**
     * Removes BasicBaskets from the stock that are out of date, as determined by their validation date being
     * after the current date.
     *
     * @return A list of BasicBaskets that were removed from the stock.
     * @see BasicBasketDAO#removeOutOfDate()
     */
    public List<BasicBasket> removeOutOfDate() {
        final List<BasicBasket> basketsOutOfDate = dao.removeOutOfDate();
        var total = basketsOutOfDate.stream().map(BasicBasket::price).reduce(BigDecimal.ZERO,BigDecimal::add);
        moneyService.remove(total);
        return basketsOutOfDate;
    }
}
