package br.hackathon.com.adapters.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CepRequest {

	@Size(min = 8, max = 9, message = "O CEP deve conter entre 8 e 9 caracteres")
	private String cep;
}
