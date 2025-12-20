package br.hackathon.com.adapters.dtos;

import java.util.List;

import br.hackathon.com.domain.models.Endereco;
import lombok.Data;

@Data
public class CadastrarFornecedorRequest {

	private String nome;
	private String cnpj;
	private String telefone;
	private Endereco endereco;
	private List<String> contatos;
	private EnderecoRequest request;
}
