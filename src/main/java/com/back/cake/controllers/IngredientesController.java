package com.back.cake.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.cake.DTOS.IngredientesDTO;
import com.back.cake.models.IngredientesModel;
import com.back.cake.repositories.IngredientesRepository;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("crud")
public class IngredientesController {
    Dotenv dotenv = Dotenv.load();
    String host= "jdbc:mysql://mysqlfirstservice-primeiro-projetojava.i.aivencloud.com:24844/cakereceitas";
    String uname= dotenv.get("USER_NAME");
    String upass= dotenv.get("PASSWORD_NAME");
    @Autowired
    private IngredientesRepository repository;

    @GetMapping("/Ingredientes")
    public ResponseEntity<?> getMethodName() {
        List<IngredientesModel> IngredientesModels = repository.findAll();
        return ResponseEntity.status(200).body(IngredientesModels);
    }


    @GetMapping("/Ingredientes/{id_Cake}")
    public ResponseEntity<?> getMethodById(@PathVariable Integer id_Cake) throws Exception {
      
  

        Connection con =  DriverManager.getConnection( host, uname, upass );
        String sql ="SELECT * FROM ingredientes  i  WHERE id_Cake = ?";
        PreparedStatement stmt =    con.prepareStatement(sql);
        stmt.setInt(1, id_Cake); // Primeiro parâmetro
        List<IngredientesModel> lstIng = new ArrayList<>();
        

        ResultSet rs =  stmt.executeQuery();
        while (rs.next()) {
            IngredientesModel  ingredientesModel = new IngredientesModel(); 
            ingredientesModel.setId_Ingrediente(rs.getInt("id_Ingrediente"));
            ingredientesModel.setNomeIngrediente(rs.getString("nome_Ingrediente"));
            ingredientesModel.setQuantidade(rs.getString("nr_qtd"));
            ingredientesModel.setCakeId(rs.getInt("id_cake"));
            lstIng.add(ingredientesModel);
        }

        
        
        return ResponseEntity.status(200).body(lstIng);
    }


    @PostMapping("ingredientes")
    public ResponseEntity<?> postMethodById(@RequestBody IngredientesDTO data) throws Exception {
        

     

        Connection con =  DriverManager.getConnection( host, uname, upass );
        String sql ="INSERT INTO ingredientes(nome_Ingrediente,nr_Qtd,id_Cake) VALUES (?,?,?)";
        PreparedStatement stmt =    con.prepareStatement(sql);
        System.out.println(data);
        stmt.setString(1, data.nome_Ingrediente());
        stmt.setString(2, data.nr_Qtd());
        stmt.setInt(3, data.Id_Cake());

        stmt.executeUpdate();
         // Primeiro parâmetro
        // List<IngredientesModel> lstIng = new ArrayList<>();
        

        // ResultSet rs =  stmt.executeQuery();
        // while (rs.next()) {
        //     IngredientesModel  ingredientesModel = new IngredientesModel(); 
        //     ingredientesModel.setId_Ingrediente(rs.getInt("id_Ingrediente"));
        //     ingredientesModel.setNomeIngrediente(rs.getString("nome_Ingrediente"));
        //     ingredientesModel.setQuantidade(rs.getInt("nr_qtd"));
        //     ingredientesModel.setCakeId(rs.getInt("id_cake"));
        //     lstIng.add(ingredientesModel);
        // }
        return ResponseEntity.status(200).body(data);
    }
  

    
}
