<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Rock Paper Scissors Game</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    </head>
    <body>
        <h2>Rock Paper Scissors Game</h2>
        <button id="createNewGame" type="button" class="btn btn-default btn-lg active">
            Create a new game
        </button>
        <table>
            <c:forEach var="game" items="${gamesList}">
                <script>
                    console.log("${game.id}");
                </script>
                <tr>
                    <td>${game.id}</td>
                </tr>
            </c:forEach>
        </table>

        <!-- Ajax function calls -->
        <script>
            $("#createNewGame").click(function () {
                $.ajax({
                    type: "POST",
                    url: 'create',
        
                    success: function (data) {
                        console.log("New game succesfully created!")
                    },
        
                    failure: function (errMsg) {
                        console.log(errMsg.toString())
                    }
                });
            });
        </script>
    </body>
</html>