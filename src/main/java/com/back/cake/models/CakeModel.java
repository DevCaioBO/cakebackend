package com.back.cake.models;






import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.back.cake.DTOS.CakeDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name="tbcake")
@Table(name="tbcake")
@EqualsAndHashCode(of = "id_Cake")
@AllArgsConstructor
@NoArgsConstructor
public class CakeModel {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_Cake")
	private int id_Cake;
	
	@Column(name ="nomeReceita")

	private String nomeReceita;
	
	// @Lob 
	// @Column(name="imagemReceita", length=512)
	// private String imagemReceita;
	
	@Column(name="imagemReceita")
	private String imagemReceita;
	
	@Lob
	@Column(name="descricao" ,columnDefinition = "LONGTEXT")
	private String descricao;

	@OneToMany(mappedBy = "cakeModel",cascade = CascadeType.ALL)
	// @JsonIgnore
	
	private List<IngredientesModel> ingredientes;

	// public List<IngredientesModel> getIngredientes(List<IngredientesModel> ingredientes) {
	// 	return ingredientes;
	// }

	public void setIngredientes(List<IngredientesModel> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public List<IngredientesModel>  ListgetIngredientes() {
		return  ingredientes;
	}
	// private final String uploadDirectory = "uploads/";

	public void updateDTO(CakeDTO dto) {
	
		this.nomeReceita = dto.nomeReceita();
		
		this.descricao = dto.descricao();
		
		
		

		


		
	}

	public int getId_Cake() {
		return id_Cake;
	}

	public void setId_Cake(int id_Cake) {
		this.id_Cake = id_Cake;
	}

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






	
	

	
	
}
