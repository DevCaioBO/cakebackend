package com.back.cake.DTOS;

import org.springframework.web.multipart.MultipartFile;

public record CakeDTO(String nomeReceita, MultipartFile imagemReceita,String descricao) {




    public String nomeReceita() {
        return nomeReceita;
    }

    public MultipartFile imagemReceita() {
        return imagemReceita;
    }

    public String descricao() {
        return descricao;
    }

    

}
