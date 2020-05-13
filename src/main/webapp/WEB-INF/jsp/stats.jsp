<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<div class="row" id="information">
    <div class="col-sm-3">
        <h3>Total rounds played</h3>
        <p id="totalRoundsPlayedText"><c:out value = "${totalRoundsPlayed}"/></p>
    </div>
    <div class="col-sm-3">
        <h3>Total wins for player 1</h3>
        <p id="totalP1WinsText"><c:out value = "${totalP1Wins}"/></p>
    </div>
    <div class="col-sm-3">
        <h3>Total wins for player 2</h3>
        <p id="totalP2WinsTest"><c:out value = "${totalP2Wins}"/></p>
    </div>
    <div class="col-sm-3">
        <h3>Total ties</h3>
        <p id="totalTiesText"><c:out value = "${totalTies}"/></p>
    </div>
</div>