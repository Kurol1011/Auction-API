package kz.kurol.auctionapi.repositories;

import kz.kurol.auctionapi.models.BoardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardItemRepository extends JpaRepository<BoardItem,Long> {
}