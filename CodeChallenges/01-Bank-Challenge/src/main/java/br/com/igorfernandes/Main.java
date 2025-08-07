package br.com.igorfernandes;

import br.com.igorfernandes.model.banco.Banco;
import br.com.igorfernandes.model.cliente.Cliente;
import br.com.igorfernandes.model.conta.Conta;
import br.com.igorfernandes.model.conta.ContaCorrente;
import br.com.igorfernandes.model.conta.ContaPoupanca;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Cliente igor = new Cliente();
        igor.setNome("Igor");

        Conta cc = new ContaCorrente(igor);
        Conta poupanca = new ContaPoupanca(igor);

        Banco banco = new Banco("Banco do Igor", List.of(cc, poupanca));

        cc.depositar(100);
        cc.transferir(100, poupanca);

        cc.imprimirExtrato();
        poupanca.imprimirExtrato();
    }

}
