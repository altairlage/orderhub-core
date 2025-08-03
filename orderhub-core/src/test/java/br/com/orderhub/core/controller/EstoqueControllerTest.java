package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Estoque;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.domain.usecases.estoques.BaixarEstoqueMultiplo; // Importar
import br.com.orderhub.core.dto.estoques.ItemEstoqueDTO;
import br.com.orderhub.core.dto.pagamentos.PagamentoDTO;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.dto.produtos.ProdutoDTO;
import br.com.orderhub.core.exceptions.EstoqueNaoEncontradoException;
import br.com.orderhub.core.interfaces.IEstoqueGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor; // Importar
import org.mockito.Captor; // Importar
import org.mockito.InjectMocks; // Importar
import org.mockito.Mock; // Importar
import org.mockito.MockitoAnnotations; // Importar


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstoqueControllerTest {

    // Mocks para as dependências do Controller
    @Mock
    private IEstoqueGateway estoqueGateway;
    @Mock
    private BaixarEstoqueMultiplo baixarEstoqueMultiplo;

    // Injeta os mocks no controller
    @InjectMocks
    private EstoqueController controller;

    // Captor para verificar os argumentos passados ao mock
    @Captor
    private ArgumentCaptor<List<ItemEstoqueDTO>> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new EstoqueController(estoqueGateway);
    }


    @Test
    void deveBaixarEstoquePorPedidoDeFormaAtomica() {
        ProdutoDTO produtoDTO1 = new ProdutoDTO(10L, "Produto A", "Descrição", 100.0);
        ProdutoDTO produtoDTO2 = new ProdutoDTO(20L, "Produto B", "Descrição", 200.0);
        List<Map<String, Object>> listaItens = List.of(Map.of("quantidade", 2, "produto", produtoDTO1), Map.of("quantidade",1, "produto", produtoDTO2));
        PedidoDTO pedidoDTO = new PedidoDTO(1L, null, new PagamentoDTO(1L, "Adamastor", "email@email.com", 150.0,StatusPagamento.EM_ABERTO), listaItens, null);

        Estoque estoqueProduto10 = new Estoque(10L, 100, LocalDateTime.now(), LocalDateTime.now());
        Estoque estoqueProduto20 = new Estoque(20L, 50, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueGateway.buscarPorIds(List.of(10L, 20L))).thenReturn(List.of(estoqueProduto10, estoqueProduto20));

        controller.baixarEstoquePorPedido(pedidoDTO);

        verify(estoqueGateway, times(2)).salvar(any(Estoque.class));
        assertEquals(98, estoqueProduto10.getQuantidadeDisponivel());
        assertEquals(49, estoqueProduto20.getQuantidadeDisponivel());
    }

    @Test
    void deveReporEstoqueComSucesso() {
        Long id = 1L;
        int quantidadeParaRepor = 5;
        Estoque estoqueInicial = new Estoque(id, 10, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.of(estoqueInicial));
        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.of(estoqueInicial));


        Estoque result = controller.repor(id, quantidadeParaRepor);

        assertNotNull(result);
        assertEquals(15, result.getQuantidadeDisponivel());
        verify(estoqueGateway).salvar(any(Estoque.class));
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        Long id = 2L;
        int quantidadeParaBaixar = 3;
        Estoque estoqueInicial = new Estoque(id, 8, LocalDateTime.now(), LocalDateTime.now());

        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.of(estoqueInicial));

        Estoque result = controller.baixar(id, quantidadeParaBaixar);

        assertNotNull(result);
        assertEquals(5, result.getQuantidadeDisponivel());
        verify(estoqueGateway).salvar(any(Estoque.class));
    }

    @Test
    void deveLancarExcecaoAoConsultarEstoqueInexistente() {
        Long id = 999L;
        when(estoqueGateway.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(EstoqueNaoEncontradoException.class, () -> controller.consultarPorId(id));
    }
}