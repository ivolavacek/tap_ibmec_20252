package br.edu.ibmec.universidade.dto;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@XmlRootElement(name = "curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
	private int codigo;
	private String nome;
}
