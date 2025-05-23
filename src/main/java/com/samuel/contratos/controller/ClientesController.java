package com.samuel.contratos.controller;

import com.samuel.contratos.controller.dtos.ClienteDto;
import com.samuel.contratos.controller.mappers.ClienteMapper;
import com.samuel.contratos.model.Cliente;
import com.samuel.contratos.model.Contrato;
import com.samuel.contratos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @GetMapping("/form/addCliente") //mostrar o formulario do cliente
    public String mostrarFormulario(
            @ModelAttribute ClienteDto clienteDto,
            Model model) {

        model.addAttribute("clienteDto", clienteMapper.toEntity(clienteDto));

        return "formulario-cliente";
    }

    @PostMapping
    public String salvarCliente(@ModelAttribute("clienteDto") @Valid ClienteDto clienteDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) { //se houver erros no formulário, volta para o mesmo
            return "formulario-cliente";
        }

        clienteService.salvarCliente(clienteDto);
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
            required = false) String nome,Model model) {

        List<Cliente> resultado = clienteService.pesquisarCliente(nome);

        model.addAttribute("clientes", resultado);

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
