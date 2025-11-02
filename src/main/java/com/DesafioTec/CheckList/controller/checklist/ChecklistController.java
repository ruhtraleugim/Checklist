package com.DesafioTec.CheckList.controller.checklist;

import com.DesafioTec.CheckList.model.list.CheckListModel;
import com.DesafioTec.CheckList.model.user.UserModel;
import com.DesafioTec.CheckList.service.checkList.CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist")
public class ChecklistController {

    @Autowired
    CheckListService service;

    @GetMapping("/myLists")
    public ResponseEntity<List<CheckListModel>> getAllByUserId(UUID id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAllByUserId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CheckListModel>> getAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAll());
    }

    @GetMapping("myLists/{id}")
    @PreAuthorize("#id == authentication.principal.userId")
    public ResponseEntity<CheckListModel> findById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getById(id)
                .orElseThrow( () -> new RuntimeException("Checklist Não Encontrada")));
    }



    @PostMapping("/myLists")
    @PreAuthorize("#id == authentication.principal.userId")
    public ResponseEntity<CheckListModel> save(@RequestBody CheckListModel model,
                                               @AuthenticationPrincipal UserModel user){
        model.setUser(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.save(model));
    }

    @DeleteMapping("/myLists/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id,
                                         @AuthenticationPrincipal UserModel user){

        CheckListModel existing = service.getById(id)
                .orElseThrow(() -> new RuntimeException("Checklist não encontrada"));

        if (!existing.getUser().getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        service.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("aceito");
    }

    @PatchMapping("/myLists/{id}")
    public ResponseEntity<CheckListModel> update(@RequestBody CheckListModel model,
                                                 @PathVariable Integer id,
                                                 @AuthenticationPrincipal UserModel user){
        CheckListModel exist = service.getById(id).orElseThrow(() -> new RuntimeException("Checklist Not Found"));

        if(!exist.getUser().getUserId().equals(user.getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.update(model,id));
    }
}