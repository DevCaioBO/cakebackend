package com.back.cake.DTOS;

public class RevenuesDTO {
    private Integer id_Cake;
    private String descricao;
    private String imagem_receita;
    private String nome_receita;
    private Integer id_Ingrediente;
    private String nome_Ingrediente;
    private String nr_Qtd;
    
    public Integer getId_Cake() {
        return id_Cake;
    }
    public void setId_Cake(Integer id_Cake) {
        this.id_Cake = id_Cake;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getImagem_receita() {
        return imagem_receita;
    }
    public void setImagem_receita(String imagem_receita) {
        this.imagem_receita = imagem_receita;
    }
    public String getNome_receita() {
        return nome_receita;
    }
    public void setNome_receita(String nome_receita) {
        this.nome_receita = nome_receita;
    }
    public Integer getId_Ingrediente() {
        return id_Ingrediente;
    }
    public void setId_Ingrediente(Integer id_Ingrediente) {
        this.id_Ingrediente = id_Ingrediente;
    }
    public String getNome_Ingrediente() {
        return nome_Ingrediente;
    }
    public void setNome_Ingrediente(String nome_Ingrediente) {
        this.nome_Ingrediente = nome_Ingrediente;
    }
    public String getNr_Qtd() {
        return nr_Qtd;
    }
    public void setNr_Qtd(String nr_Qtd) {
        this.nr_Qtd = nr_Qtd;
    }

    
}
