package com.camlait.global.erp.domain.operation.manquant;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.camlait.global.erp.domain.operation.Operation;
import com.camlait.global.erp.domain.partenaire.Vendeur;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class ManquantFinancier extends Operation {

	@ManyToOne
	@JoinColumn(name="vendeurId")
	private Vendeur vendeur;
}
