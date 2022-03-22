var socket = new WebSocket("wss://s982111.csrv.pl:2022");

socket.onopen = function(e) {
  console.log("[open] Connection established");
  console.log("Sending to server");
  sendDetailsRequest()
  };
  
  socket.onmessage = function(event) {
    console.log(`[message] Data received from server: ${event.data}`);
    socket.send("My name is John");
  
  };
  
  socket.onclose = function(event) {
    if (event.wasClean) {
      console.log(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
    } else {
      console.log('[close] Connection died');
    }
  };
  
  socket.onerror = function(error) {
    console.log(`[error] ${error.message}`);
  };


  function sendDetailsRequest()
  {
      var buffer = new ArrayBuffer(20);
      var view = new DataView(buffer, 0);
      view.setInt32(0, 1);
      view.setBigUint64(4,"-599037420688030684")
      view.setBigUint64(12,"-4688173080563069226")
      socket.send(buffer);
  }


  function sendNoteRequest(midiEvent, noteNumber, velocity)
  {
      var buffer = new ArrayBuffer(23);
      var view = new DataView(buffer, 0);
      view.setInt32(0, 0);
      view.setBigUint64(4,"-599037420688030684")
      view.setBigUint64(12,"-4688173080563069226")
      view.setInt8(20, midiEvent);
      view.setInt8(21, noteNumber);
      view.setInt8(22, velocity);
      socket.send(buffer);
  }