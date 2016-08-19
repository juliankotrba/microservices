package org.xine.async.business.contracts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Part implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dueDate;
	private BigDecimal value;

	protected Part() {
	}

	public static Part of(String dueDate, BigDecimal value) {
		final Part part = new Part();
		part.dueDate = dueDate;
		part.value = value;
		return part;
	}

	public String getDueDate() {
		return this.dueDate;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dueDate, this.value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Part other = (Part) obj;
		if (this.dueDate == null) {
			if (other.dueDate != null) {
				return false;
			}
		} else if (!this.dueDate.equals(other.dueDate)) {
			return false;
		}
		if (this.value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public Part plus(BigDecimal plus) {
		return of(this.dueDate, this.value.add(plus));
	}
}