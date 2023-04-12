package kz.kurol.auctionapi.services.impl;

import kz.kurol.auctionapi.models.Board;
import kz.kurol.auctionapi.models.Client;
import kz.kurol.auctionapi.models.Item;
import kz.kurol.auctionapi.models.enums.Status;
import kz.kurol.auctionapi.repositories.BoardRepository;
import kz.kurol.auctionapi.services.intf.BoardService;
import kz.kurol.auctionapi.services.intf.ClientService;
import kz.kurol.auctionapi.services.intf.ItemService;
import kz.kurol.auctionapi.utils.errors.BoardIsNotFoundException;
import kz.kurol.auctionapi.utils.errors.ClientIsNotFoundException;
import kz.kurol.auctionapi.utils.errors.ItemIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ItemService itemService;
    private final ClientService clientService;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, ItemService itemService, ClientService clientService) {
        this.boardRepository = boardRepository;
        this.itemService = itemService;
        this.clientService = clientService;
    }

    @Override
    public void addItem(long id) {
         Board board = new Board();
         Item item = itemService.getById(id).orElseThrow(() -> new ItemIsNotFoundException("Item not found!"));
         item.setStatus(Status.ACTIVE);
         board.setItem(item);
         boardRepository.save(board);
    }

    @Override
    public void removeItem(long boardId, long clientId) {
         Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardIsNotFoundException("Board item not found!"));
         Item item = board.getItem();
         if(item.getClient().getId() != clientId){
              Client client = clientService.getById(clientId).orElseThrow(() -> new ClientIsNotFoundException("Client not found!"));
              item.setClient(client);
         }
         item.setStatus(Status.INACTIVE);
         boardRepository.deleteById(boardId);
        //todo настроит каскадирование с доской объявления и предметом
    }

    @Override
    public Item getItem(long id) {
         return boardRepository.findById(id).orElseThrow(() -> new BoardIsNotFoundException("Board item not found!!")).getItem();
    }
}
