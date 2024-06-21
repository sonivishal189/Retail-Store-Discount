package com.retail.store.dao;

import com.retail.store.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
}
