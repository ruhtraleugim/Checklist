package com.DesafioTec.CheckList.repository;

import com.DesafioTec.CheckList.model.list.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemModel,Integer> {
}