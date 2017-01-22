package com.camlait.global.erp.domain.operation.caisse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.partenaire.Employe;
import com.camlait.global.erp.domain.util.Utility;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "`caisse-caisses`")
public class Caisse extends Entite {
	@Id
	private String caisseId;

	@Column(name = "codeCaisse", unique = true, nullable = false)
	private String codeCaisse;

	private String descriptionCaisse;

	@Transient
	private String responsableId;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "responsableId")
	private Employe responsable;

	private Date dateDeCreation;

	private Date derniereMiseAJour;

	public Caisse() {
	}

	@PrePersist
	private void setKey() {
		setCaisseId(Utility.getUidFor(caisseId));
		setDateDeCreation(new Date());
		setDerniereMiseAJour(new Date());
	}

	@PreUpdate
	private void preUpdate() {
		setDerniereMiseAJour(new Date());
	}

	@Override
	public void postConstructOperation() {
		setResponsableId(responsable.getPartenaireId());
	}
}
