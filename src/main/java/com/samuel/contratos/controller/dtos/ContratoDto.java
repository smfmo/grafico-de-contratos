package com.samuel.contratos.controller.dtos;

import com.samuel.contratos.model.Enum.TiposDeContrato;

import java.time.LocalDate;
import java.util.UUID;

public record ContratoDto(
        String agencia,
        String sr,
        String valorBruto,
        String valorLiquido,
        String prestacao,
        String parcelas,
        String prestamista,
        String iof,
        String jurosAcerto,
        LocalDate data,
        UUID clienteId,
        TiposDeContrato tiposDeContrato,
        String numeroDoContrato) {

}
