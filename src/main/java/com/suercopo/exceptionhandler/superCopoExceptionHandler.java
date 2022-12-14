package com.suercopo.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class superCopoExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String messageUsuario = messageSource.getMessage("mensagem.ivalida", null, LocaleContextHolder.getLocale());
		String mesagemDesenvolverdo = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
		return handleExceptionInternal(ex, new Erro(messageUsuario, mesagemDesenvolverdo), headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	@ExceptionHandler({ DataIntegrityViolationException.class } )
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest resquest){
		String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, resquest);
		
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	   public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,  WebRequest request) {
			
			
			String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = ex.toString();
			
			List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		}


	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String mesagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mesagemDesenvolverdor = fieldError.toString();
			erros.add(new Erro(mesagemUsuario, mesagemDesenvolverdor));
		}

		return erros;
	}

	public static class Erro {

		private String mesagemUsuario;
		private String mesagemDesenvolerdor;

		public Erro(String mesagemUsuario, String mesagemDesenvolerdor) {
			super();
			this.mesagemUsuario = mesagemUsuario;
			this.mesagemDesenvolerdor = mesagemDesenvolerdor;
		}

		public String getMesagemUsuario() {
			return mesagemUsuario;
		}

		public String getMesagemDesenvolerdor() {
			return mesagemDesenvolerdor;
		}

	}

}
