package br.edu.ibmec.universidade.dto;

public enum EstadoCivilDTO {
	SOLTEIRO,
	CASADO,
	DIVORCIADO,
	VIUVO;

	/**
	 * Converte um EstadoCivil (entity) → EstadoCivilDTO.
	 * Assume que os nomes são iguais entre enums.
	 */
	public static EstadoCivilDTO fromEntity(br.edu.ibmec.universidade.entity.EstadoCivil estado) {
		if (estado == null) return null;
		return EstadoCivilDTO.valueOf(estado.name().toUpperCase());
	}

	/**
	 * Converte DTO → entity EstadoCivil.
	 */
	public br.edu.ibmec.universidade.entity.EstadoCivil toEntity() {
		return br.edu.ibmec.universidade.entity.EstadoCivil.valueOf(this.name().toLowerCase());
	}
}
