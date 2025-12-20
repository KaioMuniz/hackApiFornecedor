package br.hackathon.com.application.ports;

import br.hackathon.com.adapters.dtos.CepRequest;
import br.hackathon.com.adapters.dtos.ViaCepResponse;

public interface EnderecoService {

	ViaCepResponse getEnderecoById(CepRequest cep);
}
