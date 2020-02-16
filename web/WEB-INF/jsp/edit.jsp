<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${type.name() == 'OBJECTIVE' ||type.name() == 'PERSONAL'}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" size=30
                                   value="${ (resume.getSection(type).getContent())}"></dd>
                    </dl>
                </c:when>
                <c:when test="${type.name() == 'ACHIEVEMENT' ||type.name() == 'QUALIFICATIONS'}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dl style="display: inline-grid;margin-left: 8px;vertical-align: top; padding-top: 0">
                            <dt></dt>
                            <c:forEach var="list" items="${ ( resume.getSection(type).getList())}">
                                <input style="margin-top: 5px; margin-bottom: 5px;" type="text" name="${type.name()}"
                                       size=30 value=${ list}>
                            </c:forEach>
                        </dl>
                    </dl>
                </c:when>
                <c:when test="${type.name() == 'EXPERIENCE' ||type.name() == 'EDUCATION'}">

                    <dl>
                        <dt>${type.title}</dt>
                        <dl style="display: inline-grid;margin-left: 8px;vertical-align: top; padding-top: 0">
                            <dt></dt>
                            <c:forEach var="org" items="${ ( resume.getSection(type).getOrganizations())}">
                                <dl style="display: inline-block;  vertical-align: top; width: 600px;">
                                    <dt>Название организации</dt>
                                    <dd><input style="margin-top: 5px; margin-bottom: 5px;" type="text"
                                               name="${type.name()}"
                                               size=30
                                               value=${ org.getHomePage().getName()}></dd>
                                    <dt>Сайт организации</dt>
                                    <dd><input style="margin-top: 5px; margin-bottom: 5px;" type="text"
                                               name="${type.name()}"
                                               size=30
                                               value=${ org.getHomePage().getUrl()}></dd>

                                    <c:forEach var="pos" items="${ org.getPositions()}">
                                        <hr style="width: 475px;margin-left: 0px;border-left-width: 0px;padding-left: 0px;">
                                        <dt>Дата с</dt>
                                        <dd><input style="margin-top: 5px; margin-bottom: 5px;" type="text"
                                                   name="${type.name()}"
                                                   size=30
                                                   value=${ pos.getStartDate().toString()}></dd>
                                        <dt>Дата по</dt>
                                        <dd><input style="margin-top: 5px; margin-bottom: 5px;" type="text"
                                                   name="${type.name()}"
                                                   size=30
                                                   value=${ pos.getEndDate().toString()}></dd>
                                        <dt>Наименование</dt>
                                        <dd><input style="margin-top: 5px; margin-bottom: 5px;" type="text"
                                                   name="${type.name()}"
                                                   size=30
                                                   value=${ pos.getTitle()}></dd>
                                        <dt>Описание</dt>
                                        <dd><input style="margin-top: 5px; margin-bottom: 5px;" type="text"
                                                   name="${type.name()}" size=30
                                                   value=${ pos.getDescription()}></dd>

                                    </c:forEach>

                                </dl>
                            </c:forEach>
                        </dl>
                    </dl>
                </c:when>
                <c:otherwise>
                    <%-- Если не одно условие не есть верно.--%>
                </c:otherwise>
            </c:choose>

        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>