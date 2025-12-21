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
import br.hackathon.com.adapters.out.FornecedorRepository;
import br.hackathon.com.adapters.out.PropostaRepository;
import br.hackathon.com.application.ports.FornecedorService;
import br.hackathon.com.domain.exceptions.CotacaoNaoEncontradaException;
import br.hackathon.com.domain.exceptions.FornecedorJaCadastradoException;
import br.hackathon.com.domain.exceptions.FornecedorNaoEncontradoException;
import br.hackathon.com.domain.exceptions.RecursoNaoAutorizadoException;
import br.hackathon.com.domain.models.Endereco;
import br.hackathon.com.domain.models.Fornecedor;
import br.hackathon.com.domain.models.Proposta;
import br.hackathon.com.domain.models.StatusCotacao;

@Service
public class FornecedorServiceImpl implements FornecedorService {

	@Autowired CotacaoRepository cotacaoRepository;
	@Autowired FornecedorRepository fornecedorRepository;
	@Autowired PropostaRepository propostaRepository;
	
	@Override
	public SolicitarCotacaoResponse solicitarCotacao(UUID id, SolicitarCotacaoRequest request, UUID usuarioId) {
		
		var fornecedor = fornecedorRepository.findByUsuarioId(usuarioId).orElseThrow(() -> new FornecedorNaoEncontradoException());
		var cotacao = cotacaoRepository.findById(id).orElseThrow(() -> new CotacaoNaoEncontradaException());
		
		 if(cotacao.getStatus().equals(StatusCotacao.CANCELADA) || cotacao.getStatus().equals(StatusCotacao.ENCERRADA)) {
			 throw new RecursoNaoAutorizadoException();
		 }
		
		 if (!cotacao.getFornecedores().contains(fornecedor)) {
		        cotacao.getFornecedores() .add(fornecedor);
		    }

		    Proposta proposta = new Proposta();
		    proposta.setCotacao(cotacao);
		    proposta.setTexto(request.getTexto());
		    proposta.setUsuarioId(usuarioId);

		    propostaRepository.save(proposta);

		    // SALVA SOMENTE O LADO DONO
		    cotacaoRepository.save(cotacao);

		    SolicitarCotacaoResponse resp = new SolicitarCotacaoResponse();
		    resp.setIdCotacao(id);
		    return resp;
		}

	@Override
	public NegociarSolicitacaoResponse negociarCotacao(UUID id, NegociarCotacaoRequest request, UUID usuarioId) {
		
		
		var cotacao = cotacaoRepository.findById(id).orElseThrow(() -> new CotacaoNaoEncontradaException());
		if(cotacao.getPropostas().size() < 1) {
			throw new RecursoNaoAutorizadoException();
		}
		
		var proposta = new Proposta();
		proposta.setCotacao(cotacao);
		proposta.setTexto(request.getTexto());
		proposta.setUsuarioId(usuarioId);
		
		propostaRepository.save(proposta);
		
		
		
		cotacao.getPropostas().add(proposta);
		cotacaoRepository.save(cotacao);
		
		var resp = new NegociarSolicitacaoResponse();
		
		resp.setId(cotacao.getId());
		
		return resp;
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
		fornecedor.setPropostas(List.of());
		
		var endereco = new Endereco();
		endereco.setBairro(request.getEndereco().getBairro());
		endereco.setCep(request.getEndereco().getCep());
		endereco.setCidade(request.getEndereco().getCidade());
		endereco.setComplemento(request.getEndereco().getComplemento());
		endereco.setEstado(request.getEndereco().getEstado());
		endereco.setLogradouro(request.getEndereco().getLogradouro());
		endereco.setNumero(request.getEndereco().getNumero());
		
		fornecedor.setEndereco(endereco);

		fornecedorRepository.save(fornecedor);
		
		var resp = new CadastroFornecedorResponse();
		resp.setCnpj(fornecedor.getCnpj());
		resp.setContatos(fornecedor.getContatos());
		resp.setCotacoes(fornecedor.getCotacoes());
		resp.setEndereco(fornecedor.getEndereco());
		resp.setId(fornecedor.getId());
		resp.setTelefone(fornecedor.getTelefone());
		resp.setNome(fornecedor.getNome());
		resp.setUsuarioId(fornecedor.getUsuarioId());
		
		return resp;
	}

	public List<Fornecedor> listAll(UUID usuarioId) {
		
		var fornecedores = fornecedorRepository.findAll();
		
		return fornecedores.stream()
				.toList();
	}
	
	
}
