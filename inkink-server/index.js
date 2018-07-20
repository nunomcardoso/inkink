var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];

server.listen(8080, function(){
	console.log("Server is now running...");
});

io.on('connection', function(socket){
	console.log(`[${socket.id}] `+"Player Connected!");
	// (when player connects) SOCKET ID
	//socket.emit('socketID', { id: socket.id });
	// (when player connects) GET PLAYERS
	socket.emit('getPlayers', players);

	// PAINT TILE
	socket.on("paintTile", function(data){
		console.log(`[PAINT] (${data.row}, ${data.col})`)
		socket.broadcast.emit("paintTile", data);
	})
	// PLAYER MOVED
	//socket.on("playerMoved", function(data){
	//	console.log(`[${socket.id}] MOVING`)
	//	socket.broadcast.emit("playerMoved", data);
	//	for(var i = 0; i < players.length; i++){
	//		if(players[i].id == data.id) {
	//			players[i].x = data.x
	//			players[i].y = data.y
	//			players[i].tileH = data.tileH
	//		}
	//	}
	//})
	// ADD PLAYER
	socket.on("addCurrentPlayer", function(data){
		console.log("NEW PLAYER ADDED: "+socket.id)
		var p = new player(socket.id, data.x, data.y, data.tileH, data.color)
		players.push(p);
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
	});
});

function player(id, x, y, tileH, color){
	this.id = id;
	this.x = x;
	this.y = y;
	this.tileH = tileH;
	this.color = color;
}