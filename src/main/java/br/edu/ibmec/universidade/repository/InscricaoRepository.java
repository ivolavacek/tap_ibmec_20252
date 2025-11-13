package main.java.br.edu.ibmec.universidade.repository;

import br.edu.ibmec.universidade.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
}
