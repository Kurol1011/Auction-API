package kz.kurol.auctionapi.repositories;

import kz.kurol.auctionapi.models.BoardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardItemRepository extends JpaRepository<BoardItem,Long> {
    List<BoardItem> findByItem_id(long id);
    List<BoardItem> findAllByItem_idIn(List<Long> ids);
}
