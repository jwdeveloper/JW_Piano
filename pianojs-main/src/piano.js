const keys = {};
const keysElement = document.getElementById("keys");
var key = 1;
var octave = 0;
for (var i = 1; i <= 88; i++) {
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
  
    const dupa = copy(i)
   
    var b = { index:copy(i)+20}
    console.log(b)
    keyModel.catch = keyModel.style.background;
    keysElement.index = b.index;
    keysElement.onclick = (e) =>{ onMouseClick(b.index)};
    keysElement.append(keyModel);
    keys[b.index] =keyModel;
}
function copy(x) {
    return JSON.parse( JSON.stringify(x) );
}
function pressKey(index)
{
    var model = keys[index];
    console.log(index,model);
    if(model == undefined)
    {
        return;
    }
    sendNoteRequest(0,index,100);
    model.style.background = "green";
}
function releseKey(index)
{
    var model = keys[index];
    console.log(index,model);
    if(model == undefined)
    {
        return;
    }
    sendNoteRequest(0,index,0);
    model.style.background =  model.catch;
}

function onMouseClick(index)
{
    var number = 0
    console.log(index);
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