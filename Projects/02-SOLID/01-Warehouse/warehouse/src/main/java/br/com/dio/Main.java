package br.com.dio;

import br.com.dio.dao.BasicBasketDAO;
import br.com.dio.dao.MoneyDAO;
import br.com.dio.service.BasicBasketService;
import br.com.dio.service.MoneyService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    private static final MoneyService moneyService = new MoneyService(new MoneyDAO());
    private static final BasicBasketService basicBasketService =
            new BasicBasketService(new BasicBasketDAO(), moneyService);

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bem-vindo(a) ao sistema de armazém");
        System.out.println("Selecione a opção desejada");
        var option = -1;
        while (true) {
            System.out.println("1 - Verificar estoque de cesta básica");
            System.out.println("2 - Verificar caixa");
            System.out.println("3 - Receber Cestas");
            System.out.println("4 - Vender Cestas");
            System.out.println("5 - Remover itens vencidos");
            System.out.println("6 - Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> checkStock();
                case 2 -> checkMoney();
                case 3 -> receiveItems();
                case 4 -> sellItems();
                case 5 -> removeItemsOutOfDate();
                case 6 -> System.exit(0);
                default -> System.out.println("Opção inválida, escolha uma opção oferecida pelo menu.");
            }
        }
    }

    private static void sellItems() {
        System.out.println("Quantas cestas serão vendidas");
        var amount = scanner.nextInt();
        final BigDecimal totalPriceSold = basicBasketService.sell(amount);
        System.out.printf("O valor da venda é de %s \n", totalPriceSold);
    }

    private static void checkStock() {
        var stockInfo = basicBasketService.getStockInfo();
        System.out.printf("Existem %s cestas em estoque, das quais %s estão fora do prazo de validade \n",
                stockInfo.total(),
                stockInfo.outOfDate());
    }

    private static void checkMoney() {
        System.out.printf("O caixa no momento é de %s\n", moneyService.getMoney());
    }

    private static void removeItemsOutOfDate() {
        var outOfDate = basicBasketService.removeOutOfDate();
        var lostEarnings = outOfDate.stream().map(BasicBasket::price).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("Foram descartadas do estoque %s cestas vencidas, o prejuízo foi de %s \n",
                outOfDate.size(),
                lostEarnings);
    }

    private static void receiveItems() {
        System.out.println("Informe o valor da entrega");
        var price = scanner.nextBigDecimal();
        System.out.println("Informe a quantidade de cestas da entrega");
        var amount = scanner.nextLong();
        System.out.println("Informe a data de vencimento");
        var validUntilStringDate = scanner.next();
        var validUntil = getValidUntilDateFromStringDate(validUntilStringDate);
        var box = new Box(amount, validUntil, price);
        var baskets = basicBasketService.receive(box);
        System.out.printf("Foram adicionadas %s cestas ao estoque\n", baskets.size());
    }

    private static LocalDate getValidUntilDateFromStringDate(final String date) {
        var splitDate = Stream.of(date.split("/")).mapToInt(Integer::parseInt).toArray();
        return LocalDate.of(splitDate[2], splitDate[1], splitDate[0]);
    }

}
