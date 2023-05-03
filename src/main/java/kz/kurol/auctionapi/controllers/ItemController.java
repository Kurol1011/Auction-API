package kz.kurol.auctionapi.controllers;

import kz.kurol.auctionapi.dto.AuctionItemDTO;
import kz.kurol.auctionapi.dto.ClientDTO;
import kz.kurol.auctionapi.dto.RateDTO;
import kz.kurol.auctionapi.models.BoardItem;
import kz.kurol.auctionapi.models.Client;
import kz.kurol.auctionapi.models.Item;
import kz.kurol.auctionapi.services.intf.BoardItemService;
import kz.kurol.auctionapi.services.intf.ClientService;
import kz.kurol.auctionapi.services.intf.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ItemController {

    private final ItemService itemService;
    private final ClientService clientService;
    private final BoardItemService boardItemService;

    @Autowired
    public ItemController(ItemService itemService, ClientService clientService, BoardItemService boardItemService) {
        this.itemService = itemService;
        this.clientService = clientService;
        this.boardItemService = boardItemService;
    }

    @PostMapping("/item/create")
    public ResponseEntity<?> createItem(@RequestBody AuctionItemDTO auctionItemDTO){
        boardItemService.convertToBoardItem(auctionItemDTO,clientService.getCurrentClient());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/item/update")
    public ResponseEntity<?> updateItem(@RequestParam("id") long id, Item item){
        itemService.update(id,item);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/auctions")
    public List<AuctionItemDTO> getAllAuctions(@RequestParam(value="id",required = false) String id){
        if(id!=null){
            return Collections.singletonList(boardItemService.convertToAuctionItemDTO(boardItemService.getBoardItemById(Integer.parseInt(id)).get()));
        }
        return boardItemService.getAllBoardItems()
                .stream()
                .map(item ->
                   boardItemService.convertToAuctionItemDTO(item)
                )
                .collect(Collectors.toList());
    }

    @GetMapping("/my-auctions")
    public List<AuctionItemDTO> getClientAuctions(){
        return boardItemService.getClientBoardItems()
                .stream()
                .limit(3)
                .map(item ->
                        boardItemService.convertToAuctionItemDTO(item)
                )
                .collect(Collectors.toList());
    }

    @PostMapping("/do-rate")
    public ResponseEntity<?> setRateToBoardItem(@RequestBody RateDTO rateDTO){
        boardItemService.updateFinalPrice(rateDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/boarditem")
    public Boolean isClientParticipation(@RequestParam(value = "id",required = true) String id){
       return clientService.hasClientAuctionItemById(Integer.parseInt(id));
    }

    @PostMapping("/join-auction")
    public ResponseEntity<?> joinInAuction(@RequestBody long id) {
        Client client = clientService.getCurrentClient();
        BoardItem boardItem = boardItemService.getBoardItemById(id).get();
        client.getBoardItems().add(boardItem);
        boardItem.getParticipants().add(client);
        clientService.save(client);
        boardItemService.save(boardItem);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/participants")
    public List<ClientDTO> getParticipants(@RequestParam(value="id") String id){
        return boardItemService.getBoardItemById(Integer.parseInt(id)).get()
                .getParticipants()
                .stream()
                .map(c -> clientService.convertToClientDTO(c))
                .collect(Collectors.toList());
    }


}
