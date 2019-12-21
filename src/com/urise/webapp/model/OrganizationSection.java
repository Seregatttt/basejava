package com.urise.webapp.model;

public class OrganizationSection extends AbstractSection {
	private Organization organization;

	public OrganizationSection(Organization organization) {
		this.organization = organization;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
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
