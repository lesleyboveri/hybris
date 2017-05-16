<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="springerlink" uri="http://hybris.com/tld/springerlinktags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set value="${springerlink:entryImage(entry)}" var="entryImage"/>
<c:set value="${fn:escapeXml(entry.parameters['contenttitle'])}" var="name"/>
<c:choose>
	<c:when test="${not empty entryImage}">
		<img src="${entryImage.url}" alt="${name}" title="${name}"/>
	</c:when>
	<c:otherwise>
		<theme:image code="img.missingProductImage.${format}" alt="${name}" title="${name}"/>
	</c:otherwise>
</c:choose>