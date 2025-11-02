package com.DesafioTec.CheckList.service.checkList;

import com.DesafioTec.CheckList.model.list.CheckListModel;
import com.DesafioTec.CheckList.model.list.ItemModel;
import com.DesafioTec.CheckList.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    ItemRepository repo;


    public List<ItemModel> getAll(){
        return repo.findAll();
    }


    public Optional<ItemModel> getById(Integer id){
        return repo.findById(id);
    }

    public ItemModel save(ItemModel model){
        return repo.save(model);
    }

    public void delete(Integer id){
        repo.deleteById(id);
    }

    public ItemModel update(ItemModel model, Integer id){
        return  repo.findById(id).map(mod ->{
            mod.setDataItem(model.getDataItem());
            mod.setColorItem(model.getColorItem());
            mod.setStatusItem(model.getStatusItem());
            mod.setNameItem(model.getNameItem());

            return repo.save(mod);
        }).orElseThrow();
    }


}