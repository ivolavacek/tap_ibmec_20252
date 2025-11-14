package br.edu.ibmec.universidade.dto;

import lombok.Data;

@Data
public class DisciplinaDTO {
    private Long id;
    private int codigo;
    private String nome;
    private int cursoId;
}



