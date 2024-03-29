package br.ufrn.imd.utravel.model;

import java.time.LocalDate;
import java.util.List;

public class Viagem extends AbstractModel {
    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<Orcamento> orcamentos;
    private List<ViagemDestino> viagemDestinos;
    private List<ViagemReserva> viagemReservas;

    public Viagem() {
    }

    public Viagem(Integer id, String titulo, LocalDate dataInicio, LocalDate dataFim) {
        super.setId(id);
        this.titulo = titulo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Viagem(int anInt, LocalDate toLocalDate, LocalDate toLocalDate1) {
        super();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public List<Orcamento> getOrcamentos() {
        return orcamentos;
    }

    public void setOrcamentos(List<Orcamento> orcamentos) {
        this.orcamentos = orcamentos;
    }

    public List<ViagemDestino> getViagemDestinos() {
        return viagemDestinos;
    }

    public void setViagemDestinos(List<ViagemDestino> viagemDestinos) {
        this.viagemDestinos = viagemDestinos;
    }

    public List<ViagemReserva> getViagemReservas() {
        return viagemReservas;
    }

    public void setViagemReservas(List<ViagemReserva> viagemReservas) {
        this.viagemReservas = viagemReservas;
    }
}
