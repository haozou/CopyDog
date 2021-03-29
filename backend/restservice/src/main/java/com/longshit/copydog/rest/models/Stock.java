package com.longshit.copydog.rest.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(	name = "stocks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "stockLabel"),
        })
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String stockLabel;

    public Stock() {
    }

    public Stock(@NotBlank String stockLabel) {
        this.stockLabel = stockLabel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockLabel() {
        return stockLabel;
    }

    public void setStockLabel(String stockLabel) {
        this.stockLabel = stockLabel;
    }
}
