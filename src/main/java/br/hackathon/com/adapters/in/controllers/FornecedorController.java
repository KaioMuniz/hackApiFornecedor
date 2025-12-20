package br.hackathon.com.adapters.in.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.hackathon.com.adapters.dtos.CadastrarFornecedorRequest;
import br.hackathon.com.adapters.dtos.CadastroFornecedorResponse;
import br.hackathon.com.adapters.dtos.SolicitarCotacaoRequest;
import br.hackathon.com.adapters.dtos.SolicitarCotacaoResponse;
import br.hackathon.com.application.components.JwtTokenComponent;
import br.hackathon.com.application.ports.FornecedorService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/funcionario")
public class FornecedorController {

	@Autowired FornecedorService service;
	@Autowired JwtTokenComponent jwt;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<CadastroFornecedorResponse> cadastrarFornecedor(
			@RequestBody CadastrarFornecedorRequest request, HttpServletRequest http ) {
		
		return ResponseEntity.ok(service.cadastrarFornecedor(request, getUsuarioId(http)));
		
	}
	
	public ResponseEntity<SolicitarCotacaoResponse> solicitarCotacao(@PathVariable UUID id,@RequestBody SolicitarCotacaoRequest request, HttpServletRequest http) {
		
		return ResponseEntity.ok(service.solicitarCotacao(id, request, getUsuarioId(http)));
	}
	
	private UUID getUsuarioId(HttpServletRequest request) {
		
		var token = request.getHeader("Authorization").replace("Bearer ", "");
		
		return jwt.getIdFromToken(token);
	}
}
