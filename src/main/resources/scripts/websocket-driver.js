class PianoSocket {

  constructor() {
    this.connection = null
    this.token = null
    this.buffer = new ArrayBuffer(23);
    this.view = new DataView(this.buffer, 0);
  }
  setToken(token) {
    try
    {
      var decoded = atob(token);
      console.log(decoded)
      var payload = JSON.parse(decoded)
      this.url = "ws://" + payload.serverIP + ":" + payload.port;
      this.aKey = BigInt(payload.a);
      this.bKey = BigInt(payload.b);
      return true;
    }
    catch(e)
    {
      return false;
    }
  }
  connect() {

    if (this.connection != null) {
      this.connection.close()
    }

    this.connection = new WebSocket(this.url);
    this.connection.onopen = function (e) {
      console.log("[open] websocket");
      publichAlert("Connected to server!","success")
    };

    this.connection.onmessage = function (event) {
      console.log(`[message] Data received from server: ${event.data}`);

    };
    this.connection.onclose = function (event) {
      if (event.wasClean) {
        console.log(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
        publichAlert("Disconnected with server!","warning")
      } else {
        console.log('[close] Connection died');
        publichAlert("Can't connect to server","danger")
      }
    };
    this.connection.onerror = function (error) {
      console.log(`[error] ${error.message}`);
    };
  }

  sendDetailsRequest() {
    var buffer = new ArrayBuffer(20);
    var view = new DataView(buffer, 0);
    view.setInt32(0, 1);
    view.setBigUint64(4, this.aKey )
    view.setBigUint64(12,this.bKey )
    console.log(view);
    this.connection.send(buffer);
  }

  sendNoteRequest(midiEvent, noteNumber, velocity) {
    this.view.setInt32(0, 0);
    this.view.setBigUint64(4, this.aKey )
    this.view.setBigUint64(12, this.bKey )
    this.view.setInt8(20, midiEvent);
    this.view.setInt8(21, noteNumber);
    this.view.setInt8(22, velocity);
    this.connection.send(this.buffer);
  }
}

pianoSocket = new PianoSocket()






