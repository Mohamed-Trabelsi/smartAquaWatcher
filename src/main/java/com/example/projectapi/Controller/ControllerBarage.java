package com.example.projectapi.Controller;

import com.example.projectapi.Service.IServiceBarage;
import com.example.projectapi.Service.IServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ControllerBarage {

    @Autowired
    IServiceBarage iServiceBarage;
    @Autowired
    IServiceUser iServiceUser;

    @GetMapping("/GetAllBarage")
    public ResponseEntity<Object> getGrandeur(){
        return iServiceBarage.getAllBarage();
    }
    @GetMapping("/GetBaragebyUser{idUser}")
    public ResponseEntity<Object> getGrandeur(@PathVariable("idUser") Long idUser){
        return iServiceBarage.getBarageByUser(idUser);
    }
    @GetMapping("/GetAllUnaffectedBarage")
    public ResponseEntity<Object> getAllUnaffectedBarage(){
        return iServiceBarage.getAllUnaffectedBarage();
    }
    @PutMapping("/SetUserid={idUser}/BarageId={idBarage}")
    public ResponseEntity<Object> getAllUnaffectedBarage(@PathVariable("idUser") Long idUser,@PathVariable("idBarage") Long idBarage){
        return iServiceUser.affectBarrageToUser(idBarage,idUser);
    }
}
