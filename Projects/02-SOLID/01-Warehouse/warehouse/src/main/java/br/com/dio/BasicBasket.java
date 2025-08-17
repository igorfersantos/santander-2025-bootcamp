package br.com.dio;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A basic basket containing its valid date and price.
 *
 * @param validUntil The date this basket will hold consumable.
 * @param price Its price.
 * @see Box
 */
public record BasicBasket(LocalDate validUntil, BigDecimal price) {
}
