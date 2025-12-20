package br.hackathon.com.adapters.dtos;

import java.util.List;
import java.util.UUID;

import br.hackathon.com.domain.models.Cotacao;
import br.hackathon.com.domain.models.Endereco;
import lombok.Data;

@Data
public class CadastroFornecedorResponse {

	private UUID id;
	private String nome;
	private String cnpj;
	private String telefone;
	private Endereco endereco;
	private List<String> contatos;
	private 	List<Cotacao> cotacoes;
	private UUID usuarioId;
}
