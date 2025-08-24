package br.com.igorfernandes.dto;

import br.com.igorfernandes.persistence.entity.BoardColumnKindEnum;

public record BoardColumnInfoDTO(Long id, int order, BoardColumnKindEnum kind) {
}
