package com.retail.store.dao;

import com.retail.store.entity.Item;
import com.retail.store.util.ItemType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    @Query("SELECT itemType FROM Item WHERE itemId = :itemId")
    ItemType findItemTypeByItemId(@Param("itemId") int itemId);
}
