package fr.steve.fresh_api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import fr.steve.fresh_api.model.entity.Greeting;
import fr.steve.fresh_api.model.entity.HelloMessage;

@Controller
public class GreetingController {


  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  @CrossOrigin(origins = "*")
  public Greeting greeting(HelloMessage message) throws Exception {
    return new Greeting("Hello "+message.getName());
  }
}