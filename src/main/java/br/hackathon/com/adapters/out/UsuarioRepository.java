package br.hackathon.com.adapters.out;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.hackathon.com.domain.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,UUID> {

}
