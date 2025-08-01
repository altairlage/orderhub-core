package br.com.orderhub.core.domain.usecases.pedidos;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.domain.entities.Pagamento;
import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.entities.Produto;
import br.com.orderhub.core.domain.enums.StatusPagamento;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.dto.pedidos.CriarPedidoDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;
import br.com.orderhub.core.interfaces.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CriarPedidoTest {
    private CriarPedido criarPedido;
    private IPedidoGateway pedidoGateway;
    private IClienteGateway clienteGateway;
    private IProdutoGateway produtoGateway;

    // Variáveis muito utilizadas
    private Produto produtoCriado;
    private Map<String, Object> mapCriarProduto1 = new HashMap<>();
    private Map<String, Object> mapCriarProduto2 = new HashMap<>();
    private Map<String, Object> mapProduto1 = new HashMap<>();
    private Map<String, Object> mapProduto2 = new HashMap<>();
    private Cliente clienteCriado;
    private Pedido pedidoCriado;
    private CriarPedidoDTO criarPedidoDTO;
    private Pagamento pagamentoCriado;

    @BeforeEach
    public void setUp() {
        pedidoGateway = mock(IPedidoGateway.class);
        clienteGateway = mock(IClienteGateway.class);
        produtoGateway = mock(IProdutoGateway.class);
        criarPedido = new CriarPedido(pedidoGateway, clienteGateway, produtoGateway);

        produtoCriado = new Produto("Arroz", "Branco", 20.0);

        mapCriarProduto1.put("quantidade", 2);
        mapCriarProduto1.put("idProduto", 1L);

        mapCriarProduto2.put("quantidade", 1);
        mapCriarProduto2.put("idProduto", 2L);

        clienteCriado = new Cliente(
                1L,
                "Adamastor",
                "123.456.789-10",
                "25/01/1900",
                "R. Teste",
                "(11) 91234-5678",
                "email@email.com",
                "infoPgto"
        );

        pagamentoCriado = new Pagamento(1L, StatusPagamento.EM_ABERTO);

        criarPedidoDTO = new CriarPedidoDTO(1L, Arrays.asList(mapCriarProduto1, mapCriarProduto2), StatusPedido.ABERTO);

        pedidoCriado = new Pedido(1L, clienteCriado, pagamentoCriado, Arrays.asList(mapProduto1, mapProduto2), StatusPedido.ABERTO);
    }

    @Test
    public void deveCriarPedidoComSucesso(){
        when(clienteGateway.buscarPorId(any(Long.class))).thenReturn(clienteCriado);
        when(produtoGateway.buscarPorId(any(Long.class))).thenReturn(produtoCriado);
        when(pedidoGateway.criar(any(Pedido.class))).thenReturn(pedidoCriado);

        Pedido resultado = criarPedido.run(criarPedidoDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getCliente().getId());
    }

    @Test
    public void deveLancarExcecaoQuandoClienteNaoExiste(){
        when(clienteGateway.buscarPorCpf(any(String.class))).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () -> criarPedido.run(criarPedidoDTO));
    }
}
