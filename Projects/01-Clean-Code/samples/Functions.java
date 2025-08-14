package samples;

public class Functions {
    // Cenário hipotético:
    // Colher os dados do usuário em um sistema de envio
    // salvar em um banco de dados
    // depois gerar um pedido com código de rastreio
    public static void main(String[] args) {
        Functions functions = new Functions();
        // Chama a função principal que orquestra as outras funções
        functions.processOrder();
    }

    // ❌ Problemas do código abaixo:
    // 1 - Nomes de variáveis não são claros
    // 2 - Excesso de parâmetros
    // 3 - Nome da função é subjetivo
    // 4 - Função faz mais do que deveria
    public String sending(String name, String zip, String data, String country) {
        // Colhendo os dados do usuário
        String userName = "João";
        String userEmail = "joao@joao.com";
        String userAddress = "Rua A, 123, Bairro B, Cidade C";
        // Salvando os dados do usuário no banco de dados
        // ....
        // Retorna código do pedido
        return "123456";
    }

    // ✅ Funções da maneira correta:
    // Separar responsabilidades
    // 1 - Coletar dados do usuário
    public String getDataFromUser() {
        // Coletando os dados do usuário
        String userName = "João";
        String userEmail = "joao@joao.com";
        String userAddress = "Rua A, 123, Bairro B, Cidade C";
        return userName + ", " + userEmail + ", " + "\"" + userAddress + "\"";
    }

    // 2 - Salvar dados do usuário no banco de dados
    public void saveUserDataToDatabase(String userData) {
        // Salvando os dados do usuário no banco de dados
        // ...
        System.out.println("Dados do usuário salvos: " + userData);
    }

    // 3 - Gerar pedido com código de rastreio
    public String createTrackingOrder(String userData) {
        // Gerando o pedido com código de rastreio
        // ...
        return "123456"; // Retorna código do pedido
    }

    // 4 - Função principal que orquestra as outras funções
    public void processOrder() {
        String userData = getDataFromUser();
        saveUserDataToDatabase(userData);
        String trackingCode = createTrackingOrder(userData);
        System.out.println("Pedido criado com código de rastreio: " + trackingCode);
    }

}
