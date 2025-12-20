package br.hackathon.com.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.hackathon.com.adapters.dtos.CadastrarFornecedorRequest;
import br.hackathon.com.adapters.dtos.CadastroFornecedorResponse;
import br.hackathon.com.adapters.dtos.NegociarCotacaoRequest;
import br.hackathon.com.adapters.dtos.NegociarSolicitacaoResponse;
import br.hackathon.com.adapters.dtos.SolicitarCotacaoRequest;
import br.hackathon.com.adapters.dtos.SolicitarCotacaoResponse;
import br.hackathon.com.adapters.out.CotacaoRepository;
import br.hackathon.com.adapters.out.EnderecoRepository;
import br.hackathon.com.adapters.out.FornecedorRepository;
import br.hackathon.com.application.ports.FornecedorService;
import br.hackathon.com.domain.exceptions.CotacaoNaoEncontradaException;
import br.hackathon.com.domain.exceptions.FornecedorJaCadastradoException;
import br.hackathon.com.domain.exceptions.FornecedorNaoEncontradoException;
import br.hackathon.com.domain.models.Endereco;
import br.hackathon.com.domain.models.Fornecedor;

@Service
public class FornecedorServiceImpl implements FornecedorService {

	@Autowired CotacaoRepository cotacaoRepository;
	@Autowired FornecedorRepository fornecedorRepository;
	@Autowired EnderecoRepository enderecoRepository;
	
	@Override
	public SolicitarCotacaoResponse solicitarCotacao(UUID id, SolicitarCotacaoRequest request, UUID usuarioId) {
		
		var fornecedor = fornecedorRepository.findByUsuarioId(usuarioId).orElseThrow(() -> new FornecedorNaoEncontradoException());
		var cotacao = cotacaoRepository.findById(id).orElseThrow(() -> new CotacaoNaoEncontradaException());
		
		var listCot = fornecedor.getCotacoes();
		var listForn = cotacao.getFornecedores();
		
		
		if(listCot.isEmpty()) {
			fornecedor.setCotacoes(List.of(cotacao));
			fornecedorRepository.save(fornecedor);
			cotacao.setFornecedores(List.of(fornecedor));
			cotacaoRepository.save(cotacao);
		}
		
		listCot.add(cotacao);
		listForn.add(fornecedor);
		
		fornecedor.setCotacoes(listCot);
		cotacao.setFornecedores(listForn);
		
		fornecedorRepository.save(fornecedor);
		
		cotacaoRepository.save(cotacao);
		
		var resp = new SolicitarCotacaoResponse();
		
		resp.setIdCotacao(id);
		
		return resp;
	}

	@Override
	public NegociarSolicitacaoResponse negociarCotacao(UUID id, NegociarCotacaoRequest request, UUID usuarioId) {
		
		
		return null;
	}

	@Override
	public CadastroFornecedorResponse cadastrarFornecedor(CadastrarFornecedorRequest request, UUID usuarioId) {
		
		if(fornecedorRepository.existsFornecedorByUsuarioId(usuarioId)) {
			throw new FornecedorJaCadastradoException();
		}
		
		if(fornecedorRepository.existsByCnpj(request.getCnpj())) {
			throw new FornecedorJaCadastradoException();
		}
		
		
		var fornecedor = new Fornecedor();
		fornecedor.setCnpj(request.getCnpj());
		fornecedor.setContatos(List.of());
		fornecedor.setCotacoes(List.of());
		fornecedor.setNome(request.getNome());
		fornecedor.setTelefone(request.getTelefone());
		fornecedor.setUsuarioId(usuarioId);
		
		var endereco = new Endereco();
		endereco.setBairro(request.getEndereco().getBairro());
		endereco.setCep(request.getEndereco().getCep());
		endereco.setCidade(request.getEndereco().getCidade());
		endereco.setComplemento(request.getEndereco().getComplemento());
		endereco.setEstado(request.getEndereco().getEstado());
		endereco.setLogradouro(request.getEndereco().getLogradouro());
		endereco.setNumero(request.getEndereco().getNumero());
		
		endereco.setFornecedor(fornecedor);
		fornecedor.setEndereco(endereco);

		enderecoRepository.save(endereco);
		fornecedorRepository.save(fornecedor);
		
		var resp = new CadastroFornecedorResponse();
		resp.setCnpj(fornecedor.getCnpj());
		resp.setContatos(fornecedor.getContatos());
		resp.setCotacoes(fornecedor.getCotacoes());
		resp.setEndereco(fornecedor.getEndereco());
		resp.setId(fornecedor.getId());
		resp.setTelefone(fornecedor.getTelefone());
		resp.setUsuarioId(fornecedor.getUsuarioId());
		
		return resp;
	}

	
	
}
