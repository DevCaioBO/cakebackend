package com.back.cake.controllers;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.Paths;// Para trabalhar com caminhos de arquivo
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException; // Para tratar exceções de entrada/saída



import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.back.cake.DTOS.CakeDTO;
import com.back.cake.DTOS.CakeDTOTransferer;
import com.back.cake.DTOS.RevenuesDTO;
import com.back.cake.configCake.appConfig;
import com.back.cake.models.CakeModel;
import com.back.cake.models.IngredientesModel;
import com.back.cake.repositories.CakeRepository;


import java.nio.file.Path;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController

@RequestMapping("crud")
public class CakeController {

       
       private final String uname;
       private final String upass;

    // Injeção de dependência do appConfig
    @Autowired
    public CakeController(appConfig config) {
        this.uname = config.getUname();
        this.upass = config.getUpass();
    }

    
    String host = "jdbc:mysql://mysqlfirstservice-primeiro-projetojava.i.aivencloud.com:24844/cakereceitas";
    
   
    
	@Autowired
	private CakeRepository repository;

	
@GetMapping("/getCake")
public ResponseEntity<?> getCake() {
    List<CakeModel> cakeModels = repository.findAll();
    List<CakeDTOTransferer> cakeDTOTransferer = new ArrayList<>();


    for (CakeModel cakeModel : cakeModels) {
        CakeDTOTransferer cakeDTOTransferers = new CakeDTOTransferer();
        cakeDTOTransferers.setId_Caker(cakeModel.getId_Cake());
        cakeDTOTransferers.setNomeReceita(cakeModel.getNomeReceita());
        cakeDTOTransferers.setDescricao(cakeModel.getDescricao());

    
        String imageName = cakeModel.getImagemReceita();
        String filePath = uploadDirectory + imageName;

        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            cakeDTOTransferers.setImagemReceita(base64Image);
        } catch (IOException e) {
            cakeDTOTransferers.setImagemReceita(null); 
        }

        cakeDTOTransferer.add(cakeDTOTransferers);
    }

    return ResponseEntity.ok().body(cakeDTOTransferer);
}

public String convertImageToBase64(String pimageName) throws Exception{
    String filePath = uploadDirectory + pimageName;

    try {
        byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
        return  Base64.getEncoder().encodeToString(imageBytes);
       
    } catch (IOException e) {
        return  "erro ao converter imagem";
    }
    
}

    @GetMapping("/getCake/{id}")
    public ResponseEntity<?> getMethodById(@PathVariable Integer id) throws Exception {
        
        List<RevenuesDTO> revenues = new ArrayList<RevenuesDTO>();

        Connection con =  DriverManager.getConnection( host, uname, upass );
        String sql ="select * from tbcake c " + 
                        " inner join ingredientes i on c.id_Cake = i.id_Cake "+  
                        " where 1=1" + 
                        " and c.id_Cake = ?";
        PreparedStatement stmt =    con.prepareStatement(sql);
        stmt.setInt(1, id); 
        ResultSet rs =  stmt.executeQuery();
 
        while (rs.next()) {
            RevenuesDTO  revenue = new RevenuesDTO(); 
            revenue.setId_Cake(rs.getInt("id_Cake"));
            revenue.setDescricao(rs.getString("descricao"));
            revenue.setImagem_receita(convertImageToBase64(rs.getString("imagem_receita")));
            revenue.setId_Ingrediente(rs.getInt("id_Ingrediente"));
            revenue.setNome_Ingrediente(rs.getString("nome_ingrediente"));
            revenue.setNome_receita(rs.getString("nome_receita"));
            revenue.setNr_Qtd(rs.getString("nr_Qtd"));
            revenues.add(revenue);
        }
        System.out.println(revenues);

        
        
        return ResponseEntity.status(200).body(revenues);
    }
    private final String uploadDirectory = "uploads/";

    @PostMapping("/postCake")
    public ResponseEntity<?> postCake(@ModelAttribute CakeDTO CakeDTO) throws Exception  {
            CakeModel cakeModel = new CakeModel();
            cakeModel.setNomeReceita(CakeDTO.nomeReceita());
            cakeModel.setDescricao(CakeDTO.descricao());

            MultipartFile image = CakeDTO.imagemReceita(); 
            long maxFileSizeInKB = 20; 
            
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = timestamp + "_" + image.getOriginalFilename();
            String filePath = uploadDirectory + fileName;

            try{

            File dir = new File(uploadDirectory);
         
            if (!dir.exists()) {
                dir.mkdirs(); 
            }


            Files.write(Paths.get(filePath), image.getBytes()); 

            cakeModel.setImagemReceita(fileName); // Supondo que você tenha um campo no CakeModel para o caminho da imagem
            repository.save(cakeModel);

            return ResponseEntity.ok("Bolo criado com sucesso!");
        }
         catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao fazer upload do bolo: " + e.getMessage()); 
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro inesperado: " + e.getMessage()); 
        }

}
    


@PutMapping("putCake/{id_Cake}")
public ResponseEntity<?> putCake(@PathVariable Integer id_Cake, @ModelAttribute CakeDTO data) {
    Optional<CakeModel> optionalCake = repository.findById(id_Cake);
    
    if (!optionalCake.isPresent()) {
        return ResponseEntity.status(404).body("Bolo não encontrado.");
    }

    CakeModel cakeModel = optionalCake.get();
    
    MultipartFile image = data.imagemReceita();
    String oldFileName = cakeModel.getImagemReceita();
  

    long maxFileSizeInKB = 40;

    if (image != null && !image.isEmpty()) {
        
        if (oldFileName != null && !oldFileName.isEmpty()) {
            File oldFile = new File(uploadDirectory + oldFileName);
            if (oldFile.exists()) {
                oldFile.delete(); 
            }
        }

  
    String timestamp = String.valueOf(System.currentTimeMillis());
    String fileName = timestamp + "_" + image.getOriginalFilename();
    String filePath = uploadDirectory + fileName;

    try {
        File dir = new File(uploadDirectory);
        if (!dir.exists()) {
            dir.mkdirs(); 
        }

        Files.write(Paths.get(filePath), image.getBytes());
       
        

        cakeModel.setImagemReceita(fileName);
       
    } catch (IOException e) {
        return ResponseEntity.status(500).body("Erro ao fazer upload do bolo: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro inesperado: " + e.getMessage());
    }
    }

  
        
        cakeModel.updateDTO(data); 
    
        repository.save(cakeModel);
        return ResponseEntity.ok("Bolo atualizado com sucesso!");


}

	@DeleteMapping("deleteCake/{id_Cake}")
	public ResponseEntity<?> deleteCake(@PathVariable int id_Cake) {
        Optional<CakeModel> cakemodel = repository.findById(id_Cake);



		 if (cakemodel.isPresent()) {
            CakeModel receita = cakemodel.get();
            String fileName = receita.getImagemReceita();  

          
            Path filePath = Paths.get(uploadDirectory + fileName);
            // repository.deleteById(id_Cake);
          
         try {
              
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    
                    repository.deleteById(id_Cake);
                    return ResponseEntity.ok("Arquivo e receita deletados com sucesso!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o arquivo.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receita não encontrada!");
        }
    


	}

	@DeleteMapping("/deleteCakes")
	public ResponseEntity<?> deleteCakes() {

		repository.deleteAll();

		return ResponseEntity.ok("receita deletada");

	}

}



