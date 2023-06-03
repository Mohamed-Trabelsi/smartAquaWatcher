package com.example.projectapi.Service;

import com.example.projectapi.Entity.Barage;
import com.example.projectapi.Entity.Noeud;
import com.example.projectapi.Entity.User;
import com.example.projectapi.Repository.BarageRepository;
import com.example.projectapi.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class ServiceNode implements IServiceNode {

    @Autowired
    BarageRepository barageRepository;



    @Override
    public ResponseEntity<Object> getNodesByBarage(Long idBarage) {

    Barage barage = barageRepository.findById(idBarage).get();

    Set<Noeud> nodes = barage.getNoeuds();
    if(nodes.isEmpty()){
        Map<String,Object> response = new HashMap<>();
        response.put("value",HttpStatus.NOT_FOUND.value());
        response.put("status",HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(response);
    }else {
        Map<String,Object> response = new HashMap<>();
        response.put("Node",nodes);
        response.put("value",HttpStatus.OK.value());
        response.put("status",HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}

    @Override
    public ResponseEntity<Object> getNumNodeByBarrage(Long idBarage) {
        Long numNode = barageRepository.findById(idBarage).get().getNoeuds().stream().count();
        Map<String,Object> response = new HashMap<>();
        response.put("numnode",numNode);
        response.put("value",HttpStatus.OK.value());
        response.put("status",HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
