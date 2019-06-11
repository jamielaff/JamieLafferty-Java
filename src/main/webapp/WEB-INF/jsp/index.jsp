<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Rock Paper Scissors Game</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row" id="title">
                <div class="col-sm-12">
                    <h2>Rock Paper Scissors Game</h2>
                </div>
            </div>
            <div class="row" id="data">
                <div class="col-sm-6">
                    <p>hello 1</p>
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
                </div>
                <div class="col-sm-6">
                    <p>hello 2</p>
                </div>
            </div>
        </div>
        

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