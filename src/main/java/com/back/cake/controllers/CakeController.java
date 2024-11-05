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
import com.back.cake.models.CakeModel;
import com.back.cake.models.IngredientesModel;
import com.back.cake.repositories.CakeRepository;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController

@RequestMapping("crud")
public class CakeController {
    Dotenv dotenv = Dotenv.load();
    String host= "jdbc:mysql://mysqlfirstservice-primeiro-projetojava.i.aivencloud.com:24844/cakereceitas";

        String uname= dotenv.get("USER_NAME");
    String upass =dotenv.get("PASSWORD_NAME");
	@Autowired
	private CakeRepository repository;

	//Puxa todos os registros de receitas no banco
@GetMapping("/getCake")
public ResponseEntity<?> getCake() {
    List<CakeModel> cakeModels = repository.findAll();
    List<CakeDTOTransferer> cakeDTOTransferer = new ArrayList<>();


    for (CakeModel cakeModel : cakeModels) {
        CakeDTOTransferer cakeDTOTransferers = new CakeDTOTransferer();
        cakeDTOTransferers.setId_Caker(cakeModel.getId_Cake());
        cakeDTOTransferers.setNomeReceita(cakeModel.getNomeReceita());
        cakeDTOTransferers.setDescricao(cakeModel.getDescricao());

        // Converte a imagem para Base64
        String imageName = cakeModel.getImagemReceita();
        String filePath = uploadDirectory + imageName;

        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            cakeDTOTransferers.setImagemReceita(base64Image);
        } catch (IOException e) {
            cakeDTOTransferers.setImagemReceita(null); // ou um valor padrão
        }

        cakeDTOTransferer.add(cakeDTOTransferers);
    }

    return ResponseEntity.ok().body(cakeDTOTransferer);
}

public String convertImageToBase64(String pimageName) throws Exception{
    String filePath = uploadDirectory + pimageName;

    try {
        byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
        // String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return  Base64.getEncoder().encodeToString(imageBytes);
       
    } catch (IOException e) {
        return  "erro ao converter nossa imagem para imagem64 java:96";
    }
    
}



// @GetMapping("/getCake/{id}")
// public ResponseEntity<?> getCakeById(@PathVariable Integer id) {
//     CakeModel cakeModel =  repository.findById(id).get();
//     // List<CakeDTOTransferer> cakeDTOTransferer = new ArrayList<>();


    
//         CakeDTOTransferer cakeDTOTransferer = new CakeDTOTransferer();
//         cakeDTOTransferer.setId_Caker(cakeModel.getId_Cake());
//         cakeDTOTransferer.setNomeReceita(cakeModel.getNomeReceita());
//         cakeDTOTransferer.setDescricao(cakeModel.getDescricao());
//         cakeDTOTransferer.setIngredients(cakeModel.ListgetIngredientes());

//         // Converte a imagem para Base64
//         String imageName = cakeModel.getImagemReceita();
//         String filePath = uploadDirectory + imageName;

//         try {
//             byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
//             String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//             cakeDTOTransferer.setImagemReceita(base64Image);
//         } catch (IOException e) {
//             cakeDTOTransferer.setImagemReceita(null); // ou um valor padrão
//         }

//         // cakeDTOTransferer.add(cakeDTOTransferers);
    

