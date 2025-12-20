package br.hackathon.com.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "fornecedor")
public class Fornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String nome;
	private String cnpj;
	private String telefone;
	@Embedded
	private Endereco endereco;
	private List<String> contatos;

	@ManyToMany(mappedBy = "fornecedores")
	private 	List<Cotacao> cotacoes = new ArrayList<>();
	private UUID usuarioId;
	
}


