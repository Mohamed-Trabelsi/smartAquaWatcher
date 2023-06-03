package com.example.projectapi.Service;

import org.springframework.http.ResponseEntity;


public interface IServiceBarage {

    ResponseEntity<Object>  getAllBarage();
    ResponseEntity<Object>  getBarageByUser(Long idUser);
    ResponseEntity<Object>  getAllUnaffectedBarage();

}
