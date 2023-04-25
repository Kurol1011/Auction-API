package kz.kurol.auctionapi.services.impl;

import kz.kurol.auctionapi.dto.ClientDTO;
import kz.kurol.auctionapi.models.BoardItem;
import kz.kurol.auctionapi.models.Client;
import kz.kurol.auctionapi.repositories.ClientRepository;
import kz.kurol.auctionapi.services.intf.BoardItemService;
import kz.kurol.auctionapi.services.intf.ClientService;
import kz.kurol.auctionapi.utils.errors.ClientIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService, UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email).orElseThrow(() -> new ClientIsNotFoundException("Email is not found!"));
    }

    @Override
    public Optional<Client> getById(long id) {
        return clientRepository.findById(id);
    }


    @Override
    public void deleteById(long id) {
        if(getById(id).isPresent()){
            clientRepository.deleteById(id);
        }
        else{
            throw new ClientIsNotFoundException("Client not found!");
        }
    }

    @Override
    public void update(long id, Client updatedClient) {
           Client clientToBeUpdated = clientRepository.findById(id).orElseThrow(()-> new ClientIsNotFoundException("Client not found!"));
           clientToBeUpdated.setName(updatedClient.getName());
           clientToBeUpdated.setSurname(updatedClient.getSurname());
           clientRepository.save(clientToBeUpdated);
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public ClientDTO convertToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPassword(client.getPassword());
        clientDTO.setBalance(client.getBalance());
        return clientDTO;
    }

    @Override
    public Client getCurrentClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Client client = (Client) authentication.getPrincipal();

        return clientRepository.findByEmail(client.getEmail()).orElseThrow(()->new ClientIsNotFoundException("Client not found!"));
    }

    @Override
    public boolean hasClientAuctionItemById(long id) {
        System.out.println(getCurrentClient().getBoardItems().stream().filter(i -> i.getId() == id).findFirst().isPresent());
        return getCurrentClient().getBoardItems().stream().filter(i -> i.getId() == id).findFirst().isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return getByEmail(username);
    }
}
