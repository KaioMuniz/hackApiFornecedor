package br.hackathon.com.application.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.hackathon.com.adapters.dtos.CepRequest;
import br.hackathon.com.adapters.dtos.ViaCepResponse;
import br.hackathon.com.application.ports.EnderecoService;
import jakarta.transaction.Transactional;

@Service
public class EnderecoServiceImpl implements EnderecoService {
	
	@Transactional
	public ViaCepResponse getEnderecoById(CepRequest cep) {
		
		RestTemplate restTemp = new RestTemplate();
		
		String url = "https://viacep.com.br/ws/" + cep.getCep() + "/json";
			
		ViaCepResponse response = restTemp.getForObject(url, ViaCepResponse.class);
		
		return response;
		
	}
}