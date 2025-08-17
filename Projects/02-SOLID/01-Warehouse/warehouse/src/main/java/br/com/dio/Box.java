package br.com.dio;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.RoundingMode.CEILING;

/**
 * Represents a box containing a number of {@link BasicBasket}s with its
 * total price.
 *
 * @param amount How many {@link BasicBasket} this box will hold.
 * @param validUntil The date which this box will hold consumable.
 * @param price The total price of this box ({@code BasicBasket.price} * {@code amount}).
 */
public record Box(long amount, LocalDate validUntil, BigDecimal price) {


    /**
     * Calculates the price per unit of a box.
     *
     * @return The price per unit of the box, rounded up to the nearest whole number.
     */
    public BigDecimal pricePerUnit(){
        return price.divide(new BigDecimal(amount()), CEILING);
    }
}
