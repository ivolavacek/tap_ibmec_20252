package br.edu.ibmec.universidade.dto;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@XmlRootElement(name = "turma")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurmaDTO {
	private int codigo;
	private int ano;
	private int semestre;
	private int disciplina;
}
