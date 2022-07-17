




alertHandler = null
function setAlertsHandler(handler)
{
    alertHandler = handler
}

function publichAlert(message, type)
{
    console.log(alertHandler)
    alertHandler.add(message,type)
}


noteHandler = null
function setNoteHanlder(handler)
{
    noteHandler = handler
}

function publichNodeEvent(packetId, nodeIndex, velocity)
{
    pianoSocket.sendNoteRequest(packetId, nodeIndex, velocity);
    if(packetId != 0)
     return
    noteHandler.handle_note_event(nodeIndex, velocity)
}