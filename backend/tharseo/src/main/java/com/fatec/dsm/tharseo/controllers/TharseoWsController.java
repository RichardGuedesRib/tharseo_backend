package com.fatec.dsm.tharseo.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TharseoWsController {
    @MessageMapping("/klines")
    @SendTo("/topic/klines")
    public String sendMsg(){
        return "teste de msg";
    }
}
