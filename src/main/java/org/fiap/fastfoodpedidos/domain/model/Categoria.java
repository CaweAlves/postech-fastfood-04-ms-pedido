package org.fiap.fastfoodpedidos.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    private Integer id;
    @NotEmpty(message = "Nome não pode estar vazio")
    @Size(max = 100)
    private String nome;
    @NotEmpty(message = "A descrição não pode estar vazia")
    @Size(max = 100)
    private String descricao;
}
