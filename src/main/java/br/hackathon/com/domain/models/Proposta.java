package br.hackathon.com.domain.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "proposta")
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String texto;
	
	@ManyToOne
	@JoinColumn(name = "cotacao_id", nullable = false)
	private Cotacao cotacao;
	@ManyToOne
	@JoinColumn(name = "fornecedor_id", nullable = false)
	private Fornecedor fornecedor;
	
	private UUID usuarioId;
}
