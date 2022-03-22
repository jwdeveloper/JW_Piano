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

