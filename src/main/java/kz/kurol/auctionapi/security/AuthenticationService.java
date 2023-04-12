package kz.kurol.auctionapi.security;

import jakarta.servlet.http.HttpServletResponse;
import kz.kurol.auctionapi.models.Client;
import kz.kurol.auctionapi.models.Role;
import kz.kurol.auctionapi.repositories.ClientRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setSurname(request.getSurname());
        client.setEmail(request.getEmail());
        client.setPass(passwordEncoder.encode(request.getPassword()));
        client.setRole(Role.USER);
        clientRepository.save(client);
        String jwtToken = jwtService.generateToken(client);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        Client client = clientRepository.findByEmail(request.getEmail()).orElseThrow();//todo implement handler exception
        String jwtToken = jwtService.generateToken(client);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return authenticationResponse;
    }
}
