package kz.kurol.auctionapi.controllers;

import kz.kurol.auctionapi.security.AuthenticationRequest;
import kz.kurol.auctionapi.security.AuthenticationResponse;
import kz.kurol.auctionapi.security.AuthenticationService;
import kz.kurol.auctionapi.security.RegisterRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
            return ResponseEntity.ok(authenticationService.authenticate(request));
    }



}

