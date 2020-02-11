package com.urise.webapp.web;

import com.urise.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ResumeServlet extends HttpServlet {
	private static Storage storage = Config.get().getStorage();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		response.getWriter().write(name == null ? "Hello resumes" : "Hello    " + name + "   !!!  ");

		String text = "" +
				"<table border=\"1\" width=\"200\" height=\"100\" bgcolor=\"#c0e4ff\">\n" +
				"<tr>\n" +
				"<th>uuid</th>\n" +
				"<th>full_name</th>\n" +
				"</tr>\n";

		for (Resume r : storage.getAllSorted()) {
			text += "<tr>\n" +
					"<td align=center>" + r.getUuid() + "</td>\n" +
					"<td align=center>" + r.getFullName() + "</td>\n" +
					"</tr>\n";
		}
		response.getWriter().write(text + "</table>\n");
	}
}
