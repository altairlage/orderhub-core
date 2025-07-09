package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.domain.presenters.PedidoPresenter;
import br.com.orderhub.core.domain.usecases.pedidos.*;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;
import br.com.orderhub.core.interfaces.IProdutoGateway;

import java.util.List;

public class PedidoController {
    private final IPedidoGateway pedidoGateway;
    private final IClienteGateway clienteGateway;
    private final IProdutoGateway produtoGateway;
    private final BuscarPedidoPorId buscarPedidoPorId;
    private final BuscarPedidosPorIdCliente buscarPedidosPorIdCliente;
    private final CriarPedido criarPedido;
    private final EditarPedidoStatus editarPedidoStatus;
    private final EditarPedido editarPedidoTodo;
    private final ListarTodosPedidos listarTodosPedidos;

    public PedidoController(IPedidoGateway pedidoGateway, IClienteGateway clienteGateway, IProdutoGateway produtoGateway){
        this.pedidoGateway = pedidoGateway;
        this.clienteGateway = clienteGateway;
        this.produtoGateway = produtoGateway;
        this.buscarPedidoPorId = new BuscarPedidoPorId(this.pedidoGateway);
        this.buscarPedidosPorIdCliente = new BuscarPedidosPorIdCliente(this.pedidoGateway,this.clienteGateway);
        this.criarPedido = new CriarPedido(this.pedidoGateway, this.clienteGateway, this.produtoGateway);
        this.editarPedidoStatus = new EditarPedidoStatus(this.pedidoGateway);
        this.editarPedidoTodo = new EditarPedido(this.pedidoGateway);
        this.listarTodosPedidos = new ListarTodosPedidos(this.pedidoGateway);
    }

    public PedidoDTO buscarPedidoPorId(Long id) {
        Pedido pedido = this.buscarPedidoPorId.run(id);
        return PedidoPresenter.ToDTO(pedido);
    }

    public List<PedidoDTO> buscarPedidosPorIdCliente(Long idCliente) {
        List<Pedido> pedidos = this.buscarPedidosPorIdCliente.run(idCliente);
        return PedidoPresenter.ToListDTO(pedidos);
    }

    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {
        return PedidoPresenter.ToDTO(this.criarPedido.run(pedidoDTO));
    }

    public PedidoDTO editarPedidoStatus(Long id, StatusPedido statusPedido) {
        return PedidoPresenter.ToDTO(this.editarPedidoStatus.run(id, statusPedido));
    }

    public PedidoDTO editarPedidoTodo(PedidoDTO pedidoAtualizado){
        return PedidoPresenter.ToDTO(this.editarPedidoTodo.run(pedidoAtualizado));
    }

    public List<PedidoDTO> listarTodosPedidos(){
        return PedidoPresenter.ToListDTO(listarTodosPedidos.run());
    }
}
