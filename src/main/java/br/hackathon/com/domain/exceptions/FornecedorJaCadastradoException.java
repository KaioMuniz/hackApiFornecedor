package br.hackathon.com.domain.exceptions;

@SuppressWarnings("serial")
public class FornecedorJaCadastradoException extends RuntimeException {

	@Override
	public String getMessage() {
		return "Este fornecedor já está cadastrado.";
	}
}
