package main.java.br.edu.ibmec.universidade.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float avaliacao1;
    private float avaliacao2;
    private float media;
    private int numFaltas;
    private String situacao;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Turma turma;
}
