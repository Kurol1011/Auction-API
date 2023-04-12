package kz.kurol.auctionapi.controllers;

import kz.kurol.auctionapi.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorld {
   @GetMapping("/hello")
    public ResponseEntity<String> sayHi(){
        return ResponseEntity.ok("Hello from rest api");
    }

    @GetMapping("/bye")
    public ResponseEntity<String> sayBye(){
        return ResponseEntity.ok("Goodbye from rest api");

    }


}
