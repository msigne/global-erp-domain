package com.camlait.global.erp.domain.operation;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.camlait.global.erp.domain.bmq.Bmq;
import com.camlait.global.erp.domain.enumeration.SensOperation;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class Recouvrement extends Operation {

	@ManyToOne
	@JoinColumn(name = "bmqId")
	private Bmq bmq;

	public Recouvrement() {
		setSensOperation(SensOperation.ENTREE);
	}
}
