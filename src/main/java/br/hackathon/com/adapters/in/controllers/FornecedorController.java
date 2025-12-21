package br.hackathon.com.adapters.in.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.hackathon.com.adapters.dtos.CadastrarFornecedorRequest;
import br.hackathon.com.adapters.dtos.CadastroFornecedorResponse;
import br.hackathon.com.adapters.dtos.NegociarCotacaoRequest;
import br.hackathon.com.adapters.dtos.NegociarSolicitacaoResponse;
import br.hackathon.com.adapters.dtos.SolicitarCotacaoRequest;
import br.hackathon.com.adapters.dtos.SolicitarCotacaoResponse;
import br.hackathon.com.application.components.JwtTokenComponent;
import br.hackathon.com.application.ports.FornecedorService;
import br.hackathon.com.domain.models.Fornecedor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/fornecedor")
@RequiredArgsConstructor
public class FornecedorController {

	@Autowired FornecedorService service;
	@Autowired JwtTokenComponent jwt;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<CadastroFornecedorResponse> cadastrarFornecedor(
			@RequestBody CadastrarFornecedorRequest request, HttpServletRequest http ) {
		
		return ResponseEntity.ok(service.cadastrarFornecedor(request, getUsuarioId(http)));
		
	}
	
	@GetMapping("all")
	public ResponseEntity<List<Fornecedor>> listFornecedores(HttpServletRequest http) {
		
		return ResponseEntity.ok(service.listAll(getUsuarioId(http)));
	}
	
	@PostMapping("/solicitar/cotacao/{id}")
	public ResponseEntity<SolicitarCotacaoResponse> solicitarCotacao(@PathVariable UUID id,
			@RequestBody SolicitarCotacaoRequest request, HttpServletRequest http) {
		
		return ResponseEntity.ok(service.solicitarCotacao(id, request, getUsuarioId(http)));
	}
	
	@PostMapping("/negociar/cotacao/{id}")
	public ResponseEntity<NegociarSolicitacaoResponse> negociarCotacao(@PathVariable UUID id,
			@RequestBody NegociarCotacaoRequest request, HttpServletRequest http ) {
		return ResponseEntity.ok(service.negociarCotacao(id, request, getUsuarioId(http)));
	}
	
	private UUID getUsuarioId(HttpServletRequest request) {
		
		var token = request.getHeader("Authorization").replace("Bearer ", "");
		
		return jwt.getIdFromToken(token);
	}
}
