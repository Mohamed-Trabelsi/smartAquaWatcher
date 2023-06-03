package com.example.projectapi.Service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IServiceGrandeur {

    Map<String,Object> getGrandeur();

    ResponseEntity<Object>  getAllTemperature();

    ResponseEntity<Object>  getLast5Temperature();
    ResponseEntity<Object>  getLast5TemperaturebyNode(Long idNode);
    ResponseEntity<Object>  getLast5HumiditybyNode(Long idNode);
    ResponseEntity<Object>  getLast5TurbiditybyNode(Long idNode);
    ResponseEntity<Object>  getLast5PhbyNode(Long idNode);

    ResponseEntity<Object>  getAllPh();
    ResponseEntity<Object>  getLast5Ph();
    ResponseEntity<Object>  getAllHumidity();
    ResponseEntity<Object>  getLast5Humidity();
    ResponseEntity<Object>  getAllTurbidity();
    ResponseEntity<Object>  getLast5Turbidity();
    ResponseEntity<Object>  getGrandeurByNode(Long idNode);







}
