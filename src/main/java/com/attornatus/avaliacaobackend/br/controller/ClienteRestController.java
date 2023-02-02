package com.attornatus.avaliacaobackend.br.controller;

import com.attornatus.avaliacaobackend.br.dto.DadosLogin;
import com.attornatus.avaliacaobackend.br.dto.UserAutheticatedDTO;
import com.attornatus.avaliacaobackend.br.model.client.Cliente;
import com.attornatus.avaliacaobackend.br.model.endereco.Endereco;
import com.attornatus.avaliacaobackend.br.model.user.User;
import com.attornatus.avaliacaobackend.br.security.UserAuthenticationService;
import com.attornatus.avaliacaobackend.br.service.ClienteService;
import com.attornatus.avaliacaobackend.br.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;
	private UserAuthenticationService userAuthenticationService;

	@Autowired
	private void AuthenticationController(UserAuthenticationService userAuthenticationService){
		this.userAuthenticationService = userAuthenticationService;
	}
	private int login(String email, String senha, String token){
		DadosLogin dadosLogin = new DadosLogin();
		dadosLogin.setSenha(senha);
		dadosLogin.setEmail(email);
		User user = userAuthenticationService.authenticate(dadosLogin, token);

		return new ResponseEntity<>(UserAutheticatedDTO.toDTO(user, "Bearer "), HttpStatus.ACCEPTED).getStatusCodeValue();

	}

	@GetMapping
	public ResponseEntity<Iterable<Cliente>> buscarTodos(@RequestHeader String email, @RequestHeader String senha, @RequestHeader String token) {

		if(login(email, senha, token) == 202){
			return ResponseEntity.ok(clienteService.buscarTodos());
		}else{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}
	@GetMapping("/{id}/enderecos")
	public ResponseEntity<List<Endereco>> buscaDeEnderecos(@PathVariable Long id) {
		return ResponseEntity.ok(clienteService.buscaDeEnderecos(id));
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

	@PutMapping("/{id}/inserirEndereco")
	public ResponseEntity<Endereco> inserirEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
		return ResponseEntity.ok(clienteService.inserirEndereco(id,endereco));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id, @RequestHeader String email, @RequestHeader String senha, @RequestHeader String token) {

		if(login(email, senha, token) == 202){
			clienteService.deletar(id);
			return ResponseEntity.ok().build();
		}else{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}
