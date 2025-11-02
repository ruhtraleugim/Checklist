package com.DesafioTec.CheckList.model.list;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private String nameItem;
    private String statusItem;
    private String colorItem;
    private String tipeItem;
    private Date dataItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id", nullable = false)
    private CheckListModel checkList;
}
