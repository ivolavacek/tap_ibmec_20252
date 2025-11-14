package br.edu.ibmec.universidade.service;

import br.edu.ibmec.universidade.dto.AlunoDTO;
import br.edu.ibmec.universidade.entity.*;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.universidade.repository.AlunoRepository;
import br.edu.ibmec.universidade.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public AlunoDTO buscarAluno(int matricula) throws DaoException {
        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new DaoException("Aluno não encontrado"));
        return toDTO(aluno);
    }

    public double calcularMensalidade(int matricula, double precoPorDisciplina) throws ServiceException {
        try {
            Aluno aluno = alunoRepository.findById(matricula)
                    .orElseThrow(() -> new ServiceException("Aluno não encontrado"));

            int quantidade = aluno.getInscricoes().size();
            return quantidade * precoPorDisciplina;

        } catch (Exception e) {
            throw new ServiceException("Erro ao calcular mensalidade", e);
        }
    }

    public Collection<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public void cadastrarAluno(AlunoDTO alunoDTO) throws ServiceException, DaoException {
        validarAlunoDTO(alunoDTO);

        Curso curso = cursoRepository.findById(alunoDTO.getCurso())
                .orElseThrow(() -> new DaoException("Curso não encontrado"));

        Aluno aluno = new Aluno(
                alunoDTO.getMatricula(),
                alunoDTO.getNome(),
                getData(alunoDTO.getDtNascimento()),
                alunoDTO.isMatriculaAtiva(),
                EstadoCivil.solteiro, // ou parsear do DTO
                curso,
                alunoDTO.getTelefones()
        );

        alunoRepository.save(aluno);
    }

    public void alterarAluno(AlunoDTO alunoDTO) throws ServiceException, DaoException {
        validarAlunoDTO(alunoDTO);

        Curso curso = cursoRepository.findById(alunoDTO.getCurso())
                .orElseThrow(() -> new DaoException("Curso não encontrado"));

        Aluno aluno = new Aluno(
                alunoDTO.getMatricula(),
                alunoDTO.getNome(),
                getData(alunoDTO.getDtNascimento()),
                alunoDTO.isMatriculaAtiva(),
                EstadoCivil.solteiro,
                curso,
                alunoDTO.getTelefones()
        );

        alunoRepository.save(aluno);
    }

    public void removerAluno(int matricula) throws DaoException {
        if (!alunoRepository.existsById(matricula)) {
            throw new DaoException("Aluno não encontrado");
        }
        alunoRepository.deleteById(matricula);
    }

    // --------- Helpers ---------

    private void validarAlunoDTO(AlunoDTO dto) throws ServiceException {
        if (dto.getMatricula() < 1 || dto.getMatricula() > 99) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if (dto.getNome() == null || dto.getNome().isEmpty() || dto.getNome().length() > 20) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }
    }

    private AlunoDTO toDTO(Aluno a) {
        AlunoDTO dto = new AlunoDTO();
        dto.setMatricula(a.getMatricula());
        dto.setNome(a.getNome());
        String dataFormatada = formatarData(a.getDataNascimento());
        dto.setDtNascimento(dataFormatada);
        dto.setIdade(AlunoDTO.getIdadeConvertida(dataFormatada));
        dto.setMatriculaAtiva(a.isMatriculaAtiva());
        dto.setEstadoCivilDTO(null); // conversão futura
        dto.setTelefones(a.getTelefones());
        dto.setCurso(a.getCurso() != null ? a.getCurso().getCodigo() : 0);
        return dto;
    }

    private String formatarData(DataNascimento dn) {
        if (dn == null) return null;
        return String.format("%02d/%02d/%04d", dn.getDia(), dn.getMes(), dn.getAno());
    }

    public static DataNascimento getData(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = sdf.parse(data);
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(d);
            return new DataNascimento(
                    cal.get(java.util.Calendar.DAY_OF_MONTH),
                    cal.get(java.util.Calendar.MONTH) + 1,
                    cal.get(java.util.Calendar.YEAR)
            );
        } catch (Exception e) {
            System.out.println("Erro conversão da data: " + e.getMessage());
            return null;
        }
    }
}
