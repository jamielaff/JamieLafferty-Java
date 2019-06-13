<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<tr class="roundOutcome">
    <td id="roundId"><c:out value = "${roundId}"/></td>
    <td id="playerOneMove"><c:out value = "${playerOneMove}"/></td>
    <td id="playerTwoMove"><c:out value = "${playerTwoMove}"/></td>
    <td id="result"><c:out value = "${result}"/></td>
</tr>