package org.fiap.fastfoodpedidos.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private Integer id;
    @NotEmpty(message = "Nome não pode estar vazio")
    @Size(max = 100)
    private String nome;
    @NotEmpty(message = "Descrição não pode estar vazia")
    @Size(max = 100)
    private String descricao;
    @Positive
    @NotNull(message = "O preço deve ser informado")
    private BigDecimal preco;
    @Valid
    private Categoria categoria;
}
