package com.urise.webapp.model;

public class OrganizationSection extends Section {
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
	public String toString() {
		return "\n OrganizationSection{" +
				"organization=" + organization +
				'}' + "\n";
	}
}
