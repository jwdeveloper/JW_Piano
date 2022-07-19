!This is Alpha version it could still constains bugs!

JW Piano is a one in own kind plugin that give you opportunity to play piano with your firends! But this is only top on the iceberg.
If you want to play MIDI file or send notes from real piano, download JW Piano client app and connect to your server!


#Commands
/piano
  - open gui where you can create/remove/modify pianos

/piano texturepack
  - get texturepack




#Permissions
piano.manager
- creating new pianos
   - removing pianos
   - changing piano style [None, UprightPiano, GrandPiano]
   - changing style of piano
   - disable/enable piano




```yaml
Settings:
  minDistanceToPiano: 3.0       # maximum piano radious
  maxDistanceFromPiano: 3.0     # minimal piano radious
  maxDistanceFromKeys: 2.0      # how close player can hit keys
  autoDownloadTexturepack: true # downloading texturepack when a player join
  runPianoPlayerServer: true    # running websocket server for connecting player with JW Piano client
  customServerIp: ''            # when you running plugin on local machine set this to 'localhost'
  webSocketPort: 2022           # set port for websocket
```




