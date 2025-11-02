package com.DesafioTec.CheckList.repository;

import com.DesafioTec.CheckList.model.list.CheckListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ListRepo extends JpaRepository<CheckListModel,Integer> {

     List<CheckListModel> findAllByUser_UserId(UUID userId);

}