
var currentPlayer;

function startGame() {
  const formData = {"type": "start"};
  let URL = '/Game421/action';
  axios.post(URL,formData)
        .then(response => {
            if (response.status == 200) {
                     fetchStatus();
            }
        }).catch(error => console.error(error));
};

function toss(item) {
  if (currentPlayer != null) {
      if (item == null) {
          axios.get('/Game421/randomitem')
                  .then(response => {
                      if (response.status == 200){
                            postToss(response.data.item);
                      }
                 }).catch(error => console.error(error));
      }
      if (item != null) {
            postToss(item);
      }
  } else {
      console.log('current player is null');
  }
};

function postToss(item) {
     const formData = {"type": "play", "token": currentPlayer, "item": item};
           var URL = '/Game421/action';
           axios.post(URL,formData)
               .then(response => {
                   if (response.status == 200) {
                       fetchStatus();
                   }
               }).catch(error => console.error(error));

};

function stopGame() {
    const formData = {"type": "reset"};
    let URL = '/Game421/action';
    axios.post(URL,formData)
        .then(response => {
            if (response.status == 200) {
                      fetchStatus();
            }
        }).catch(error => console.error(error));

};

function fetchStatus() {
  axios.get('/Game421/info')
        .then(response => {
            if (response.status == 200){
                var data = response.data;
                var status = data.status;
                var firstPlayer = JSONPath({path: "$.players[0]", json: data});
                var secondPlayer = JSONPath({path: "$.players[1]", json: data});
                $('#status').text(status[1]);

                if (status[0] === "PLAYING") {
                      $('#player1').text(firstPlayer[0].name);
                      $('#player2').text(secondPlayer[0].name);
                      setTosses(data);
                      $('#start_button').prop('disabled', true);
                      disableItemButtons(false);
                      var turn = data.turn;
                      if (firstPlayer[0].token === turn) {
                           currentPlayer = turn;
                           $('#player1status').text('playing...');
                           $('#player2status').text('');
                      } else if (secondPlayer[0].token === turn) {
                           currentPlayer = turn;
                           $('#player2status').text('playing...');
                           $('#player1status').text('');
                      } else {
                           currentPlayer = null;
                           $('#player1status').text('');
                           $('#player2status').text('');
                      }
                } else if (status[0] === "OVER") {
                       currentPlayer = null;
                       setTosses(data);
                      $('#winner').text(data.winner.name);
                      resetScreen();
                } else if (status[0] === "WAITING_START") {
                      $('#player1').text('');
                      $('#player2').text('');
                      $('#toss1').text('');
                      $('#toss2').text('');
                      $('#winner').text('');
                      resetScreen();
                }
            }
        }).catch(error => console.error(error));
};

function resetScreen() {
    $('#player1status').text('');
    $('#player2status').text('');
    $('#start_button').prop('disabled', false);
    disableItemButtons(true)
}

function setTosses(data) {
   $('#toss1').text(JSONPath({path: "$.hits[0]", json: data}));
   $('#toss2').text(JSONPath({path: "$.hits[1]", json: data}));
}

function disableItemButtons(state) {
    $('#rock').prop('disabled', state);
    $('#paper').prop('disabled', state);
    $('#scissor').prop('disabled', state);
    $('#random').prop('disabled', state);
}


