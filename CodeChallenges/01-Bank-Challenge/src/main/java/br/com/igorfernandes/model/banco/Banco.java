package br.com.igorfernandes.model.banco;

import br.com.igorfernandes.model.conta.Conta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Banco {

    private String nome;
    private List<Conta> contas;

}
