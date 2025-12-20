package br.hackathon.com.domain.exceptions;

@SuppressWarnings("serial")
public class FornecedorNaoEncontradoException extends RuntimeException {

	@Override
	public String getMessage() {
		return "Fornecedor não encontrado.";
	}
}
