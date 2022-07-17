Vue.component('midi-devices-component', {
    props: ['global'],
    mounted() {
        setup_midi(this)
    },
    data: function () 
    {
       return {
           devices:[],
           on_midi:null,
       }
   },
    methods: {
      set_device(id)
      {
        device = this.devices.find(e => e.id === id)
        device.active = !device.active
        if(device.active == true){
          this.current_device.handler.onmidimessage = this.on_midi
        }
        else
        {
          this.current_device.handler.onmidimessage = (e) =>{}
        }
      },
   },
    template: `
    <div>
      <div class="dropdown" style = "margin-top:1em; " >
        <button class="btn btn-success btn-lg dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >Midi output</button>
        <div class="dropdown-menu"  aria-labelledby="dropdownMenuButton" >
          <div class ="dropmenu-div "  v-for="device in devices" >
             <a  style ="color:black;">{{ device.name }}</a>
             <input class="form-check-input " style ="float:right;" type="checkbox" v-on:click="set_device(device.id)"  v-model="device.active">
          </div>
        </div>
      </div>
    </div>
      `})
 
 function setup_midi(context)
 {
    window.onload = function () {
        window.navigator.requestMIDIAccess().then(
          access => on_midi_success(access, context),
          access => on_midi_fail(access, context)
        );
      };
 }


 function on_midi_success(midi, context) 
 {
    console.log("[midi-success]");
    for(var device of midi.inputs.values())
    {
        context.devices.push(
            {
                id: device.id,
                name: device.name,
                handler : device,
                active:true,
            })
            context.on_midi = on_midi_message;
       
    }
 }
 function on_midi_fail(midiAccess, context) 
 {
    console.log("[midi-fail]");
    publichAlert("MIDI divices can't be loaded!","danger")
 }

function on_midi_message(msg) 
{
    if(msg.data[0] == 176)
    {
        if(msg.data[2] == 0)
        {
          publichNodeEvent(1,msg.data[1],0)
         
          return
        }
        if(msg.data[2]== 127)
        {
          alreaduRelese = false;
          publichNodeEvent(1,msg.data[1],1)
         
          return
        }
        return;
    }
    console.log("midi message", msg.data[0],msg.data[1],msg.data[2])
    if (msg.data[0] >= 144 && msg.data[0] <= 159) {
      if (msg.data[2] == 0) {
        publichNodeEvent(0, msg.data[1], 0);
      }
      else {
        publichNodeEvent(0, msg.data[1], msg.data[2]);
      }
      return;
    }
  
    if ((msg.data[0] >= 128 && msg.data[0]<=143) || msg.data[2] == 0) {
      publichNodeEvent(0, msg.data[1], 0);
      return;
    }
  }
    
    