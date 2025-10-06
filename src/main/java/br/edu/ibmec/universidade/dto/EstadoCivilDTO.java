package br.edu.ibmec.universidade.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum EstadoCivilDTO {
	SOLTEIRO, CASADO, DIVORCIADO, VIUVO;
}
