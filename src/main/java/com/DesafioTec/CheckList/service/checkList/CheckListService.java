package com.DesafioTec.CheckList.service.checkList;

import com.DesafioTec.CheckList.model.list.CheckListModel;
import com.DesafioTec.CheckList.repository.ListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckListService {
    @Autowired
    private ListRepo repo;

    public List<CheckListModel> getAllByUserId(UUID userId){return repo.findAllByUser_UserId(userId);}

    public List<CheckListModel> getAll(){return repo.findAll();}

    public Optional<CheckListModel> getById(Integer id){return repo.findById(id);}

    public CheckListModel save(CheckListModel model){return repo.save(model);}

    public void delete(Integer id){repo.deleteById(id);}

    public CheckListModel update(CheckListModel model, Integer id){
        return  repo.findById(id).map(mod ->{
            mod.setNameList(model.getNameList());
            return repo.save(mod);
        }).orElseThrow(() -> new RuntimeException("checklist not found"));
    }
}