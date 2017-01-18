package com.camlait.global.erp.domain.document;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.amazonaws.util.CollectionUtils;
import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.document.commerciaux.DocumentCommerciaux;
import com.camlait.global.erp.domain.document.commerciaux.vente.DocumentDeVente;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClient;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureClientComptant;
import com.camlait.global.erp.domain.document.commerciaux.vente.FactureMarge;
import com.camlait.global.erp.domain.document.stock.DocumentDeStock;
import com.camlait.global.erp.domain.entrepot.Magasin;
import com.camlait.global.erp.domain.enumeration.SensOperation;
import com.camlait.global.erp.domain.enumeration.TypeDocuments;
import com.camlait.global.erp.domain.exception.DataStorageExcetion;
import com.camlait.global.erp.domain.inventaire.Inventaire;
import com.camlait.global.erp.domain.partenaire.Employe;
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
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor(suppressConstructorProperties = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Document extends Entite {

	@Id
	private String documentId;

	@Column(name = "codeDocument", unique = true, nullable = false)
	private String codeDocument;

	private Date dateDocument;

	@Transient
	private String magasinId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "magasinId")
	private Magasin magasin;

	@Transient
	private String responsableId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "responsableId")
	private Employe responsableDocument;
	

	private Date dateDeCreation;

	private Date derniereMiseAJour;

	@Enumerated(EnumType.STRING)
	private SensOperation sensOperation;

	@Transient
	private String BmqId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "bmqId")
	private Bmq bmq;

	@Transient
	private String inventaireId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "inventaireId")
	private Inventaire inventaire;

	@JsonManagedReference
	@OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
	private Collection<LigneDeDocument> ligneDocuments = Sets.newHashSet();

	@Enumerated(EnumType.STRING)
	private TypeDocuments typeDocument;

	public Document() {
		setDateDeCreation(new Date());
		setDerniereMiseAJour(new Date());
	}

	public boolean isFactureClient() {
		return this instanceof FactureClient;
	}

	public boolean isFactureComptant() {
		return this instanceof FactureClientComptant;
	}

	public boolean isFactureMarge() {
		return this instanceof FactureMarge;
	}

	public boolean isDocumentDeVente() {
		return this instanceof DocumentDeVente;
	}
	
	public boolean isDocumentCommerciaux() {
		return this instanceof DocumentCommerciaux;
	}
	
	public boolean stockAffects() {
		return (this instanceof DocumentDeStock) || (this instanceof FactureClient);
	}


	@PrePersist
	private void setKey() {
		if (!CollectionUtils.isNullOrEmpty(ligneDocuments)) {
			setDocumentId(Utility.getUid());
		} else {
			throw new DataStorageExcetion("Unable to store a document with no detail.");
		}
	}

	@Override
	public void postConstructOperation() {
		setMagasinId(magasin.getMagasinId());
		setResponsableId(responsableDocument.getPartenaireId());
		setBmqId(bmq != null ? bmq.getBmqId() : null);
		setInventaireId(inventaire != null ? inventaire.getInventaireId() : null);
	}
}
