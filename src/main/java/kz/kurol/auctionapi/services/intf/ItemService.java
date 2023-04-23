package kz.kurol.auctionapi.services.intf;

import kz.kurol.auctionapi.models.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Optional<Item> getById(long id);
    void saveNewItem(Item item);
    void deleteById(long id);
    void update(long id,Item updatedItem);
    List<Item> getAll();

    List<Item> getUserItemsById(long id);


}
