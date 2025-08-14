package samples;

public class Variaveis {

    private static final double INTEREST_RATE_THRESHOLD = 2.0;

    public static void main(String[] args) {
        // ❌ Nome de variável não descritiva. Nome do que?
        String name = "João";

        // ✅ Nome de variável descritiva, sabemos que esse é o nome do usuário.
        String userName = "João";

        // ❌ Nome de variável não descritiva ou muito subjetiva. Taxa do que?
        double tax = 0.29;

        // ✅ Nome de variável descritiva, sabemos que essa é a taxa de juros.
        double interestRate = 0.29;

        // ❌ Números mágicos, não sabemos o que 2 representa.
        if (interestRate < 2) {
            System.out.println("Taxa de juros baixa");
        }

        // ✅ Nomes descritivos, constantes bem definivas (usando letras maiscúlas)
        // e sem números mágicos, sabemos que 2 representa o limite de taxa de juros.
        if (interestRate < INTEREST_RATE_THRESHOLD) {
            System.out.println("Taxa de juros baixa, abaixo do limite de " + INTEREST_RATE_THRESHOLD);
        }

        // ❌ Abreviação não conhecida, o que é IR?
        double myIR = 1.5;

        // ✅ Nome descritivo, sabemos que é a taxa de imposto de renda.
        double incomeTaxRate = 1.5;

    }
}