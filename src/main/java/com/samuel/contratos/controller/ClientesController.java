package com.samuel.contratos.controller;

import com.samuel.contratos.model.Cliente;
import com.samuel.contratos.model.Contrato;
import com.samuel.contratos.model.Endereco;
import com.samuel.contratos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/clientes")
public class ClientesController {

    private final ClienteService clienteService;

    @Autowired
    public ClientesController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/form/addCliente") //mostrar o formulario do cliente
    public String mostrarFormulario(Model model) {
        model.addAttribute("clientes", new Cliente());
        model.addAttribute("endereco", new Endereco());
        return "formulario-cliente";
    }

    @PostMapping
    public String salvarCliente(@ModelAttribute Cliente cliente,
                                @ModelAttribute Endereco endereco) {
        clienteService.salvarCliente(cliente, endereco);
        return "redirect:/inicio";
    }

/*   @DeleteMapping
//    public String excluirCliente(Cliente cliente) {
//        clienteService.excluirCliente(cliente);
//        return "Cliente excluido com sucesso!";
}*/

    @GetMapping("/controle/listaClientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();

        //para cada cliente, busca os contratos associados
        for (Cliente cliente : clientes) {
            List<Contrato> contratosDoCliente = clienteService
                    .listarContratosPorCliente(cliente.getId());
            cliente.setContratos(contratosDoCliente);
        }

        model.addAttribute("clientes", clientes);

        return "Controle-de-clientes";
    }

/*    @PutMapping
//    public String atualizarDadosCliente(Cliente cliente) {
//        clienteService.atualizarDadosCliente(cliente);
//        return "Cliente atualizado com sucesso!";
 }*/

    @GetMapping
    public String pesquisarCliente(@RequestParam(name = "nome",
            required = false) String nome,
                                   Model model) {

        List<Cliente> clientes;
        if (nome != null && !nome.isEmpty()) {
            clientes = clienteService.pesquisarCliente(nome);
        } else {
            clientes = clienteService.pesquisarCliente("");
        }
        model.addAttribute("clientes", clientes);
        return "Controle-de-clientes";
    }

    @GetMapping("{id}")
    public String mostrarInformacoes(@PathVariable UUID id,
                                     Model model) {
       Cliente cliente = clienteService.informacoesCliente(id);
       model.addAttribute("cliente", cliente);
       return "informacoes-cliente";
    }

    @GetMapping("/clientes")
    public String voltar() {
        return "Controle-de-clientes";
    }
}
