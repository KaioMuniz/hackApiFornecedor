package br.hackathon.com.adapters.out;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.hackathon.com.domain.models.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {

	@Query("""
			SELECT COUNT(f) > 0
			FROM Fornecedor f
			WHERE f.usuarioId = :usuarioId
			""")
	boolean existsFornecedorByUsuarioId(@Param("usuarioId") UUID usuarioId);
	
	@Query("""
			SELECT COUNT(f) > 0
			FROM Fornecedor f
			WHERE f.cnpj = :cpnj
			""")
	boolean existsByCnpj(@Param("cpnj") String cnpj);
	
	Optional<Fornecedor> findByUsuarioId(UUID usuarioId);
}
