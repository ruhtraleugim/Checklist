package com.DesafioTec.CheckList.model.list;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.DesafioTec.CheckList.model.user.UserModel;

@Entity
@Table(name = "check_list")
@Data
public class CheckListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer listaId;

    @Column(name = "name_list", nullable = false)
    private String nameList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "checkList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemModel> itensList;
}
