package br.hackathon.com.adapters.in.handlers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.hackathon.com.domain.exceptions.CotacaoNaoEncontradaException;
import br.hackathon.com.domain.exceptions.FornecedorJaCadastradoException;
import br.hackathon.com.domain.exceptions.FornecedorNaoEncontradoException;
import br.hackathon.com.domain.exceptions.RecursoNaoAutorizadoException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CotacaoNaoEncontradaException.class)
	public ResponseEntity<Map<String, Object>> handleCotacaoNaoEncontradaException(CotacaoNaoEncontradaException ex, WebRequest request) {
		var body = new HashMap<String, Object>();
		body.put("timestamp", LocalDateTime.now());
		body.put("error", HttpStatus.BAD_REQUEST.value());
		body.put("message", ex.getMessage());
		
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FornecedorJaCadastradoException.class)
	public ResponseEntity<Map<String, Object>> handleCotacaoNaoEncontradaException(FornecedorJaCadastradoException ex, WebRequest request) {
		var body = new HashMap<String, Object>();
		body.put("timestamp", LocalDateTime.now());
		body.put("error", HttpStatus.BAD_REQUEST.value());
		body.put("message", ex.getMessage());
		
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FornecedorNaoEncontradoException.class)
	public ResponseEntity<Map<String, Object>> handleCotacaoNaoEncontradaException(FornecedorNaoEncontradoException ex, WebRequest request) {
		var body = new HashMap<String, Object>();
		body.put("timestamp", LocalDateTime.now());
		body.put("error", HttpStatus.BAD_REQUEST.value());
		body.put("message", ex.getMessage());
		
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RecursoNaoAutorizadoException.class)
	public ResponseEntity<Map<String, Object>> handleCotacaoNaoEncontradaException(RecursoNaoAutorizadoException ex, WebRequest request) {
		var body = new HashMap<String, Object>();
		body.put("timestamp", LocalDateTime.now());
		body.put("error", HttpStatus.UNAUTHORIZED.value());
		body.put("message", ex.getMessage());
		
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	
}
