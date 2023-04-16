package kz.kurol.auctionapi.controllers;

import kz.kurol.auctionapi.dto.AuctionItemDTO;
import kz.kurol.auctionapi.models.Item;
import kz.kurol.auctionapi.services.intf.BoardItemService;
import kz.kurol.auctionapi.services.intf.ClientService;
import kz.kurol.auctionapi.services.intf.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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




}
