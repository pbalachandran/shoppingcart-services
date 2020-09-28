package com.corelogic.sc.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_category")
@ToString(exclude = {"products", "createdDate"})
public class ProductCategory implements Serializable {
    @Id
    @Column(name = "product_category_name")
    private String productCategoryName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @JsonBackReference
    @OneToMany(mappedBy = "productCategory",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Product> products;
}
