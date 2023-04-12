package kz.kurol.auctionapi.services.impl;

import kz.kurol.auctionapi.models.Item;
import kz.kurol.auctionapi.models.enums.Status;
import kz.kurol.auctionapi.repositories.ItemRepository;
import kz.kurol.auctionapi.services.intf.ItemService;
import kz.kurol.auctionapi.utils.errors.ItemIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Optional<Item> getById(long id) {
        return itemRepository.findById(id);
    }

    @Override
    public void saveNewItem(Item item) {
        item.setStatus(Status.INACTIVE);
        itemRepository.save(item);
    }



    @Override
    public void deleteById(long id) {
        if(getById(id).isPresent()){
            itemRepository.deleteById(id);
        }
        else{
            throw new ItemIsNotFoundException("item is not found!");
        }
    }

    @Override
    public void update(long id,Item updatedItem) {
        Item itemToBeUpdated = getById(id).orElseThrow(()-> new ItemIsNotFoundException("Item not found!"));
        itemToBeUpdated.setTitle(updatedItem.getTitle());
        itemToBeUpdated.setDescription(updatedItem.getDescription());
        itemRepository.save(itemToBeUpdated);
    }



    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }
}
