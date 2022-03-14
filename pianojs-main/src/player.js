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
        console.log(input);
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
    console.log(midiMessage);
    var data = midiMessage.data;

    if(data[0]== 144)
    {
        pressKey(data[1]);
    }

    if(data[0]== 128 || data[2]==0)
    {
        releseKey(data[1]);
        return;
    }
   
}

