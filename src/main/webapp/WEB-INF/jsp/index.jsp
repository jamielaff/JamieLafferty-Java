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
            <hr/>
            <div class="row" id="information">
                <div class="col-sm-3">
                    <h3>Total rounds played</h3>
                    <p>0</p>
                </div>
                <div class="col-sm-3">
                    <h3>Total wins for player 1</h3>
                    <p>0</p>
                </div>
                <div class="col-sm-3">
                    <h3>Total wins for player 2</h3>
                    <p>0</p>
                </div>
                <div class="col-sm-3">
                    <h3>Total ties</h3>
                    <p>0</p>
                </div>
            </div>
            <hr/>
            <div class="row" id="data">
                <div class="col-sm-3" id="displayGames">
                    <button id="createNewGame" type="button" class="btn btn-default btn-lg" style="width: 100%">
                            Create a new game
                    </button>

                    <ul class="list-group" id="activeGames">
                        <c:forEach var="game" items="${gamesList}">
                            <li class="list-group-item" role="button" id="${game.id}">
                                Game #${game.id}
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-sm-9" id="displayRoundsForGame">
                </div>
            </div>
        </div>

        <!-- Ajax function calls -->
        <script type="text/javascript">
            // Click event for createNewGame
            $("#createNewGame").click(function () {
                newGamePOST();
            });

            // Click event for viewGame
            activateGameLinkHook();

            // Handle POST for newGame
            function newGamePOST() {
                $.ajax({
                    type: "POST",
                    url: '/api/v1/games/create',
        
                    success: function(response) {
                        console.log("New game succesfully created!");
                        document.getElementById("activeGames").insertAdjacentHTML('afterbegin', '<li class="list-group-item" role="button" id="' + response.gameId + '">Game #' + response.gameId+ '</li>');
                        activateGameLinkHook();
                    },
        
                    failure: function(errMsg) {
                        console.log(errMsg.toString());
                        alert('There was a problem creating the game');
                    }
                });
            }

            // Hanle GET for getGame
            function gameGET(gameId) {
                var urlAddr = '/api/v1/games/' + gameId;
                $.ajax({
                    type: "GET",
                    url: urlAddr,

                    success: function(response) {
                        document.getElementById("displayRoundsForGame").innerHTML = "";
                        document.getElementById("displayRoundsForGame").insertAdjacentHTML('afterbegin', response);
                        activatePlayNewRoundHook();
                    },

                    failure: function(errMsg) {
                        console.log(errMsg.toString());
                        alert('There was a problem getting the rounds for game #'+gameId);
                    }
                })
            }

            // Hanle PUT for newRound
            function newRoundPUT() {
                var activeId = $('li.active').attr("id");
                var urlAddr = '/api/v1/games/' + activeId + '/rounds'

                $.ajax({
                    type: "POST",
                    url: urlAddr,

                    success: function(response) {
                        console.log(response);
                        document.getElementById("roundDetails").insertAdjacentHTML('afterend', response);
                    },

                    failure: function(errMsg) {
                        alert("nok");
                    }
                })
            }

            function activateGameLinkHook() {
                $(".list-group-item").click(function () {
                    $('li').removeClass("active");
                    $(this).addClass("active");

                    var gameId = $(this).attr("id");
                    gameGET(gameId);
                })
            }

            function activatePlayNewRoundHook() {
                $("#playNewRound").click(function () {
                    var activeId = $('li.active').attr("id");
                    newRoundPUT()
                });
            }
        </script>
    </body>
</html>