package br.hackathon.com.adapters.out;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hackathon.com.domain.models.Cotacao;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, UUID> {

}
