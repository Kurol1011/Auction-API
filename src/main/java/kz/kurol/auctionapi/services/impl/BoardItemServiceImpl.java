package kz.kurol.auctionapi.services.impl;

import jakarta.transaction.Transactional;
import kz.kurol.auctionapi.dto.AuctionItemDTO;
import kz.kurol.auctionapi.dto.RateDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional()
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

    @Override
    public List<BoardItem> getItemsById(List<Long> itemsId) {
        return null;
    }

    @Override
    public List<BoardItem> getAllBoardItems() {
        return boardItemRepository.findAll();
    }



    @Override
    public void convertToBoardItem(AuctionItemDTO auctionItemDTO, Client client) {
        Item item = new Item();
        item.setStatus(Status.ACTIVE);
        item.setTitle(auctionItemDTO.getTitle());
        item.setDescription(auctionItemDTO.getDescription());
        item.setClient(client);
        BoardItem boardItem = new BoardItem();
        boardItem.setItem(item);
        boardItem.setStartingPrice(auctionItemDTO.getInitialPrice());
        boardItem.setFinalPrice(auctionItemDTO.getInitialPrice());
        boardItemRepository.save(boardItem);
    }

    @Override
    public AuctionItemDTO convertToAuctionItemDTO(BoardItem boardItem){
        AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
        auctionItemDTO.setId(boardItem.getId());
        auctionItemDTO.setTitle(boardItem.getItem().getTitle());
        auctionItemDTO.setDescription(boardItem.getItem().getDescription());
        auctionItemDTO.setInitialPrice(boardItem.getStartingPrice());
        auctionItemDTO.setFinalPrice(boardItem.getFinalPrice());
        return auctionItemDTO;
    }

    @Override
    public Optional<BoardItem> getBoardItemById(long id) {
        return boardItemRepository.findById(id);
    }

    @Override
    public void updateFinalPrice(RateDTO rateDTO) {
        BoardItem boardItem = boardItemRepository.findById(rateDTO.getId()).get();
        boardItem.setFinalPrice(rateDTO.getRate());
        boardItemRepository.save(boardItem);
    }

    @Override
    public void save(BoardItem boardItem) {
        boardItemRepository.save(boardItem);
    }

    @Override
    public List<BoardItem> getClientBoardItems() {
        List<Item> getAllItems = itemService.getUserItemsById(clientService.getCurrentClient().getId());
        List<Item> itemsActive = getAllItems.stream().filter(i -> i.getStatus() == Status.ACTIVE).collect(Collectors.toList());
        return boardItemRepository.findAllByItem_idIn(itemsActive.stream().map(i -> i.getId()).collect(Collectors.toList()));
    }
}
