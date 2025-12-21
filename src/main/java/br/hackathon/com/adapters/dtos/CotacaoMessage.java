package br.hackathon.com.adapters.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class CotacaoMessage {
	
	private UUID idCotacao;
	private String emailEmpresa;
	private String emailFornecedor;

}
