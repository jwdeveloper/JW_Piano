
More informations 
https://www.spigotmc.org/resources/piano.103490/
JW Piano is a one in own kind plugin that give you opportunity to play piano with your firends! But this is only top on the iceberg.
If you want to play MIDI file or send notes from real piano, download JW Piano client app and connect to your server!

Plugin showcase
https://www.youtube.com/watch?v=F4iKXAMIioo&t=0s&ab_channel=JW

Commands
```
/piano
  - open gui where you can create/remove/modify pianos

/piano update
  - updates plugin only players with 'piano' or 'piano.update' can use it
```

Permissions
permissions:
```yaml
permissions:
- piano
  childern:
  - piano.remove
  - piano.create
  - piano.volume
  - piano.rename
  - piano.appearance
  - piano.active
  - piano.effects
  - piano.update
```




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




