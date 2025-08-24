package br.com.igorfernandes.dto;

import br.com.igorfernandes.persistence.entity.BoardColumnKindEnum;

public record BoardColumnDTO(Long id,
                             String name,
                             BoardColumnKindEnum kind,
                             int cardsAmount) {
}
