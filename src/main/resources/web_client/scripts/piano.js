const keys = {};
const keysElement = document.getElementById("keys");
var key = 1;
var octave = 0;
for (let i = 1; i <= 88; i++) {
    if (i > 3 && i < 88) {
        key = (i - 4) % 12;
        octave = 1 + (i - 4) / 12;
    }
    if (i <= 3) {
        key = i + 8;
    }
    if(i == 88)
    {
        key = 0;
    }
    const keyModel = document.createElement("li");
    const mappedName = mapKey(key);
    if (key == 1 || key == 3 || key == 6 || key == 8 || key == 10) {
        keyModel.className = "black "+mappedName;
    }
    else {
        keyModel.className = "white "+mappedName;
       
    }
  
    let b = i + 20
    keyModel.catch = keyModel.style.background;
    keyModel.index =b;
    keyModel.onclick = (e) =>{ onMouseClick(b)};
    keysElement.append(keyModel);
    keys[b] =keyModel;
}
function pressKey(index)
{
    var model = keys[index];
    if(model == undefined)
    {
        return;
    }
    model.style.background = "green";
    sendNoteRequest(0,index,100);
    
}
function releseKey(index)
{
    var model = keys[index];
    if(model == undefined)
    {
        return;
    }
    sendNoteRequest(0,index,0);
    model.style.background =  model.catch;
}

function onMouseClick(index)
{
    pressKey(index);
    setTimeout(function() { releseKey(index); }, 100);
}

function mapKey(index) {
    switch (index) {
        case 0:
            return "c";
        case 1:
            return "c#";
        case 2:
            return "d";
        case 3:
            return "d#";
        case 4:
            return "e";
        case 5:
            return "f";
        case 6:
            return "f#";
        case 7:
            return "g";
        case 8:
            return "g#";
        case 9:
            return "a";
        case 10:
            return "a#";
        case 11:
            return "h";
    }
}