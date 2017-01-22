package com.camlait.global.erp.domain.operation.reglement;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.camlait.global.erp.domain.Entite;
import com.camlait.global.erp.domain.util.Utility;

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
@Table(name = "`reg-mode-de-reglements`")
public class ModeDeReglement extends Entite {

	@Id
	private String modeDeReglementId;

	@Column(name = "codeModeReglement", nullable = false, unique = true)
	private String codeModeReglement;

	private String descriptionModeReglement;

	private Date dateDeCreation;

	private Date derniereMiseAJour;

	public ModeDeReglement() {
	}

	@PrePersist
	private void setKey() {
		setModeDeReglementId(Utility.getUidFor(modeDeReglementId));
		setDateDeCreation(new Date());
		setDerniereMiseAJour(new Date());
	}

	@PreUpdate
	private void preUpdate() {
		setDerniereMiseAJour(new Date());
	}

	@Override
	public void postConstructOperation() {
	}
}
