package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.dto.estoques.ItemEstoqueDTO;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstoqueControllerTest {

    private IEstoqueGateway estoqueGateway; // MUDANÇA: Agora mockamos o Gateway
    private EstoqueController controller;

    @BeforeEach
    void setUp() {
        // MUDANÇA: Mockamos apenas o gateway
        estoqueGateway = mock(IEstoqueGateway.class);
        // MUDANÇA: Instanciamos o controller com o gateway, como na nova regra
        controller = new EstoqueController(estoqueGateway);
    }

    @Test
    void deveReporEstoqueComSucesso() {
        String sku = "1";
        int quantidadeParaRepor = 5;
        Estoque estoqueInicial = new Estoque(sku, 10, LocalDateTime.now(), LocalDateTime.now());

        // Configuração do Mock: Quando buscar pelo sku "1", retorne o estoque inicial
        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoqueInicial));

        // Ação
        Estoque result = controller.repor(sku, quantidadeParaRepor);

        // Verificação
        assertNotNull(result);
        assertEquals(15, result.getQuantidadeDisponivel()); // O método reporEstoque na entidade deve ter funcionado
        // Verifica se o método 'salvar' do gateway foi chamado com o estoque atualizado
        verify(estoqueGateway).salvar(any(Estoque.class));
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        String sku = "2";
        int quantidadeParaBaixar = 3;
        Estoque estoqueInicial = new Estoque(sku, 8, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueGateway.buscarPorSku(sku)).thenReturn(Optional.of(estoqueInicial));

        Estoque result = controller.baixar(sku, quantidadeParaBaixar);

        assertNotNull(result);
        assertEquals(5, result.getQuantidadeDisponivel());
        verify(estoqueGateway).salvar(any(Estoque.class));
    }

    @Test
    void deveLancarExcecaoAoConsultarEstoqueInexistente() {
        when(estoqueGateway.buscarPorSku("999")).thenReturn(Optional.empty());

        assertThrows(EstoqueNaoEncontradoException.class, () -> controller.consultarPorSku("999"));
    }

    @Test
    void deveBaixarEstoquePorPedidoDeFormaAtomica() {
        // Cenário: Pedido com 2 produtos
        ProdutoDTO produtoDTO1 = new ProdutoDTO(10L, "Produto A", "Descrição", 100.0);
        ProdutoDTO produtoDTO2 = new ProdutoDTO(20L, "Produto B", "Descrição", 200.0);
        List<Map<Integer, ProdutoDTO>> listaItens = List.of(Map.of(2, produtoDTO1), Map.of(1, produtoDTO2));
        PedidoDTO pedidoDTO = new PedidoDTO(1L, null, null, listaItens, null);

        // Estoques existentes no banco
        Estoque estoqueProduto10 = new Estoque("10", 100, LocalDateTime.now(), LocalDateTime.now());
        Estoque estoqueProduto20 = new Estoque("20", 50, LocalDateTime.now(), LocalDateTime.now());

        // Configuração do Mock: quando o gateway buscar a lista de SKUs, retorne os estoques
        when(estoqueGateway.buscarPorSkus(List.of("10", "20"))).thenReturn(List.of(estoqueProduto10, estoqueProduto20));

        // Ação
        controller.baixarEstoquePorPedido(pedidoDTO);

        // Verificação
        // Garante que o método 'salvar' foi chamado 2 vezes (uma para cada item)
        verify(estoqueGateway, times(2)).salvar(any(Estoque.class));
        // Valida que a quantidade foi baixada corretamente em memória
        assertEquals(98, estoqueProduto10.getQuantidadeDisponivel());
        assertEquals(49, estoqueProduto20.getQuantidadeDisponivel());
    }
}