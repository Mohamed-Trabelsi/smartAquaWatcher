package com.example.projectapi.Service;

import com.example.projectapi.Entity.Barage;
import com.example.projectapi.Entity.User;
import com.example.projectapi.Repository.BarageRepository;
import com.example.projectapi.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ServiceBarage implements IServiceBarage {

    @Autowired
    BarageRepository barageRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public ResponseEntity<Object> getAllBarage() {


        List<Barage> barages = barageRepository.findAll();

        //List<Map<String,Object>> response =new ArrayList<>();


        Map<String,Object> response = new HashMap<>();
        response.put("Barage",barages);
        response.put("value",HttpStatus.OK.value());
        response.put("value",HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getBarageByUser(Long idUser) {

        User user = userRepository.findById(idUser).get();

        Set<Barage> barages = user.getBarages();

        Map<String,Object> response = new HashMap<>();

        response.put("Barage",barages);
        response.put("value",HttpStatus.OK.value());
        response.put("value",HttpStatus.OK.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @Override
    public ResponseEntity<Object> getAllUnaffectedBarage() {

        Map<String,Object> response = new HashMap<>();

        List<Barage> barages = barageRepository.findBaragesWithNoUsers();
        if(barages.size()>0){
            response.put("barages",barages);
            response.put("value",HttpStatus.OK.value());
            response.put("status",HttpStatus.OK.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
        }else if(barages.size()==0){
            response.put("barages",barages);
            response.put("value",HttpStatus.NOT_FOUND.value());
            response.put("status",HttpStatus.NOT_FOUND.getReasonPhrase());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);

    }

}
