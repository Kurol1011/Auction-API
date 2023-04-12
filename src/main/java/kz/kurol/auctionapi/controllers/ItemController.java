package kz.kurol.auctionapi.controllers;

import kz.kurol.auctionapi.models.Client;
import kz.kurol.auctionapi.models.Item;
import kz.kurol.auctionapi.services.intf.BoardService;
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
    private final BoardService boardService;

    @Autowired
    public ItemController(ItemService itemService, ClientService clientService, BoardService boardService) {
        this.itemService = itemService;
        this.clientService = clientService;
        this.boardService = boardService;
    }

    @PostMapping("/item/create")
    public ResponseEntity<?> createItem(Item item){
        itemService.saveNewItem(item);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/item/update")
    public ResponseEntity<?> updateItem(@RequestParam("id") long id, Item item){
        itemService.update(id,item);
        return ResponseEntity.ok(HttpStatus.OK);
    }




}
