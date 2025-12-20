package br.hackathon.com.domain.exceptions;

@SuppressWarnings("serial")
public class CotacaoNaoEncontradaException extends RuntimeException {

	@Override
	public String getMessage() {
		return "Cotação não encontrada, verfique o ID informado. ";
	}
}
