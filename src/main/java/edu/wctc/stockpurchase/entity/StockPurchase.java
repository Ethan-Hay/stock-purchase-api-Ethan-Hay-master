package edu.wctc.stockpurchase.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name="stock_purchase")
public class StockPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="purchase_id")
    private int id;

    /*
    Complete this class by adding the rest of the fields
    and their @Column mappings where necessary. Consult
    stock_purchase.png to see the table structure.
     */
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "price_per_share")
    private double pricePerShare;
    @Column(name = "shares")
    private double shares;
    @Column(name = "purchase_date")
    private Timestamp purchaseDate;
}
