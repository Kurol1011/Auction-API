package kz.kurol.auctionapi.services.intf;

import kz.kurol.auctionapi.dto.AuctionItemDTO;
import kz.kurol.auctionapi.models.Client;
import kz.kurol.auctionapi.models.Item;

public interface BoardItemService {
    void addItem(long id);
    void removeItem(long boardId,long clientId);
    Item getItem(long id);

    void convertToBoardItem(AuctionItemDTO auctionItemDTO, Client client);
}
