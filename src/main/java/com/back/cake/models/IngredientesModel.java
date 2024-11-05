package com.back.cake.models;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;


@Entity
@Table(name ="ingredientes")
@EqualsAndHashCode(of = "id_Ingrediente")
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_Cake")
public class IngredientesModel {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_Ingrediente")
    private Integer id_Ingrediente;

    @Column(name="nomeIngrediente")
    private String nomeIngrediente;

    // @Column(name ="nr_Qtd")
    // private Integer quantidade;
        @Column(name ="nr_Qtd")
    private String quantidade;

    @ManyToOne
    @JoinColumn(name="id_Cake",nullable = false)
    @JsonBackReference
    private CakeModel cakeModel;

    @Transient 
    private Integer cakeId;

    
    @PostLoad
    private void populateCakeId() {
        this.cakeId = cakeModel != null ? cakeModel.getId_Cake() : null;
    }


    public IngredientesModel() {
    }

    public IngredientesModel(Integer id_Ingrediente, String nomeIngrediente) {
        this.id_Ingrediente = id_Ingrediente;
        this.nomeIngrediente = nomeIngrediente;
    }

    public Integer getId_Ingrediente() {
        return id_Ingrediente;
    }

    public void setId_Ingrediente(Integer id_Ingrediente) {
        this.id_Ingrediente = id_Ingrediente;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente){
        this.nomeIngrediente = nomeIngrediente;
    }





    public CakeModel getCakeModel() {
        return cakeModel;
    }

    public void setCakeModel(CakeModel cakeModel) {
        this.cakeModel = cakeModel;
    }


    public Integer getCakeId() {
        return cakeId;
    }


    public void setCakeId(Integer cakeId) {
        this.cakeId = cakeId;
    }


    public String getQuantidade() {
        return quantidade;
    }


    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }


    




    

    

}
