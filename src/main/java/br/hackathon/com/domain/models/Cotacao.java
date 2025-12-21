package br.hackathon.com.domain.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cotacao")
public class Cotacao {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String nome;

	@ManyToMany
	private List<Fornecedor> fornecedores = new ArrayList<>();

	@OneToMany(mappedBy = "cotacao")
	private List<Proposta> propostas;

	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;

	@Column(nullable = false)
	private LocalDate dataLimite; // Para o sistema saber quando parar de aceitar propostas

	@Enumerated(EnumType.STRING)
	private StatusCotacao status = StatusCotacao.ABERTA;

	@Column(columnDefinition = "TEXT")
	private String descricaoItens;

}

