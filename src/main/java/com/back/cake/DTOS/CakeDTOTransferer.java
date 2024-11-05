package com.back.cake.DTOS;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.back.cake.models.IngredientesModel;

public class CakeDTOTransferer {
    private Integer Id_Caker;
    private String nomeReceita;
    private String imagemReceita; 
    private String descricao;
    private List<IngredientesModel> ingredientes;

    

    // Getters e Setters
    public String getNomeReceita() {
        return nomeReceita;
    }

    public void setNomeReceita(String nomeReceita) {
        this.nomeReceita = nomeReceita;
    }

    public String getImagemReceita() {
        return imagemReceita;
    }

    public void setImagemReceita(String imagemReceita) {
        this.imagemReceita = imagemReceita;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId_Caker() {
        return Id_Caker;
    }

    public void setId_Caker(Integer id_Caker) {
        Id_Caker = id_Caker;
    }

    public void setIngredients(List<IngredientesModel> ingredientes){
        this.ingredientes = ingredientes;
    }

    public List<IngredientesModel> getId_Caker(List<IngredientesModel> ingredientes ) {
        return ingredientes;
    }
    
}
