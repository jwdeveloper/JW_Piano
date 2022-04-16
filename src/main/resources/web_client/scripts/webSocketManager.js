


var socket = new WebSocket("wss://localhost:2022");

socket.onopen = function (e) {
  console.log("[open] Connection established");
  sendDetailsRequest()
};

socket.onmessage = function (event) {
  console.log(`[message] Data received from server: ${event.data}`);

};

socket.onclose = function (event) {
  if (event.wasClean) {
    console.log(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
  } else {
    console.log('[close] Connection died');
  }
};

socket.onerror = function (error) {
  console.log(`[error] ${error.message}`);
};

var params = getUrlParams();
var webSocketUrl = getWebSocketUrl(params);
var aKey = BigInt(params.a);
var bKey = BigInt(params.b);
console.log("a",params.a);
console.log("ANote",aKey);

console.log("b",params.b);
console.log("BNote",bKey);
function sendDetailsRequest() {
  var buffer = new ArrayBuffer(20);
  var view = new DataView(buffer, 0);
  view.setInt32(0, 1);
  view.setBigUint64(4, params.a)
  view.setBigUint64(12, params.b)
  console.log(view);
  socket.send(buffer);
}


function sendNoteRequest(midiEvent, noteNumber, velocity) {
  var buffer = new ArrayBuffer(23);
  var view = new DataView(buffer, 0);
  view.setInt32(0, 0);
  view.setBigUint64(4, aKey)
  view.setBigUint64(12, bKey)
  view.setInt8(20, midiEvent);
  view.setInt8(21, noteNumber);
  view.setInt8(22, velocity);

  socket.send(buffer);
}

function getUrlParams() {
  var url = window.location.href;
  var start = url.indexOf("payload");
  url = url.substring(start);
  var separator = url.indexOf("=")
  var payload = url.substring(separator + 1);

  var decoded = atob(payload);
  console.log("Decoded",decoded);
  return JSON.parse(decoded);
}

function getWebSocketUrl(dto)
{ 
  return "ws://"+dto.serverIP+":"+dto.port;
}