package one.digitalinnovation.service.impl;

import one.digitalinnovation.model.Cliente;
import one.digitalinnovation.model.ClienteRepository;
import one.digitalinnovation.model.Endereco;
import one.digitalinnovation.model.EnderecoRepository;
import one.digitalinnovation.service.ClienteService;
import one.digitalinnovation.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}. Com isso, como essa classe é um)
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 *
 * @author Daniel Machado
 * */
@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    public ClienteServiceImpl() {
    }
    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrai integrações com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Cliente> buscaTodos(){
        return clienteRepository.findAll();
    }
    @Override
    public Cliente buscaPorId(Long id) {
       Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }
    @Override
    public void inserir(Cliente cliente) {
        // Verifica se o endereço do cliente já existe(pelo Cep)
        salvarClienteComCep(cliente);
    }
        @Override
    public void atualizar(Long id, Cliente cliente) {
        // Busca cliente por id, caso exista:
            // Verifica se o endereço do Cliente já existe (pelo Cep)
            // Caso não exista, integrar co via CEP e persistir o retorno
            // Alterar Cliente, vinculando o endereço (novo ou existente)

        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteComCep(cliente);
        }
    }
    @Override
    public void deletar(Long id) {
        // Deletar cliente por id
        clienteRepository.deleteById(id);
    }
    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereceo().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com ViaCEP e persistir o retorno
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereceo(endereco);
        // Inserir cliente, vinculando o endereço(novo ou existente)
        clienteRepository.save(cliente);
    }
}


