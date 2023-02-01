package com.attornatus.avaliacaobackend.br.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.attornatus.avaliacaobackend.br.model.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attornatus.avaliacaobackend.br.model.cliente.Cliente;
import com.attornatus.avaliacaobackend.br.model.endereco.Endereco;
import com.attornatus.avaliacaobackend.br.model.endereco.EnderecoRepository;
import com.attornatus.avaliacaobackend.br.service.ClienteService;
import com.attornatus.avaliacaobackend.br.service.ViaCepService;


@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;

	@Override
	public Iterable<Cliente> buscarTodos() {
		// Buscar todos os Clientes.
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		// Buscar Cliente por ID.
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public List<Endereco> buscaDeEnderecos(Long id) {
		return buscarPorId(id).getEnderecos();
	}

	@Override
	public Endereco inserirEndereco(Long id, Endereco endereco) {
		Cliente cliente = buscarPorId(id);
		List<Endereco> enderecos = cliente.getEnderecos();

		Endereco novoEndereco = enderecoRepository.findById(endereco.getCep()).orElseGet(() ->
				viaCepService.consultarCep(endereco.getCep())
		);
		novoEndereco.setPrincipal(endereco.isPrincipal());
		novoEndereco.setNumero(endereco.getNumero());
		enderecoRepository.save(novoEndereco);

		enderecos.add(novoEndereco);
		cliente.setEnderecos(enderecos);
		atualizar(id, cliente);
		return novoEndereco;
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		// Buscar Cliente por ID, caso exista:
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCep(Cliente cliente) {
		List<Endereco> enderecos = new ArrayList<>();
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		for (int i = 0; i < cliente.getEnderecos().size(); i++) {
			Endereco enderecoGet = cliente.getEnderecos().get(i);
			String cep = enderecoGet.getCep();
			Endereco novoEndereco = enderecoRepository.findById(cep).orElseGet(() -> {
				// Caso não exista, integrar com o ViaCEP e persistir o retorno.
				return viaCepService.consultarCep(cep);
			});
			novoEndereco.setPrincipal(enderecoGet.isPrincipal());
			novoEndereco.setNumero(enderecoGet.getNumero());
			enderecoRepository.save(novoEndereco);
			enderecos.add(novoEndereco);
		}
		cliente.setEnderecos(enderecos);

		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		clienteRepository.save(cliente);
	}

}
