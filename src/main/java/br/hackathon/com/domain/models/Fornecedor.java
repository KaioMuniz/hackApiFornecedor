package br.hackathon.com.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Fornecedor {

	private UUID id;
	private String nome;
	private String cnpj;
	private String telefone;
	private Endereco endereco;
	private List<String> contatos;
	private 	List<Cotacao> cotacoes = new ArrayList<>();
	private UUID usuarioId;
	
}

/*
 * Razão Social e Nome Fantasia (se aplicável).
CNPJ (Cadastro Nacional da Pessoa Jurídica) ou CPF (se for pessoa física).
Inscrição Estadual e Inscrição Municipal (se houver e aplicável ao tipo de negócio).
Endereço completo (incluindo CEP, cidade e estado).
Dados de Contato: Telefone(s) e e-mail(s).
Dados Bancários para fins de pagamento.
Nome e função da pessoa de contato ou representante legal. */
