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
import java.util.ArrayList;
import java.util.List;


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
					String[] sections = request.getParameterValues(type.name());
					ListSection listSection2 = new ListSection(new ArrayList<>());
					for (String str : sections) {
						listSection2.save(str);
					}
					r.addSection(type, listSection2);
					break;
				case EXPERIENCE:
				case EDUCATION:
					String[] sections2 = request.getParameterValues(type.name());
					String nameOrg = sections2[0];
					String urlOrg = sections2[1];
					List<Organization.Position> positions = new ArrayList<>();

					for (int i = 2; i < sections2.length; i = i + 4) {
						LocalDate startDate = LocalDate.parse(sections2[i]);
						LocalDate endDate = LocalDate.parse(sections2[i + 1]);
						String title = sections2[i + 2];
						String description = sections2[i + 3];
						positions.add(new Organization.Position(startDate, endDate, title, description));
					}
					r.addSection(type, new OrganizationSection(new Organization(new Link(nameOrg, urlOrg), positions)));
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
		switch (action) {
			case "delete":
				storage.delete(uuid);
				response.sendRedirect("resume");
				return;
			case "view":
			case "edit":
				r = storage.get(uuid);
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
