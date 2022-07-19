!This is Alpha version it could still constains bugs!

JW Piano is a one in own kind plugin that give you opportunity to play piano with your firends! But this is only top on the iceberg.
If you want to play MIDI file or send notes from real piano, download JW Piano client app and connect to your server!




[MEDIA=youtube]1WWlWJqWo9s[/MEDIA]
[MEDIA=youtube]x5vCJuvdg8Q[/MEDIA]

Installation [Video tutorial soon]:
Download and put into plugins folder
Run server
Enter command /piano
some funtions will works only for players with OP or permission piano.manager
Click crafitingbox icon to create new piano
Now you can play piano in minecraft :D

Do you want expirence real piano??? No problem! [Video tutorial soon]
If you are using local server open /plugins/JW_Piano/Settings.json
Set customServerIp to 'localhost'
Enter /refresh command in the server console
Enter command /piano
Click on the nether campfire
Download JW Piano Client
Run JW_Piano_Client.exe
After running website should open in your browser
Go back to minecraft
Enter command /piano
Click on your piano
Click on the diamand icon
Open chat and [click here] to copy token value
Go back to website, paste token into input field and click connect
That's it! Now you can send MIDI output to Minecraft server
Have fun :D


/piano
  - open gui where you can create/remove/modify pianos

/piano texturepack
  - get texturepack





piano.manager
- creating new pianos
   - removing pianos
   - changing piano style [None, UprightPiano, GrandPiano]
   - changing style of piano
   - disable/enable piano




[code=YAML]Settings:
  minDistanceToPiano: 3.0       # maximum piano radious
  maxDistanceFromPiano: 3.0     # minimal piano radious
  maxDistanceFromKeys: 2.0      # how close player can hit keys
  autoDownloadTexturepack: true # downloading texturepack when a player join
  runPianoPlayerServer: true    # running websocket server for connecting player with JW Piano client
  customServerIp: ''            # when you running plugin on local machine set this to 'localhost'
  webSocketPort: 2022           # set port for websocket
[/code]




