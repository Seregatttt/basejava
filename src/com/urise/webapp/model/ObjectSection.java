package com.urise.webapp.model;

public class ObjectSection extends Section {
	private Organization organization;

	public ObjectSection(Organization organization) {
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
		return "\n ObjectSection{" +
				"organization=" + organization +
				'}' + "\n";
	}
}
