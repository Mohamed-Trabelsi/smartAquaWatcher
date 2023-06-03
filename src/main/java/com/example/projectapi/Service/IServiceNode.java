package com.example.projectapi.Service;

import org.springframework.http.ResponseEntity;

public interface IServiceNode {

     ResponseEntity<Object>  getNodesByBarage(Long idBarage);
     ResponseEntity<Object> getNumNodeByBarrage(Long idBarage);

}







