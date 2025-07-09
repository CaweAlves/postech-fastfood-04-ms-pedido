package org.fiap.fastfoodpedidos.domain.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @NotEmpty(message = "Nome não pode estar vazio")
    @Size(max = 100)
    private String nome;
    @Positive
    @NotNull(message = "O preço deve ser informado")
    private Double preco;
    @Positive
    @NotNull(message = "A quantidade deve ser informada")
    private Integer quantidade;

}
