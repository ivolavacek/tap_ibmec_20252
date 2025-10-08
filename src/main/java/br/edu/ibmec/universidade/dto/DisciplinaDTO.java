package br.edu.ibmec.universidade.dto;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@XmlRootElement(name = "disciplina")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDTO {
    private int codigo;
    private String nome;
    private int curso;
}

