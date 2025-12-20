package br.hackathon.com.adapters.out;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hackathon.com.domain.models.Proposta;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, UUID> {

}
