package com.attornatus.avaliacaobackend.br.service;

import com.attornatus.avaliacaobackend.br.model.cliente.Cliente;
import com.attornatus.avaliacaobackend.br.model.endereco.Endereco;

import java.util.List;


public interface ClienteService {

	Iterable<Cliente> buscarTodos();

	Cliente buscarPorId(Long id);
	List<Endereco> buscaDeEnderecos(Long id);
	Endereco inserirEndereco(Long id, Endereco endereco);

	void inserir(Cliente cliente);

	void atualizar(Long id, Cliente cliente);

	void deletar(Long id);

}
