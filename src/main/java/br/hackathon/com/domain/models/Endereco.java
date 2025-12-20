package br.hackathon.com.domain.models;

import lombok.Data;

@Data
public class Endereco {

	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	private String estado;
	
	private String cep;
	
}
