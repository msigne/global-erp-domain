package com.camlait.global.erp.domain.dm;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.camlait.global.erp.domain.BaseEntity;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.enumeration.EnumTypeEntitity;
import com.camlait.global.erp.domain.enumeration.OtherEnum;
import com.camlait.global.erp.domain.exception.DataValidationException;
import com.camlait.global.erp.domain.operation.Recovery;
import com.camlait.global.erp.domain.partner.Employee;
import com.camlait.global.erp.domain.partner.Seller;
import com.camlait.global.erp.domain.util.Utility;
import com.camlait.global.erp.domain.warehouse.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
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
@EqualsAndHashCode(callSuper = false, exclude = {"documents", "recoveries", "dailyMovementDetails"})
@ToString(exclude = {"documents", "recoveries", "dailyMovementDetails"})
@Builder
@Table(name = "`dm-daily-movments`")
public class DailyMovement extends BaseEntity {

    @Id
    private String dmId;

    @Column(nullable = false, unique = true)
    private String dmCode;

    private Date dmDate;

    @Transient
    private String sellerId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Seller seller;

    @Transient
    private String storeId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @JsonManagedReference
    @OneToMany(mappedBy = "dailyMovment")
    private Collection<Document> documents = Sets.newHashSet();

    @JsonManagedReference
    @OneToMany(mappedBy = "dailyMovment")
    private Collection<Recovery> recoveries = Sets.newHashSet();

    @JsonManagedReference
    @OneToMany(mappedBy = "dailyMovment")
    private Collection<DailyMovementDetail> dailyMovementDetails = Sets.newHashSet();

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date lastUpdateddate;

    private boolean bmqClos;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "workerId")
    private Employee worker;

    public DailyMovement() {
    }

    @PrePersist
    private void setKey() {
        setDmId(Utility.getUidFor(dmId));
        final List<String> errors = Lists.newArrayList();
        if (!errors.isEmpty()) {
            throw new DataValidationException(Joiner.on("\n").join(errors));
        }
    }

    @PreUpdate
    private void preUpdate() {
        setLastUpdateddate(new Date());
    }

    @Override
    public void postConstructOperation() {
        setStoreId(store.getStoreId());
        setSellerId(seller.getPartnerId());
    }

    /**
     * Built the Daily Movement details  for the current object.
     * 
     * @return The current object with associated details.
     */
    public DailyMovement buildLigne() {
        Optional<Set<DailyMovementDetail>> lines = 
                this.getDocuments().stream().map(d->{
                    return d.getDocumentDetails().stream().map(l->{
                        return DailyMovementDetail.builder()
                                .dailyMovement(this)
                                .dmdId(this.getDmId())
                                .document(d)
                                .lineUnitPrice(l.getLineUnitPrice())
                                .product(l.getProduct())
                                .productId(l.getProduct().getProductId())
                                .lineQuantity(l.getLineQuantity())
                                .build();
                    }).collect(Collectors.toSet());
                }).findFirst();
        setDailyMovementDetails(lines.isPresent() ? lines.get() : Sets.newHashSet());
        return this;
    }

    @Override
    public EnumTypeEntitity toEnum() {
        return OtherEnum.BMQ;
    }
}