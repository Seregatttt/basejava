package com.urise.webapp.web;

import com.urise.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ResumeServlet extends HttpServlet {
	private Storage storage; // = Config.get().getStorage();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		storage = Config.get().getStorage();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String uuid = request.getParameter("uuid");
		String fullName = request.getParameter("fullName");
		String action = request.getParameter("action");
		String action2 = request.getParameter("test_act");
		if (action2 == "") {

		}
		Resume r = storage.get(uuid);

		r.setFullName(fullName);
		for (ContactType type : ContactType.values()) {
			String value = request.getParameter(type.name());
			if (value != null && value.trim().length() != 0) {
				r.addContact(type, value);
			} else {
				r.getContacts().remove(type);
			}
		}
		for (SectionType type : SectionType.values()) {
			switch (type) {
				case OBJECTIVE:
				case PERSONAL:
					String info2 = request.getParameter(type.name());
					r.addSection(type, new TextSection(info2));
					break;
				case ACHIEVEMENT:
				case QUALIFICATIONS:
					Map<String, String[]> map = request.getParameterMap();
					String[] sections = request.getParameterValues(type.name());
					ListSection listSection = new ListSection(new ArrayList<>());
					for (String str : sections) {
						listSection.save(str);
					}
					r.addSection(type, listSection);
					break;
				case EXPERIENCE:
				case EDUCATION:
					String[] list_organisation = request.getParameterValues(type.name() + "&list_organisation");
					List<Organization> organizations = new ArrayList<>();
					for (String org_id : list_organisation) {
						String name = request.getParameter(type.name() + "&org=" + org_id);
						String url = request.getParameter(type.name() + "&url=" + org_id);
						String[] list_position = request.getParameterValues(type.name() + "&list_organisation=" + org_id + "&list_position");
						List<Organization.Position> positions = new ArrayList<>();
						if (list_position != null) {
							for (String pos_id : list_position) {
								String posDate1 = request.getParameter(type.name() + "&list_organisation=" + org_id + "&list_position=" + pos_id + "&posDate1");
								String posDate2 = request.getParameter(type.name() + "&list_organisation=" + org_id + "&list_position=" + pos_id + "&posDate2");
								String posName = request.getParameter(type.name() + "&list_organisation=" + org_id + "&list_position=" + pos_id + "&posName");
								String posDescript = request.getParameter(type.name() + "&list_organisation=" + org_id + "&list_position=" + pos_id + "&posDescript");
								positions.add(new Organization.Position(LocalDate.parse(posDate1).getYear(), LocalDate.parse(posDate1).getMonth(),
										LocalDate.parse(posDate2).getYear(), LocalDate.parse(posDate2).getMonth(),
										posName, posDescript));
							}
						}
						organizations.add(new Organization(new Link(name, url), positions));
					}
					r.addSection(type, new OrganizationSection(organizations));
					break;
			}
		}
		storage.update(r);
		response.sendRedirect("resume");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		String uuid = request.getParameter("uuid");
		String action = request.getParameter("action");
		if (action == null) {
			request.setAttribute("resumes", storage.getAllSorted());
			request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
			return;
		}
		Resume r;
		OrganizationSection section;
		Organization org;
		switch (action) {
			case "delete":
				storage.delete(uuid);
				response.sendRedirect("resume");
				return;
			case "view":
			case "edit":
				r = storage.get(uuid);
				break;
			case "add_listSection":
				r = storage.get(uuid);
				ListSection listSection = (ListSection) r.getSection(SectionType.valueOf(request.getParameter("type_section")));
				if (listSection == null) {
					listSection = new ListSection("");
					r.addSection(SectionType.valueOf(request.getParameter("type_section")), listSection);
				} else {
					listSection.getList().add("");
				}
				storage.update(r);
				break;
			case "add_resume":
				r = new Resume("Новое резюме");
				response.sendRedirect("resume");
				storage.save(r);
				return;
			case "add_position":
				r = storage.get(uuid);
				//int num_position = Integer.parseInt(request.getParameter("num_position"));
				section = (OrganizationSection) r.getSection(SectionType.valueOf(request.getParameter("type_section")));
				org = section.getOrganizations().get(Integer.parseInt(request.getParameter("num_organisation")));
				org.getPositions().add(
						new Organization.Position(LocalDate.now().getYear(), LocalDate.now().getMonth(),
								LocalDate.now().getYear(), LocalDate.now().getMonth(),
								"new", "new"));
				storage.update(r);
				break;
			case "del_position":
				r = storage.get(uuid);
				section = (OrganizationSection) r.getSection(SectionType.valueOf(request.getParameter("type_section")));
				org = section.getOrganizations().get(Integer.parseInt(request.getParameter("num_organisation")));
				org.getPositions().remove(Integer.parseInt(request.getParameter("num_position")));
				storage.update(r);
				break;
			case "add_organisation":
				r = storage.get(uuid);
				//int num_position = Integer.parseInt(request.getParameter("num_position"));
				OrganizationSection orgSectionection = (OrganizationSection) r.getSection(SectionType.valueOf(request.getParameter("type_section")));
				if (orgSectionection == null) {
					orgSectionection = new OrganizationSection(new Organization("new", "new",
							new Organization.Position(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getYear(), LocalDate.now().getMonth(),
									"", "")));
					r.addSection(SectionType.valueOf(request.getParameter("type_section")), orgSectionection);
				} else {
					orgSectionection.getOrganizations().add(new Organization("new", "new",
							new Organization.Position(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getYear(), LocalDate.now().getMonth(),
									"", "")));
				}
				storage.update(r);
				break;
			case "del_organisation":
				r = storage.get(uuid);
				int num_organisation = Integer.parseInt(request.getParameter("num_organisation"));
				section = (OrganizationSection) r.getSection(SectionType.valueOf(request.getParameter("type_section")));
				section.getOrganizations().remove(num_organisation);
				storage.update(r);
				break;
			default:
				throw new IllegalArgumentException("Action " + action + " is illegal");
		}
		request.setAttribute("resume", r);
		request.getRequestDispatcher(
				("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
		).forward(request, response);
	}
}
