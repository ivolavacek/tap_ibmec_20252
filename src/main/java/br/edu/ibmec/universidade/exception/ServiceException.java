package br.edu.ibmec.universidade.exception;

import lombok.Getter;

import java.util.ArrayList;

@Getter
@SuppressWarnings("serial")
public class ServiceException extends Exception {
	private String message;
	private ServiceExceptionEnum tipo;

	// private ArrayList;

	public ServiceException() {

	}

	public ServiceException(String msg) {
		super(msg);
		this.message = msg;
	}

	public ServiceException(ServiceExceptionEnum tipo) {
		this.tipo = tipo;
	}

	public ServiceException(ArrayList listaErrosCurso) {
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String erroAoListarInscrições, Exception e) {
	}

	public void setMessage(String msg) {
		this.message = msg;
	}

    public void setTipo(ServiceExceptionEnum tipo) {
		this.tipo = tipo;
	}

	public enum ServiceExceptionEnum {

		CURSO_CODIGO_INVALIDO("Código de curso inválido"), CURSO_NOME_INVALIDO(
				"Nome de curso inválido"), ALUNO_MATRICULA_INVALIDA, ALUNO_NOME_INVALIDO;

		private ServiceExceptionEnum() {
			// TODO Auto-generated constructor stub
		}

		private ServiceExceptionEnum(String valor) {
			// TODO Auto-generated constructor stub
		}

	}
}
