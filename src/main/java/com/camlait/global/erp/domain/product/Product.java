package com.camlait.global.erp.domain.product;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.amazonaws.util.CollectionUtils;
import com.camlait.global.erp.domain.BaseEntity;
import com.camlait.global.erp.domain.document.business.Tax;
import com.camlait.global.erp.domain.enumeration.EnumTypeEntitity;
import com.camlait.global.erp.domain.inventory.Stock;
import com.camlait.global.erp.domain.inventory.StockCard;
import com.camlait.global.erp.domain.localization.Localization;
import com.camlait.global.erp.domain.tarif.PriceType;
import com.camlait.global.erp.domain.tarif.Tariff;
import com.camlait.global.erp.domain.tarif.Tariffication;
import com.camlait.global.erp.domain.util.Utility;
import com.camlait.global.erp.domain.warehouse.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"taxes", "stocks", "stockCards", "tarifications"})
@ToString(exclude = {"taxes", "stocks", "stockCards", "tarifications"})
@Builder
@Table(name = "`product-products`")
public class Product extends BaseEntity {

    @Id
    private String productId;

    @Column(unique = true, nullable = false)
    private String productCode;

    private String productDescription;

    @Transient
    private String productCategoryId;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productCategoryId")
    private ProductCategory category;

    private boolean taxableProduct;

    private Double defaultUnitprice;

    @JsonManagedReference
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Tax> taxes = Sets.newHashSet();

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date lastUpdatedDate;

    private boolean stockFollowing;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Stock> stocks = Sets.newHashSet();

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<StockCard> stockCards = Sets.newHashSet();

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Tariffication> tarifications = Sets.newHashSet();

    public Product() {
    }

    public Long availableQuantity(Store m) {
        return this.getStocks().stream().filter(s -> s.getStore().getStoreId().equals(m.getStoreId())).mapToLong(s -> s.getAvailableQuantity()).sum();
    }

    public Boolean isAvailable(Store m, Long quantiteVoulue) {
        return availableQuantity(m) >= quantiteVoulue;
    }

    @PostConstruct
    public void copieCategorieProduitTaxe() {
        if (category != null) {
            final Collection<Tax> ctaxes = category.getTaxes();
            if (!CollectionUtils.isNullOrEmpty(ctaxes) && CollectionUtils.isNullOrEmpty(taxes)) {
                setTaxes(taxes);
            }
        }
    }

    @PrePersist
    private void setKey() {
        setProductId(Utility.getUidFor(productId));
    }

    @PreUpdate
    private void preUpdate() {
        setLastUpdatedDate(new Date());
    }

    @Override
    public void postConstructOperation() {
        setProductCategoryId(category.getProductcategoryId());
    }

    /**
     * Retrieve the unit price of the current product based on the given price
     * type.
     * 
     * @param type Provided price type.
     * @return The unit price that belongs to the given type or the default unit
     *         price if no record found for the given price type.
     */
    /**
     * Retrieve the unit price of the current product
     * 
     * @param type
     * @param zone
     * @param tarif
     * @return The unit price that belongs to the given price type, localization and the tariff.
     */
    public Double getUnitPrice(PriceType type, Localization zone, Tariff tariff) {
        if (type == null || CollectionUtils.isNullOrEmpty(tarifications)) {
            return defaultUnitprice;
        }
        final Optional<Tariffication> p = tarifications.stream()
                .filter(t -> zone.getLocalId().equals(t.getZone().getLocalId()))
                .filter(t->tariff.getTarifId().equals(t.getTarif().getTarifId()))
                .filter(t -> type.getPriceTypeId().equals(t.getPriceType().getPriceTypeId()))
                .findFirst();
        return p.isPresent() ? p.get().getValue() : defaultUnitprice;
    }

    /**
     * Retrieve the stock of the current product for a specific store.
     * 
     * @param m
     *            given store.
     * @return
     */
    public Stock getStockByStore(Store m) {
        return stocks.stream().filter(s -> m.getStoreId().equals(s.getStore().getStoreId())).findFirst().orElse(null);
    }

    @Override
    public EnumTypeEntitity toEnum() {
        // TODO Auto-generated method stub
        return null;
    }
}