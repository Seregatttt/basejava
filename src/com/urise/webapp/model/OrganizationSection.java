package com.urise.webapp.model;

import java.util.Objects;

public class OrganizationSection extends AbstractSection {
	private static final long serialVersionUID = 1L;
	private final Organization organization;

	public OrganizationSection(Organization organization) {
		Objects.requireNonNull(organization, "organization must not be null");
		this.organization = organization;
	}

	public Organization getOrganization() {
		return organization;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OrganizationSection that = (OrganizationSection) o;

		return organization != null ? organization.equals(that.organization) : that.organization == null;
	}

	@Override
	public int hashCode() {
		return organization != null ? organization.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "\n OrganizationSection{" +
				"organization=" + organization +
				'}' + "\n";
	}
}
