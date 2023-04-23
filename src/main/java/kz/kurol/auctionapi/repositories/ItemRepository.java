package kz.kurol.auctionapi.repositories;

import kz.kurol.auctionapi.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByClient_id(long clientId);
}
