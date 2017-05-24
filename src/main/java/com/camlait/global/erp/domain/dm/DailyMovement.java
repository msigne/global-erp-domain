package com.camlait.global.erp.domain.dm;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.camlait.global.erp.domain.BaseEntity;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.enumeration.EnumTypeEntitity;
import com.camlait.global.erp.domain.enumeration.OtherEnum;
import com.camlait.global.erp.domain.exception.DataValidationException;
import com.camlait.global.erp.domain.helper.EntityHelper;
import com.camlait.global.erp.domain.operation.Recovery;
import com.camlait.global.erp.domain.partner.Employee;
import com.camlait.global.erp.domain.partner.Seller;
import com.camlait.global.erp.domain.warehouse.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"documents", "recoveries", "dailyMovementDetails"})
@ToString(exclude = {"documents", "recoveries", "dailyMovementDetails"})
@Builder
@Table(name = "`dm-daily-movements`")
public class DailyMovement extends BaseEntity {

    @Id
    private String dmId;

    @Column(nullable = false, unique = true)
    private String dmCode;

    private Date dmDate;

    @Transient
    private String sellerId;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sellerId")
    private Seller seller;

    @Transient
    private String storeId;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "storeId")
    private Store store;

    @Builder.Default 
    @OneToMany(mappedBy = "dailyMovement")
    private Collection<Document> documents = Lists.newArrayList();

    @Builder.Default 
    @OneToMany(mappedBy = "dailyMovement")
    private Collection<Recovery> recoveries = Lists.newArrayList();

    @Builder.Default 
    @OneToMany(mappedBy = "dailyMovement")
    private Collection<DailyMovementDetail> dailyMovementDetails = Lists.newArrayList();
    private boolean bmqClos;

    @Transient
    private String workerId;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "workerId")
    private Employee worker;

    public DailyMovement() {
    }

    @PrePersist
    private void setKey() {
        setDmId(EntityHelper.getUidFor(dmId));
        final List<String> errors = Lists.newArrayList();
        if (!errors.isEmpty()) {
            throw new DataValidationException(Joiner.on("\n").join(errors));
        }
    }

    @Override
    public DailyMovement init() {
        setStoreId(store == null ? null : store.getStoreId());
        setSellerId(seller == null ? null : seller.getPartnerId());
        setWorkerId(worker == null ? null : worker.getPartnerId());
        setDailyMovementDetails(dailyMovementDetails == null ? Lists.newArrayList() : dailyMovementDetails.stream().map(dm -> {
            return dm.init();
        }).collect(Collectors.toList()));
        setRecoveries(recoveries == null ? Lists.newArrayList() : recoveries.stream().map(r -> {
            return r.init();
        }).collect(Collectors.toList()));
        setDocuments(documents == null ? Lists.newArrayList() : documents.stream().map(d -> {
            return d.init();
        }).collect(Collectors.toList()));
        return this;
    }

    /**
     * Built the Daily Movement details for the current object.
     * 
     * @return The current object with associated details.
     */
    public DailyMovement buildLigne() {
        Optional<Set<DailyMovementDetail>> lines = this.getDocuments().stream().map(d -> {
            return d.getDocumentDetails().stream().map(l -> {
                return DailyMovementDetail.builder().dailyMovement(this).dmdId(this.getDmId()).document(d).lineUnitPrice(l.getLineUnitPrice())
                        .product(l.getProduct()).productId(l.getProduct().getProductId()).lineQuantity(l.getLineQuantity()).build();
            }).collect(Collectors.toSet());
        }).findFirst();
        setDailyMovementDetails(lines.isPresent() ? lines.get() : Lists.newArrayList());
        return this;
    }

    @Override
    public EnumTypeEntitity toEnum() {
        return OtherEnum.BMQ;
    }
}
