package br.hackathon.com.domain.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
  
    @Column(name = "razaosocial", nullable = false)
    private String razaoSocial;
 
    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "telefone", nullable = false)
    private String telefone;
    
    @OneToMany
    @JoinColumn
    private List<Cotacao>  cotacoes;
    
    private UUID usuarioId;
    
    @CreationTimestamp
    private LocalDateTime dataCadastro;
    
}
