# Entendendo SOLID

Esta parte do curso foca nos princípios de SOLID em Java.

## 💭 O que é SOLID?

É um acrônimo criado por Michael Feathers para descrever o que é considerado como os 5 princípios fundamentais para um software que contêm uma arquitetura robusta e de fácil manutenção.

Abaixo fiz um resumo/transcrição da videoaula com o significado de cada letra.

### S - Single Responsability Principle (SRP)

O princípio da responsabilidade única dita as classes devem ser especialistas em uma única tarefa, evitando o acumulo de responsabilidades.

Exemplo: Uma única classe que faz todo o cálculo de notas fiscais com mais de 20 mil linhas, que ao mesmo tempo que verifica se as notas são válidas, importa, emite novas notas, e imprime elas. Essa classe "coração" poderia ser quebrada em classes e sub-classes menores que realizam essas tarefas de forma especializada.

### O - Open/Closed Principle

O Princípio de Aberto/Fechado diz que as partes (principais?) que compõem o nosso software devem ser fechadas para modificações, mas devem ser abertas para extensões. Abaixo darei um [Exemplo prático em java com sealed classes](https://www.baeldung.com/java-sealed-classes-interfaces)

Exemplo: Pensei nesse exemplo usando como base em uma solução que propus em uma antiga empresa que trabalhei. Imaginemos uma tela de importação de notas fiscais, que inicialmente integra apenas uma API para importação e validação de notas fiscais, pode dispor de várias novas APIs sendo integradas ao decorrer dos anos sem que seja necessário mudar uma única linha sequer do código feito para integrar a primeira API.

Para que isso seja possível, inicialmente o software pode ter uma implementação de uma **classe abstrata** de base `ImportadorDeNotaFiscal` com a keyword `sealed`, além de uma interface `sealed` chamada `Importador` dentro de um package `importacoes`, que define um contrato comum que toda nova integração com novas APIs deve respeitar, sendo assim possível extender o sistema sem afetar a primeira API integrada nessa tela.

### L - Liskov Substitution Principle (LSP)

O princípio da Substituição de [Liskov](https://en.wikipedia.org/wiki/Liskov_substitution_principle) diz que dado uma subclasse B, que extende A, caso uma instância da superclasse A seja substituida pela instância da classe B, o programa deve continuar funcional. Em java, isso se traduziria para o caso onde utilizamos de polimorfismo com interfaces ou classes abstratas para tipar um objeto da em sua forma mais genérica possível, ainda mantendo suas funcionalidades.

Exemplo:

```java

public interface Shape {
    double calculateArea();
}

public class Rectangle implements Shape {
    // atributos, construtor, etc

    @override
    public double calculateArea() {
        return side * side;
    }
}

public class Circle implements Shape {
    // atributos, construtor, etc

    @override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }
}

public static void main(String[] args) {
    // Deve continuar funcionando como Rectangle ou Circle, que são subtipos de Shape
    Shape rectangle = new Rectangle(2);
    Shape circle = new Circle(2);

    System.out.println("A área do retângulo é : " + rectangle.calculateArea());
    System.out.println("A área do círculo é : " + circle.calculateArea());
}
```

### I - Interface Segregation Principle

O Princípio da Segregação de Interface diz que interfaces com muitas funcionalidades não coesas devem ser divididas em interfaces menores. Tem uma relação direta com o [Princípio de Responsabilidade Única](#s---single-responsability-principle-srp), pois também defende a quebra de código não-coeso.

Exemplo:

```java
// Interface segregada para cálculo de área
public interface AreaCalculavel {
    double calcularArea();
}

// Interface segregada para cálculo de perímetro
public interface PerimetroCalculavel {
    double calcularPerimetro();
}

// Classe que implementa apenas o que faz sentido
public class Circulo implements AreaCalculavel, PerimetroCalculavel {
    private double raio;

    public Circulo(double raio) {
        this.raio = raio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * raio;
    }
}

// Outra classe que pode implementar apenas uma das interfaces, se necessário
public class Quadrado implements AreaCalculavel, PerimetroCalculavel {
    private double lado;

    public Quadrado(double lado) {
        this.lado = lado;
    }

    @Override
    public double calcularArea() {
        return lado * lado;
    }

    @Override
    public double calcularPerimetro() {
        return 4 * lado;
    }
}
```

Dessa forma, cada classe implementa apenas as interfaces relevantes, evitando métodos desnecessários.

### Dependency Inversion Principle - DIP

O princípio da inversão de dependência diz que os módulos do sistema devem depender de interfaces
e não de implementações específicas

Exemplo:

```java
// Interface que define o contrato para envio de notificações
public interface Notificador {
    void notificar(String mensagem);
}

// Implementação concreta para envio de notificações por email
public class EmailNotificador implements Notificador {
    @Override
    public void notificar(String mensagem) {
        // Lógica para enviar email
        System.out.println("Enviando email: " + mensagem);
    }
}

// Implementação concreta para envio de notificações por SMS
public class SmsNotificador implements Notificador {
    @Override
    public void notificar(String mensagem) {
        // Lógica para enviar SMS
        System.out.println("Enviando SMS: " + mensagem);
    }
}

// Classe que depende da abstração Notificador, e não das implementações concretas
public class AlertaService {
    private Notificador notificador;

    // Injeção de dependência via construtor
    public AlertaService(Notificador notificador) {
        this.notificador = notificador;
    }

    public void emitirAlerta(String mensagem) {
        notificador.notificar(mensagem);
    }
}

// Exemplo de uso
public class Main {
    public static void main(String[] args) {
        // Podemos facilmente trocar o tipo de notificador sem alterar a classe AlertaService
        Notificador emailNotificador = new EmailNotificador();
        AlertaService alertaService = new AlertaService(emailNotificador);
        alertaService.emitirAlerta("Alerta importante!");

        Notificador smsNotificador = new SmsNotificador();
        alertaService = new AlertaService(smsNotificador);
        alertaService.emitirAlerta("Alerta importante via SMS!");
    }
}
```
