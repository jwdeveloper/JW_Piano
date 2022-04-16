WebMidi
.enable()
.then(onEnabled)
.catch(err => alert(err));

// Function triggered when WebMidi.js is ready
function onEnabled() {

// Display available MIDI input devices
if (WebMidi.inputs.length < 1) {
    document.body.innerHTML+= "No device detected.";
  } else {
    WebMidi.inputs.forEach((device, index) => {
      document.body.innerHTML+= `${index}: ${device.name} <br>`;
    });
  }
  
  const mySynth = WebMidi.inputs[0];
  // const mySynth = WebMidi.getInputByName("TYPE NAME HERE!")
  
  mySynth.channels[1].addListener("noteon", e => {
    document.body.innerHTML+= `${e.note.name} <br>`;
  });
  WebMidi.inputs[0].addListener("noteon", e => {
    document.body.innerHTML+= `${e.note.name} <br>`;
  }, {channels: [1, 2, 3]});

}



window.onload = function () {
    window.navigator.requestMIDIAccess().then(
        x => onMIDISuccess(x),
        x => onMIDIFail(x)
    );
};
function onMIDIFail(midiAccess) {
    console.log("fail", midiAccess);

}
function onMIDISuccess(midiAccess) {
    showMidiDefices(midiAccess);
}
function showMidiDefices(midiDriver)
{
    const devices = document.getElementById("midi-devices");
    for (var input of midiDriver.inputs.values())
    {
        const device = document.createElement("a");
        device.className = "dropdown-item";
        device.MIDI =  input;
        device.textContent = input.name +" "+ input.id;
        input.onmidimessage = onMIDIMessage;
        devices.append(device);
    }
    
}

function onMIDIMessage(midiMessage) 
{
    //input.onmidimessage = onMIDIMessage;
    var data = midiMessage.data;
      console.log(data)
      console.log("press"+data[1])
    if(data[0]== 144 )
    {
      
        
        if(data[2]== 0)
        {
            releseKey(data[1]);
        }
        else
        {
            pressKey(data[1]);
        }
        return;
    }

    if(data[0]== 128 || data[2]==0)
    {
        releseKey(data[1]);
        console.log("relese"+data[1])
        return;
    }
   
}

