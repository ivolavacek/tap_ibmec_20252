package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int codigo;
    private String nome;

    @ManyToOne
    @JsonBackReference(value = "curso-disciplina")
    private Curso curso;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Turma> turmas = new ArrayList<>();

    public void addTurma(Turma turma) {
        turmas.add(turma);
        turma.setDisciplina(this);
    }

    public void removeTurma(Turma turma) {
        turmas.remove(turma);
        turma.setDisciplina(null);
    }
}
