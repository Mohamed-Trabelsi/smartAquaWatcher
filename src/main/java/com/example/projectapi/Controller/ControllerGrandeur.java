package com.example.projectapi.Controller;

import com.example.projectapi.Entity.User;
import com.example.projectapi.Repository.GrandeurRepository;
import com.example.projectapi.Service.IServiceGrandeur;
import com.example.projectapi.Service.IServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ControllerGrandeur {

    @Autowired
    IServiceGrandeur iServiceGrandeur;

    @GetMapping("/GetAllTemperature")
    public  ResponseEntity<Object> getTemperature(){
        return iServiceGrandeur.getAllTemperature();
    }
    @GetMapping("/GetLast5Temperature")
    public  ResponseEntity<Object> getLast5Temperature(){
        return iServiceGrandeur.getLast5Temperature();
    }

    @GetMapping("/GetLast5TemperaturebyNode={id}")
    public  ResponseEntity<Object> getLast5TemperaturebyIdNode(@PathVariable("id") Long idNode){
        return iServiceGrandeur.getLast5TemperaturebyNode(idNode);
    }
    @GetMapping("/GetLast5HumiditybyNode={id}")
    public  ResponseEntity<Object> getLast5HumiditybyIdNode(@PathVariable("id") Long idNode){
        return iServiceGrandeur.getLast5HumiditybyNode(idNode);
    }
    @GetMapping("/GetLast5TurbiditybyNode={id}")
    public  ResponseEntity<Object> getLast5TurbiditybyIdNode(@PathVariable("id") Long idNode){
        return iServiceGrandeur.getLast5TurbiditybyNode(idNode);
    }
    @GetMapping("/GetLast5PhbyNode={id}")
    public  ResponseEntity<Object> getLast5PhbyIdNode(@PathVariable("id") Long idNode){
        return iServiceGrandeur.getLast5PhbyNode(idNode);
    }
    @GetMapping("/GetAllHumidity")
    public  ResponseEntity<Object> getHumidity(){
        return iServiceGrandeur.getAllHumidity();
    }
    @GetMapping("/GetLast5Humidity")
    public  ResponseEntity<Object> getLast5Humidity(){
        return iServiceGrandeur.getLast5Humidity();
    }
    @GetMapping("/GetAllPh")
    public  ResponseEntity<Object> getPh(){
        return iServiceGrandeur.getAllPh();
    }
    @GetMapping("/GetLast5Ph")
    public  ResponseEntity<Object> getLast5Ph(){
        return iServiceGrandeur.getLast5Ph();
    }
    @GetMapping("/GetAllTurbidity")
    public  ResponseEntity<Object> getTurbidity(){
        return iServiceGrandeur.getAllTurbidity();
    }

    @GetMapping("/GetLast5Turbidity")
    public  ResponseEntity<Object> getLast5Turbidity(){
        return iServiceGrandeur.getLast5Turbidity();
    }
    @GetMapping("/GetGrandeur")
    public Map<String, Object> getGrandeur(){
        return iServiceGrandeur.getGrandeur();
    }

    @GetMapping("/GetGrandeurbyNode{idNode}")
    public ResponseEntity<Object> getGrandeur(@PathVariable("idNode") Long idNoeud){
        return iServiceGrandeur.getGrandeurByNode(idNoeud);
    }






}
