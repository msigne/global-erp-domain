package com.camlait.global.erp.domain.bmq;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.document.Document;
import com.camlait.global.erp.domain.entrepot.Magasin;
import com.camlait.global.erp.domain.operation.Recouvrement;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.partenaire.Vendeur;
import com.camlait.global.erp.domain.util.Utility;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Bmq extends Entite {

    @Id
    private String bmqId;

    @Column(nullable = false, unique = true)
    private String codeBmq;

    private Date dateBmq;

    @Transient
    private String vendeurId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "vendeurId")
    private Vendeur vendeur;

    @Transient
    private String magasinId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "magasinId")
    private Magasin magasin;

    @JsonManagedReference
    @OneToMany(mappedBy = "bmq")
    private Collection<Document> documents = Sets.newHashSet();

    @JsonManagedReference
    @OneToMany(mappedBy = "bmq")
    private Collection<Recouvrement> recouvrements = Sets.newHashSet();

    @JsonManagedReference
    @OneToMany(mappedBy = "bmq")
    private Collection<LigneBmq> ligneBmqs = Sets.newHashSet();

    private Date dateDeCreation;

    private Date derniereMiseAJour;

    private boolean bmqClos;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "responsableId")
    private Employe responsable;

    public Bmq() {
        setDateDeCreation(new Date());
        setDerniereMiseAJour(new Date());
    }

    public void setvendeurId() {
        setVendeurId(getVendeur().getPartenaireId());
    }

    public void setMagasinId() {
        setMagasinId(getMagasin().getMagasinId());
    }
    
	@PrePersist
	private void setKey() {
		setBmqId(Utility.getUid());
	}
}
