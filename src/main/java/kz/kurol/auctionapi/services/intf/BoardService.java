package kz.kurol.auctionapi.services.intf;

import kz.kurol.auctionapi.models.Item;

public interface BoardService {
    void addItem(long id);
    void removeItem(long boardId,long clientId);
    Item getItem(long id);


}
