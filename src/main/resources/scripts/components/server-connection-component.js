Vue.component('server-connection-component', {
    props: ['global'],
    mounted() {
      pianoSocket.setToken("ewogICJzZXJ2ZXJJUCI6ICJsb2NhbGhvc3QiLAogICJwb3J0IjogMjAyMiwKICAiYSI6ICItOTAyNDMwMzA5Mzg1NzU2MzMyMyIsCiAgImIiOiAiLTc0Njk3NTUyMzkzNTMyNzIwMDUiCn0=")
      pianoSocket.connect()
    },
    data: function () 
    {
       return {
           token:"",
       }
   },
    methods: {
      connect()
      {
        let status = pianoSocket.setToken(this.token)
        if(status == false)
        {
          publichAlert("Invalid value of token!","danger")
          return
        }
        pianoSocket.connect()
      }
   },
    template: `
        
     <div class="input-group">
          <div class="input-group-prepend no-radious">
              <button class="btn btn-success" type="button" v-on:click="connect()">Connect</button>
          </div>
          <input type="text" class="form-control" placeholder="enter token here" v-model="token">
      </div>
      `})
 
 