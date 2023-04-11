package com.api.apiClienteProduto.controller;

import com.api.apiClienteProduto.entity.cliente.Cliente;
import com.api.apiClienteProduto.entity.cliente.ClienteRepository;
import com.api.apiClienteProduto.entity.cliente.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private ClienteRepository repository;

    @PostMapping("clientes")
    @Transactional
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente){
        validarCliente(cliente);
        validarCPF(cliente.getCpf());
        validarEmail(cliente.getEmail());
        service.saveCliente(cliente);
        return ResponseEntity.ok(cliente);
    }

    private void validarCliente(Cliente newCliente) {
        if (newCliente.getNome() == null || newCliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("Cliente without name");
        } else if (newCliente.getEmail() == null || newCliente.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Cliente without email");
        } else if (newCliente.getNomeMae() == null || newCliente.getNomeMae().isEmpty()) {
            throw new IllegalArgumentException("Cliente without mother's name");
        } else if (newCliente.getSenha() == null || newCliente.getSenha().isEmpty()) {
            throw new IllegalArgumentException("Cliente without password");
        } else if (newCliente.getTelefone() == null || newCliente.getTelefone().isEmpty()) {
            throw new IllegalArgumentException("Cliente without phone number");
        } else if (newCliente.getIdade() == null) {
            throw new IllegalArgumentException("Cliente without age");
        } else if (newCliente.getEndereco() == null) {
            throw new IllegalArgumentException("Cliente without address");
        } else if (newCliente.getCpf() == null || newCliente.getCpf().isEmpty()) {
            throw new IllegalArgumentException("Cliente without CPF");
        } else if (newCliente.getRg() == null || newCliente.getRg().isEmpty()) {
            throw new IllegalArgumentException("Cliente without RG");
        } else if (newCliente.getRenda() == null) {
            throw new IllegalArgumentException("Cliente without income");
        } else if (newCliente.getPatrimonio() == null) {
            throw new IllegalArgumentException("Cliente without assets");
        } else if (newCliente.getProdutos() == null || newCliente.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("Cliente without products");
        }
    }

    public void validarCPF(String cpf){
        if(cpf.length() != 11){
            throw new IllegalArgumentException("cpf inválido");
        }
    }

    public void validarEmail(String email){
        if(!email.contains("@")){
            throw new IllegalArgumentException("email inválido");
        }
    }

    @GetMapping("clientes")
    public ResponseEntity<List<Cliente>> all(){
        return ResponseEntity.ok(service.getAllClientes());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Cliente> one(@PathVariable Long id){
        return ResponseEntity.ofNullable(service.findById(id));
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> desativarCliente(@PathVariable Long id){
        Cliente clienteDesativado = service.desativarCliente(id);
        if (clienteDesativado != null) {
            return ResponseEntity.ok(clienteDesativado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }






}
