package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "professor-turma")
    private List<Turma> turmas = new ArrayList<>();

    public void addTurma(Turma turma) {
        turmas.add(turma);
        turma.setProfessor(this);
    }

    public void removeTurma(Turma turma) {
        turmas.remove(turma);
    }
}
