package com.example.projectapi.Controller;

import com.example.projectapi.Service.IServiceBarage;
import com.example.projectapi.Service.IServiceNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ControllerNode {

    @Autowired
    IServiceNode iServiceNode;

    @GetMapping("/GetNodeByBarage{idBarage}")
    public ResponseEntity<Object> getGrandeur(@PathVariable("idBarage") Long idBarage){
        return iServiceNode.getNodesByBarage(idBarage);
    }

    @GetMapping("/GetNumNode{idBarage}")
    public ResponseEntity<Object> getNumNode(@PathVariable("idBarage") Long idBarage){
        return iServiceNode.getNumNodeByBarrage(idBarage);
    }



}
