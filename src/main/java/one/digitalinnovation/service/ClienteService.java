package one.digitalinnovation.service;

import one.digitalinnovation.model.Cliente;

/**
 * Interface que define o padrao<b>Strategy</b> no dominio de Cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 *
 * @author Daniel Machado
 * */
public interface ClienteService {

    Iterable<Cliente> buscaTodos();

    Cliente buscaPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
