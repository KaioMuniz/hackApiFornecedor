package br.hackathon.com.adapters.dtos;

import java.util.List;

import lombok.Data;

@Data
public class CadastrarFornecedorRequest {

	private String nome;
	private String cnpj;
	private String telefone;
	private List<String> contatos;
	private EnderecoRequest endereco;
}
