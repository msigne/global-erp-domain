package com.camlait.global.erp.domain.partenaire;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.document.commerciaux.vente.DocumentDeVente;
import com.camlait.global.erp.domain.enumeration.TypePartenaire;
import com.camlait.global.erp.domain.immobilisation.PartenaireImmobilisation;
import com.camlait.global.erp.domain.operation.Operation;
import com.camlait.global.erp.domain.operation.reglement.ModeleDeReglement;
import com.camlait.global.erp.domain.organisation.Centre;
import com.camlait.global.erp.domain.produit.Tarif;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Partenaire extends Entite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partenaireId;

    @Column(name = "codePartenaire", nullable = false, unique = true)
    private String codePartenaire;

    @Column(length = 512)
    private String adresse;

    private String telephone;

    private Date dateDeCreation;

    private Date derniereMiseAJour;

    @Enumerated(EnumType.STRING)
    private TypePartenaire typePartenaire;

    @Transient
    private Long centreId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "centreId")
    private Centre centre;

    @JsonManagedReference
    @OneToMany(mappedBy = "client")
    private Collection<DocumentDeVente> documents = Sets.newHashSet();

    @JsonManagedReference
    @OneToMany(mappedBy = "immobilisation")
    private Collection<PartenaireImmobilisation> partenaireImmobilisations = Sets.newHashSet();

    @JsonManagedReference
    @OneToMany(mappedBy = "partenaire")
    private Collection<Operation> operations = Sets.newHashSet();

    @Transient
    private Long groupePartenaireId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "groupePartenaireId")
    private GroupePartenaire groupePartenaire;

    @Transient
    private Long tarifId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "tarifId")
    private Tarif tarif;

    @JsonManagedReference
    @OneToMany(mappedBy = "partenaire")
    private Collection<ModeleDeReglement> modeleDeReglements = Sets.newHashSet();

    public Partenaire() {
        setDateDeCreation(new Date());
        setDerniereMiseAJour(new Date());
    }
}
