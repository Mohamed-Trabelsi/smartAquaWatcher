package com.example.projectapi.Service;

import com.example.projectapi.Entity.Barage;
import com.example.projectapi.Entity.Grandeur;
import com.example.projectapi.Entity.Noeud;
import com.example.projectapi.Repository.BarageRepository;
import com.example.projectapi.Repository.GrandeurRepository;
import com.example.projectapi.Repository.NodeRepository;
import com.example.projectapi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceGrandeur implements IServiceGrandeur{

    @Autowired
    GrandeurRepository grandeurRepository;

    @Autowired
    BarageRepository barageRepository;

    @Autowired
    NodeRepository nodeRepository;


    @Override
    public Map<String, Object> getGrandeur() {
        Map<String,Object> grandeur = new HashMap<>();
        grandeur.put("value",grandeurRepository.findFirstByOrderByIdDesc());
        grandeur.put("status", HttpStatus.OK);
        grandeur.put("code",HttpStatus.OK.value());
        return grandeur;
    }

    @Override
    public ResponseEntity<Object> getAllTemperature() {

        List<Grandeur> list_temp=grandeurRepository.findAll();

        List<Map<String,Object>> temps = new ArrayList<>();

        for(Grandeur g : list_temp){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getTemp());
            tempMap.put("heure",g.getHeure());
            temps.add(tempMap);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("values",temps);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getLast5Temperature() {
        List<Grandeur> list_grandeur=grandeurRepository.findAllByOrderByIdDesc().stream().limit(5).collect(Collectors.toList());

        List<Map<String,Object>> Temperatures = new ArrayList<>();

        for(Grandeur g : list_grandeur){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getTemp());
            tempMap.put("heure",g.getHeure());
            Temperatures.add(tempMap);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("values",Temperatures);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getLast5TemperaturebyNode(Long idNode) {

        Noeud node = nodeRepository.findById(idNode).get();
        List<Grandeur> grandeurnode = node.getGrandeurs().stream().collect(Collectors.toList());
        List<Map<String,Object>> temp = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();

        if(grandeurnode.size()!=0){

        List<Grandeur> sortedGrandeurs = grandeurnode.stream().limit(5)
                .sorted(Comparator.comparing(Grandeur::getHeure).reversed())
                .collect(Collectors.toList());

        // Loop through sorted list and add values to temp
        for(Grandeur g : sortedGrandeurs){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getTemp());
            tempMap.put("heure",g.getHeure());
            temp.add(tempMap);
        }
            response.put("temperature",temp);
            response.put("value",HttpStatus.OK);
            response.put("status",HttpStatus.OK.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getLast5HumiditybyNode(Long idNode) {
        Noeud node = nodeRepository.findById(idNode).get();
        Set<Grandeur> grandeurnode = node.getGrandeurs();
        List<Map<String,Object>> humidities = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();


        if(grandeurnode.size()!=0){

            List<Grandeur> sortedGrandeurs = grandeurnode.stream().limit(5)
                    .sorted(Comparator.comparing(Grandeur::getHeure,Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            // Loop through sorted list and add values to temp
            for(Grandeur g : sortedGrandeurs){
                Map<String,Object> tempMap = new HashMap<>();
                tempMap.put("value",g.getHumidite());
                tempMap.put("heure",g.getHeure());
                humidities.add(tempMap);
            }
            response.put("humidity",humidities);
            response.put("value",HttpStatus.OK);
            response.put("status",HttpStatus.OK.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(response);    }

    @Override
    public ResponseEntity<Object> getLast5TurbiditybyNode(Long idNode) {
        Noeud node = nodeRepository.findById(idNode).get();
        Set<Grandeur> grandeurnode = node.getGrandeurs();
        List<Map<String,Object>> turbidities = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();

        if(grandeurnode.size()!=0){

            List<Grandeur> sortedGrandeurs = grandeurnode.stream().limit(5)
                    .sorted(Comparator.comparing(Grandeur::getHeure,Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            // Loop through sorted list and add values to temp
            for(Grandeur g : sortedGrandeurs){
                Map<String,Object> tempMap = new HashMap<>();
                tempMap.put("value",g.getTurbidite());
                tempMap.put("heure",g.getHeure());
                turbidities.add(tempMap);
            }
            response.put("turbidity",turbidities);
            response.put("value",HttpStatus.OK);
            response.put("status",HttpStatus.OK.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getLast5PhbyNode(Long idNode) {

        Noeud node = nodeRepository.findById(idNode).get();
        Set<Grandeur> grandeurnode = node.getGrandeurs();
        List<Map<String,Object>> phlist = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();


        if(grandeurnode.size()!=0){

            List<Grandeur> sortedGrandeurs = grandeurnode.stream().limit(5)
                    .sorted(Comparator.comparing(Grandeur::getHeure,Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            // Loop through sorted list and add values to temp
            for(Grandeur g : sortedGrandeurs){
                Map<String,Object> tempMap = new HashMap<>();
                tempMap.put("value",g.getPh());
                tempMap.put("heure",g.getHeure());
                phlist.add(tempMap);
            }
            response.put("ph",phlist);
            response.put("value",HttpStatus.OK);
            response.put("status",HttpStatus.OK.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getAllPh() {
        List<Grandeur> list_grandeur=grandeurRepository.findAll();

        List<Map<String,Object>> phs = new ArrayList<>();

        for(Grandeur g : list_grandeur){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getPh());
            tempMap.put("heure",g.getHeure());
            phs.add(tempMap);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("values",phs);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getLast5Ph() {
        List<Grandeur> list_grandeur=grandeurRepository.findAllByOrderByIdDesc().stream().limit(5).collect(Collectors.toList());

        List<Map<String,Object>> Ph = new ArrayList<>();

        for(Grandeur g : list_grandeur){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getPh());
            tempMap.put("heure",g.getHeure());
            Ph.add(tempMap);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("values",Ph);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getAllHumidity() {
        List<Grandeur> list_grandeur=grandeurRepository.findAll();

        List<Map<String,Object>> humidities = new ArrayList<>();

        for(Grandeur g : list_grandeur){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getHumidite());
            tempMap.put("heure",g.getHeure());
            humidities.add(tempMap);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("values",humidities);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getLast5Humidity() {

        List<Grandeur> list_grandeur=grandeurRepository.findAllByOrderByIdDesc().stream().limit(5).collect(Collectors.toList());
        List<Map<String,Object>> humidities = new ArrayList<>();

        for(Grandeur g : list_grandeur){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getHumidite());
            tempMap.put("heure",g.getHeure());
            humidities.add(tempMap);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("values",humidities);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getAllTurbidity() {
        List<Grandeur> list_grandeur=grandeurRepository.findAll();

        List<Map<String,Object>> turbidities = new ArrayList<>();

        for(Grandeur g : list_grandeur){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getTurbidite());
            tempMap.put("heure",g.getHeure());
            turbidities.add(tempMap);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("values",turbidities);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);    }

    @Override
    public ResponseEntity<Object> getLast5Turbidity() {
        List<Grandeur> list_grandeur=grandeurRepository.findAllByOrderByIdDesc().stream().limit(5).collect(Collectors.toList());

        List<Map<String,Object>> turbidities = new ArrayList<>();

        for(Grandeur g : list_grandeur){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("value",g.getTurbidite());
            tempMap.put("heure",g.getHeure());
            turbidities.add(tempMap);
        }

        Map<String,Object> response = new HashMap<>();
        response.put("values",turbidities);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getGrandeurByNode(Long idNode) {

        Noeud node = nodeRepository.findById(idNode).get();
        Grandeur g= node.getGrandeurs().stream().max(Comparator.comparing(Grandeur::getId)).orElse(null);

        Map<String,Object> grandeur = new HashMap<>();

        if(g!=null){
           grandeur.put("grandeur",g);
           grandeur.put("status", HttpStatus.OK);
           grandeur.put("value",HttpStatus.OK.value());
           return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(grandeur);
       }else{
            grandeur.put("status", HttpStatus.NOT_FOUND);
            grandeur.put("value",HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(grandeur);

        }



    }


}
