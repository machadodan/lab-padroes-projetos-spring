package one.digitalinnovation.controller;

import one.digitalinnovation.model.Cliente;
import one.digitalinnovation.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Esse{@link RestController}representa nossa<b> Facade.</b>, pois abstrai todos
 * a complexidade de integração (banco de dados H2 e API do ViaCep) em uma
 * interface simples e coesa(API REST)
 *
 * @author Daniel Machado
 * */

@RestController
@RequestMapping("clientes")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> buscaTodos() {
        return ResponseEntity.ok(clienteService.buscaTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscaPorId(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) {
        clienteService.inserir(cliente);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        clienteService.atualizar(id, cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
