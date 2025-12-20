package br.hackathon.com.application.ports;

import java.util.UUID;

import br.hackathon.com.adapters.dtos.CadastrarFornecedorRequest;
import br.hackathon.com.adapters.dtos.CadastroFornecedorResponse;
import br.hackathon.com.adapters.dtos.NegociarCotacaoRequest;
import br.hackathon.com.adapters.dtos.NegociarSolicitacaoResponse;
import br.hackathon.com.adapters.dtos.SolicitarCotacaoRequest;
import br.hackathon.com.adapters.dtos.SolicitarCotacaoResponse;

public interface FornecedorService {
	
	CadastroFornecedorResponse cadastrarFornecedor(CadastrarFornecedorRequest request, UUID usuarioId);

	SolicitarCotacaoResponse solicitarCotacao(UUID id, SolicitarCotacaoRequest request, UUID usuarioId);
	
	NegociarSolicitacaoResponse negociarCotacao(UUID id, NegociarCotacaoRequest request, UUID usuarioId);
}
