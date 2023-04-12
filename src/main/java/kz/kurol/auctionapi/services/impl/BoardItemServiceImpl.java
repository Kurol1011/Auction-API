package kz.kurol.auctionapi.services.impl;

import kz.kurol.auctionapi.models.BoardItem;
import kz.kurol.auctionapi.models.Client;
import kz.kurol.auctionapi.models.Item;
import kz.kurol.auctionapi.models.enums.Status;
import kz.kurol.auctionapi.repositories.BoardItemRepository;
import kz.kurol.auctionapi.services.intf.BoardItemService;
import kz.kurol.auctionapi.services.intf.ClientService;
import kz.kurol.auctionapi.services.intf.ItemService;
import kz.kurol.auctionapi.utils.errors.BoardIsNotFoundException;
import kz.kurol.auctionapi.utils.errors.ClientIsNotFoundException;
import kz.kurol.auctionapi.utils.errors.ItemIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardItemServiceImpl implements BoardItemService {

    private final BoardItemRepository boardItemRepository;
    private final ItemService itemService;
    private final ClientService clientService;

    @Autowired
    public BoardItemServiceImpl(BoardItemRepository boardItemRepository, ItemService itemService, ClientService clientService) {
        this.boardItemRepository = boardItemRepository;
        this.itemService = itemService;
        this.clientService = clientService;
    }

    @Override
    public void addItem(long id) {
         BoardItem boardItem = new BoardItem();
         Item item = itemService.getById(id).orElseThrow(() -> new ItemIsNotFoundException("Item not found!"));
         item.setStatus(Status.ACTIVE);
         boardItem.setItem(item);
         boardItemRepository.save(boardItem);
    }

    @Override
    public void removeItem(long boardId, long clientId) {
         BoardItem boardItem = boardItemRepository.findById(boardId).orElseThrow(() -> new BoardIsNotFoundException("Board item not found!"));
         Item item = boardItem.getItem();
         if(item.getClient().getId() != clientId){
              Client client = clientService.getById(clientId).orElseThrow(() -> new ClientIsNotFoundException("Client not found!"));
              item.setClient(client);
         }
         item.setStatus(Status.INACTIVE);
         boardItemRepository.deleteById(boardId);
        //todo настроит каскадирование с доской объявления и предметом
    }

    @Override
    public Item getItem(long id) {
         return boardItemRepository.findById(id).orElseThrow(() -> new BoardIsNotFoundException("Board item not found!!")).getItem();
    }
}
