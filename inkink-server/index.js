var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var running = false;
var initTime = 0
var players = [];
var board = [
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0]
]
var cleanBoard = [
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0,0,0,0]
]

server.listen(8080, function(){
	console.log("Server is now running...");
});

io.on('connection', function(socket){
	console.log(`[${socket.id}] `+"Player Connected!");
	// (when player connects) SOCKET ID
	socket.emit('socketID', { id: socket.id });
	// (when player connects) GET PLAYERS
	socket.emit('getPlayers', players);
	// (when player connects) GET BOARD
	socket.emit('getBoard', board);

	// PAINT TILE
	socket.on("paintTile", function(data){
		console.log(`[PAINT] (${data.row}, ${data.col})`)
		socket.broadcast.emit("paintTile", data);
		board[data.row][data.col] = data.color
	})
	// END GAME
	socket.on("endGame", function(data){
		if(running){
			console.log("endGame")
			board = JSON.parse(JSON.stringify(cleanBoard))
			players = []
			running = false
		}
	})
	// END GAME
	socket.on("startGame", function(data){
		if(!running){
			console.log("startGame")
			board = JSON.parse(JSON.stringify(cleanBoard))
			initTime = new Date().getTime()
			running = true
			socket.broadcast.emit('getInitTime', {init: initTime})
		}
	})
	// WEAPON USED
	socket.on("weaponUsed", function(data){
		console.log(`[WEAPON] (${data.row}, ${data.col})`)
		socket.broadcast.emit("weaponUsed", data);
	})
	// PLAYER MOVED (Isometric)
	socket.on("playerMoved", function(data){
		console.log(`[${socket.id}] MOVING`)
		socket.broadcast.emit("playerMoved", data);
		for(var i = 0; i < players.length; i++){
			if(players[i].id == data.id) {
				players[i].row = data.row
				players[i].col = data.col
			}
		}
	})
	// ADD PLAYER
	socket.on("addCurrentPlayer", function(data){
		console.log("NEW PLAYER ADDED: "+socket.id)
		var p = new player(socket.id, data.row, data.col, data.color)
		if(!players.find(e => e.id == socket.id))
			players.push(p);
		// Send new Player to all
		socket.broadcast.emit('newPlayer', p);
	})
	// DISCONNECT
	socket.on('disconnect', function(){
		console.log("Player Disconnected");
		socket.broadcast.emit('playerDisconnected', { id: socket.id });
		for(var i = 0; i < players.length; i++){
			if(players[i].id == socket.id){
				players.splice(i, 1);
			}
		}
		if(running && players.length===0){
			console.log("ending Game 0 players found")
			board = JSON.parse(JSON.stringify(cleanBoard))
			players = []
			running = false
		}
	});
});

function player(id, row, col, color){
	this.id = id;
	this.row = row;
	this.col = col;
	this.color = color;
}