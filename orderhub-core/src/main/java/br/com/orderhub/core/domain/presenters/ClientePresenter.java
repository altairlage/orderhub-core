package br.com.orderhub.core.domain.presenters;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.ClienteDTO;

import java.util.ArrayList;
import java.util.List;

public class ClientePresenter {

    public static ClienteDTO ToDTO(Cliente cliente){
        return new ClienteDTO(cliente.getId(),
                cliente.getNome(),
                cliente.getDataNascimento(),
                cliente.getEndereco(),
                cliente.getNumeroContato(),
                cliente.getEmail(),
                cliente.getInfoPagamento());
    }

    public static Cliente ToDomain(ClienteDTO clienteDTO){
        return new Cliente(
                clienteDTO.id(),
                clienteDTO.nome(),
                clienteDTO.dataNascimento(),
                clienteDTO.endereco(),
                clienteDTO.numeroContato(),
                clienteDTO.email(),
                clienteDTO.infoPagamento()
        );
    }

    public static List<ClienteDTO> ToListDTO(List<Cliente> listaCliente){
        List<ClienteDTO> listaDTO = new ArrayList<>();
        for (Cliente cliente : listaCliente) {
            listaDTO.add(ToDTO(cliente));
        }
        return listaDTO;
    }
}
