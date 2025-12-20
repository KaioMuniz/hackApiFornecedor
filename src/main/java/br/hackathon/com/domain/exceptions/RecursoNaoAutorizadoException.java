package br.hackathon.com.domain.exceptions;

@SuppressWarnings("serial")
public class RecursoNaoAutorizadoException extends RuntimeException {

	@Override
	public String getMessage() {
		return "Recurso não autorizado.";
	}
}
