package kz.kurol.auctionapi.services.intf;

import kz.kurol.auctionapi.dto.AuctionItemDTO;
import kz.kurol.auctionapi.dto.RateDTO;
import kz.kurol.auctionapi.models.BoardItem;
import kz.kurol.auctionapi.models.Client;
import kz.kurol.auctionapi.models.Item;

import java.util.List;
import java.util.Optional;

public interface BoardItemService {
    void addItem(long id);
    void removeItem(long boardId,long clientId);
    Item getItem(long id);

    List<BoardItem> getItemsById(List<Long> id);

    List<BoardItem> getAllBoardItems();
    void convertToBoardItem(AuctionItemDTO auctionItemDTO, Client client);

    List<BoardItem> getClientBoardItems();

    AuctionItemDTO convertToAuctionItemDTO(BoardItem boardItem);

    Optional<BoardItem> getBoardItemById(long id);

    void updateFinalPrice(RateDTO rateDTO);

    void save(BoardItem boardItem);

}
