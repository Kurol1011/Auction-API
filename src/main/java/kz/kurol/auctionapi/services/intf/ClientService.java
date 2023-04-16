package kz.kurol.auctionapi.services.intf;

import kz.kurol.auctionapi.models.Client;

import java.util.List;
import java.util.Optional;


public interface ClientService {
    public List<Client> getAll();
    public Client getByEmail(String email);
    public Optional<Client> getById(long id);
    public void deleteById(long id);
    public void update(long id, Client updatedClient);
    public void save(Client client);
    // public boolean isEqualPassword(String password, String confirmPassword); // todo should be implemented and uncommented
    // public Client convertToClient(ClientDTO clientDTO); // todo should be implemented and uncommented
    // public ClientDTO convertToClientDTO(Client client); // todo should be implemented and uncommented
    public Client getCurrentClient();





}
