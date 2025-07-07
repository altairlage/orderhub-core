package br.com.orderhub.core.controller;

import br.com.orderhub.core.domain.entities.Pedido;
import br.com.orderhub.core.domain.enums.StatusPedido;
import br.com.orderhub.core.domain.presenters.PedidoPresenter;
import br.com.orderhub.core.domain.usecases.pedidos.*;
import br.com.orderhub.core.dto.pedidos.PedidoDTO;
import br.com.orderhub.core.interfaces.IClienteGateway;
import br.com.orderhub.core.interfaces.IPedidoGateway;

import java.util.List;

public class PedidoController {
    private final IPedidoGateway pedidoGateway;
    private final IClienteGateway clienteGateway;
    private final BuscarPedidoPorId buscarPedidoPorId;
    private final BuscarPedidosPorIdCliente buscarPedidosPorIdCliente;
    private final CriarPedido criarPedido;
    private final EditarPedidoStatus editarPedidoStatus;
    private final EditarPedidoTodo editarPedidoTodo;

    public PedidoController(IPedidoGateway pedidoGateway, IClienteGateway clienteGateway, BuscarPedidoPorId buscarPedidoPorId, BuscarPedidosPorIdCliente buscarPedidosPorIdCliente, CriarPedido criarPedido, EditarPedidoStatus editarPedidoStatus, EditarPedidoTodo editarPedidoTodo) {
        this.pedidoGateway = pedidoGateway;
        this.clienteGateway = clienteGateway;
        this.buscarPedidoPorId = new BuscarPedidoPorId(this.pedidoGateway);
        this.buscarPedidosPorIdCliente = new BuscarPedidosPorIdCliente(this.pedidoGateway,this.clienteGateway);
        this.criarPedido = new CriarPedido(this.pedidoGateway);
        this.editarPedidoStatus = new EditarPedidoStatus(this.pedidoGateway);
        this.editarPedidoTodo = new EditarPedidoTodo(this.pedidoGateway);
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
}
