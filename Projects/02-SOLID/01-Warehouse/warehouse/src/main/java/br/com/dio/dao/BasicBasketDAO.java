package br.com.dio.dao;

import br.com.dio.BasicBasket;
import br.com.dio.model.StockInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BasicBasketDAO {

    private static final List<BasicBasket> stock = new ArrayList<>();

    /**
     * Add a batch of BasicBaskets to the stock.
     *
     * @param baskets A list of BasicBaskets.
     * @return The BasicBaskets added to the stock.
     */
    public List<BasicBasket> addBatch(final List<BasicBasket> baskets) {
        stock.addAll(baskets);
        return baskets;
    }

    /**
     * Removes a specified amount of BasicBaskets from the stock, by order of price (ascending).
     *
     * @param amount The number of BasicBaskets to remove from the stock.
     * @return A list of BasicBaskets, containing the removed ones.
     * @throws IllegalArgumentException If the amount to remove is greater than the total amount in stock.
     */
    public List<BasicBasket> remove(final int amount) {
        if (amount > stock.size()) {
            throw new IllegalArgumentException("The amount of basic baskets to remove can't be greater than the total" +
                    " amount in stock");
        }
        stock.sort(Comparator.comparing(BasicBasket::price));
        return stock.subList(0, amount);
    }

    /**
     * Returns information about the stock, including the total number of BasicBaskets in stock and the number of
     * BasicBaskets that are out of date.
     *
     * @return A {@link StockInfo} object containing the total number of BasicBaskets in stock and the number of
     * BasicBaskets that are out of date.
     */
    public StockInfo getStockInfo() {
        return new StockInfo(
                stock.size(),
                stock.stream()
                        .filter(b -> b.validUntil().isBefore(LocalDate.now())).count()
        );
    }

    /**
     * Removes BasicBaskets from the stock that are out of date.
     * <p>
     * BasicBaskets are considered out of date if their validation date is after the current date.
     *
     * @return A list of removed BasicBaskets.
     */
    public List<BasicBasket> removeOutOfDate() {
        var basketsOutOfDate = stock.stream()
                .filter(basket -> basket.validUntil().isBefore(LocalDate.now()))
                .toList();
        stock.removeAll(basketsOutOfDate);
        return basketsOutOfDate;
    }
}
