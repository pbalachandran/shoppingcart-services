package com.corelogic.sc.db.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
@ToString(exclude = {"items", "createdDate"})
public class Cart implements Serializable {
    @Id
    @Column(name = "cart_name")
    private String cartName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JsonBackReference
    @OneToMany(mappedBy = "cart",
            cascade = CascadeType.ALL)
    private List<Item> items;
}