//     return ResponseEntity.ok().body(cakeDTOTransferer);
// }



    @GetMapping("/getCake/{id}")
    public ResponseEntity<?> getMethodById(@PathVariable Integer id) throws Exception {
        // CakeModel cakeModel = new CakeModel();

        
        List<RevenuesDTO> revenues = new ArrayList<RevenuesDTO>();
      


        Connection con =  DriverManager.getConnection( host, uname, upass );
        String sql ="select * from tbcake c " + //
                        " inner join ingredientes i on c.id_Cake = i.id_Cake "+  //
                        " where 1=1" + //
                        " and c.id_Cake = ?";
        PreparedStatement stmt =    con.prepareStatement(sql);
        stmt.setInt(1, id); // Primeiro parâmetro
        // List<IngredientesModel> lstIng = new ArrayList<>();
        

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

	
	
    // @PostMapping("/postCake")
    // public ResponseEntity<?> postCake(
    // @RequestParam("nomeReceita") String nomeReceita,
    // @RequestParam("descricao") String descricao,
    // @RequestParam("imagemReceita") MultipartFile imagemReceita) {

    // CakeModel cakeModel = new CakeModel();
    // cakeModel.setNomeReceita(nomeReceita);
    // cakeModel.setDescricao(descricao);

    // try {
    //     // Limita o tamanho máximo da imagem, por exemplo, 2MB
    //     if (imagemReceita.getSize() > 2 * 1024 * 1024) {
    //         return ResponseEntity.status(400).body("A imagem deve ter no máximo 2MB.");
    //     }

    //     byte[] bytes = imagemReceita.getBytes();
    //     BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

    //     // Verifica se a imagem foi lida corretamente
    //     if (image == null) {
    //         return ResponseEntity.status(400).body("Imagem inválida.");
    //     }

    //     // Detectar o tipo de imagem
    //     String contentType = imagemReceita.getContentType();
    //     String formatName = "png"; // Default para PNG

    //     if (contentType != null) {
    //         if (contentType.equals("image/jpeg")) {
    //             formatName = "jpeg"; // Usar jpeg se for um arquivo JPEG
    //         } else if (contentType.equals("image/gif")) {
    //             formatName = "gif"; // Usar gif se for um arquivo GIF
    //         }
    //     }

    //     // Compacta a imagem
    //     ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //     ImageIO.write(image, formatName, baos);
    //     String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
    //     cakeModel.setImagemReceita(base64Image);

    //     // (Validação de campos e salvamento)
    //     if (nomeReceita != null && descricao != null && base64Image != null) {
    //         if (!nomeReceita.isEmpty() && !descricao.isEmpty()) {
    //             repository.save(cakeModel);
    //             return ResponseEntity.ok().body(cakeModel);
    //         } else {
    //             return ResponseEntity.ok().body("Não insira valores vazios");
    //         }
    //     } else {
    //         return ResponseEntity.ok().body("Atente-se: Nenhum campo pode estar vazio!");
    //     }
    // } catch (IOException e) {
    //     return ResponseEntity.status(500).body("Erro ao processar a imagem: " + e.getMessage());
    // }
    // }
    private final String uploadDirectory = "uploads/";

    @PostMapping("/postCake")
    public ResponseEntity<?> postCake(@ModelAttribute CakeDTO CakeDTO) throws Exception  {

          
            CakeModel cakeModel = new CakeModel();
            cakeModel.setNomeReceita(CakeDTO.nomeReceita());
            cakeModel.setDescricao(CakeDTO.descricao());

        
            MultipartFile image = CakeDTO.imagemReceita(); 
            long maxFileSizeInKB = 20; 
            

            // if (image.getSize() > maxFileSizeInKB * 1024) {
            //     return ResponseEntity.status(500).body("Erro ao fazer upload do bolo: GIGANTE IMAGEM ");
            // } 

            // if (image == null || image.isEmpty()) {
            //     return ResponseEntity.badRequest().body("Imagem não fornecida.");
            // }
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
	public  ResponseEntity<?> putCake(@PathVariable int id_Cake, @RequestBody CakeDTO data) {
		CakeModel cakeModel = repository.findById(id_Cake).get();
		cakeModel.updateDTO(data);
		repository.save(cakeModel);
		
		
		return ResponseEntity.status(200).body("Modificado com sucesso!");
	}

	@DeleteMapping("deleteCake/{id_Cake}")
	public ResponseEntity<?> deleteCake(@PathVariable int id_Cake) {

		repository.deleteById(id_Cake);

		return ResponseEntity.ok("receita deletada");

	}

	@DeleteMapping("/deleteCakes")
	public ResponseEntity<?> deleteCakes() {

		repository.deleteAll();

		return ResponseEntity.ok("receita deletada");

	}

}

