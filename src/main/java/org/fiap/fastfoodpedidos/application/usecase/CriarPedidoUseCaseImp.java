package org.fiap.fastfoodpedidos.application.usecase;

import lombok.AllArgsConstructor;
import org.fiap.fastfoodpedidos.application.port.driven.BuscarProdutoPeloIdUseCase;
import org.fiap.fastfoodpedidos.application.port.driven.SalvarPedido;
import org.fiap.fastfoodpedidos.application.port.driver.CriarPedidoUseCase;
import org.fiap.fastfoodpedidos.domain.enumeration.PedidoStatus;
import org.fiap.fastfoodpedidos.domain.model.Pedido;
import org.fiap.fastfoodpedidos.domain.model.PedidoProduto;
import org.fiap.fastfoodpedidos.domain.model.Produto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CriarPedidoUseCaseImp implements CriarPedidoUseCase {

    private final SalvarPedido salvarPedido;
    private final BuscarProdutoPeloIdUseCase buscarProdutoPeloIdUseCase;

    @Override
    public Pedido execute(Pedido pedido) {
        pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
        HashSet<PedidoProduto> produtoQuantidadeInfo = new HashSet<>(pedido.getPedidoProdutos());
        pedido.setPedidoProdutos(null);
        Set<PedidoProduto> pedidoProdutos = carregarProdutos(produtoQuantidadeInfo, pedido);

        pedido.setPedidoProdutos(pedidoProdutos);

        BigDecimal totalPedido = calcularTotal(pedidoProdutos);

        pedido.setTotal(totalPedido);
        return salvarPedido.execute(pedido);
    }

    private Set<PedidoProduto> carregarProdutos(HashSet<PedidoProduto> produtoQuantidadeInfo, Pedido pedido) {

        return produtoQuantidadeInfo.stream()
                .map(pedidoProduto -> {
                    Produto produtoEncontrado = buscarProdutoPeloIdUseCase.execute(pedidoProduto.getProduto().getId());
                    return PedidoProduto.builder()
                            .pedido(pedido)
                            .produto(produtoEncontrado)
                            .valorUnitarioProdutoMomentoVenda(produtoEncontrado.getPreco())
                            .quantidade(pedidoProduto.getQuantidade())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private BigDecimal calcularTotal(Set<PedidoProduto> pedidosProdutos) {
        return pedidosProdutos.stream().map(
                pedidoProduto -> pedidoProduto.getProduto().getPreco()
                        .multiply(BigDecimal.valueOf(pedidoProduto.getQuantidade()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
