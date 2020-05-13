<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="container" style="width: auto">
    <div>
        <button id="playNewRound" type="button" class="btn btn-default btn-lg" style="width:50%;float:left">
            Play a new round
        </button>
        <button id="restartGame" type="button" class="btn btn-default btn-lg" style="width:50%;float:left">
            Restart current game
        </button>
        <table class="table table-striped">
            <tr id="roundDetails">
                <th>Round Number</th>
                <th>Player One Move</th>
                <th>Player Two Move</th>
                <th>Winner</th>
            </tr>
            <c:forEach var="round" items="${selectedGameRounds}">
                <c:set var = "roundId" scope = "request" value = "${round.id}"/>
                <c:set var = "playerOneMove" scope = "request" value = "${round.playerOneMove}"/>
                <c:set var = "playerTwoMove" scope = "request" value = "${round.playerTwoMove}"/>
                <c:set var = "result" scope = "request" value = "${round.result}"/>
                <jsp:include page="round.jsp" flush="true"/>
            </c:forEach>
        </table>
    </div>
</div>
