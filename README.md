
![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/banner.png)

<p align="center">
<a href="https://discord.gg/2hu6fPPeF7"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png"  /></a><a href="https://github.com/jwdeveloper/JW_Piano"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png"  /></a><a href="https://www.spigotmc.org/resources/piano.103490/"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png"  /></a></p>

A Minecraft plugin that adds a playable piano to the game would allow players to interact with a piano in the game world.
This piano would function just like a real-life piano, allowing players to play individual notes or full melodies using their real keyboard with Desktop app connection.
The plugin would add a new level of creativity and musical expression to the game, allowing players to showcase their musical talents and compose their own tunes.
Additionally, the piano could be used as a decorative item in the game world, adding a new element of realism and immersion to the game.
Overall, this plugin would be a fun and unique addition to Minecraft, providing players with a new way to interact with the game world and express themselves.


[Download Desktop App](https://github.com/jwdeveloper/JW_Piano_Desktop/releases/latest/download/JW_Piano_Desktop.jar)


[Download Resourcepack](https://download.mc-packs.net/pack/6fd6764e874d973fecd2d6debce416671399782b.zip)


<details>
<summary>Common issues</summary>


### Resourcepack
 -  When you have some problems with resourcepack download it directly

### Desktop app configuration, `config.yml` > `plugin.websocket.server-ip`
 -  Make sure port you are trying to use is open
 -  When you've got problems with connection try to change `plugin.websocket.server-ip` or  `plugin.websocket.port`
 -  Check if you need to create new port in the server hosting panel and then set in to `plugin.websocket.port`
 -  When your server use proxy use Proxy IP to `plugin.websocket.server-ip`
 -  When you server IP has port ignore port. Example: 

Wrong: `craftplayer.com:22225`

Correct: `craftplayer.com`

 -  When you are running server locally set value to `localhost` to `plugin.websocket.server-ip`
 -  When above solutions does not help set IP that you use in Minecraft server lists to `plugin.websocket.server-ip`
</details>


[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/F4iKXAMIioo/0.jpg)](https://www.youtube.com/watch?v=F4iKXAMIioo&t=2s&ab_channel=JW)


[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/PSbwsbX7xc0/0.jpg)](https://www.youtube.com/watch?v=PSbwsbX7xc0&t=27s&ab_channel=JW)


![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/style.png)


![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/webclient.png)


![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/configuration.png)

``` yaml
#
# piano-config.models-limit
# -> Limit of pianos that could be spawn on the server
#
# plugin.resourcepack.url
#    If you need to replace default resourcepack with your custom one
#    set this to link of you resourcepack
#    ! after plugin update make sure your custom resourcepack is compatible !
#
# plugin.resourcepack.load-on-join
#    Downloads resourcepack when player joins to server
#
# plugin.websocket.run
#    When false websocket will not run 
#
# plugin.saving-frequency
#    Determinate how frequent data is saved to files, value in minutes
#
# plugin.language
#    If you want add your language open `languages` folder copy `en.yml` call it as you want \n" +
#  "set `language` property to your file name and /reload server 
#
# plugin.websocket.port
#    Set port for websocket
# ! Make sure that port is open
# ! When you have server on hosting, generate new port on the hosting panel
#
#
# plugin.websocket.custom-id
#    Set own IP for websocket, by default plugin use IP of your server
# ! When you are using proxy set here proxy IP
# ! When you are running plugin locally on your PC, set 'localhost'
# ! When default IP not works try use IP that you are using in minecraft server list
#
#
# plugin.websocket.server-ip
#    Set own IP for websocket, by default plugin use IP of your server
# ! When you are using proxy set here proxy IP
# ! When you are running plugin locally on your PC, set 'localhost'
# ! When default IP not works try use IP that you are using in minecraft server list
#
#
# plugin.websocket.server-ip
#    Set own IP for websocket, by default plugin use IP of your server
# ! When you are using proxy set here proxy IP
# ! When you are running plugin locally on your PC, set 'localhost'
# ! When default IP not works try use IP that you are using in minecraft server list
#
#
# <PluginConfig>
# 
# piano.models-limit
#  Limit of pianos that could be spawn on the server
# 
# piano.piano-range
#  Piano became interactive when player distance to piano is lower or equal that `piano-range`
# 
# 
# skins.name
#  test
# 
# skins.custom-model-id
#  test
# 
# skins.material
#  test
# 
# sounds.namespace
#  Name of the folder that sounds are save in resourcepack
# 
# 
# sounds.sound-category
#  Define sound category from minecraft settings that sound will play in.
#  Allowed categories [AMBIENT, BLOCKS, HOSTILE, MASTER, MUSIC, NEUTRAL, RECORDS, VOICE, WEATHER]
# 
# </PluginConfig>

plugin:
  version: 1.2.0
  resourcepack:
    url: https://download.mc-packs.net/pack/6fd6764e874d973fecd2d6debce416671399782b.zip
    load-on-join: false
  websocket:
    run: true
    port: 443
    server-ip: 5.173.198.132
  saving-frequency: 5
  language: en
piano:
  models-limit: 10
  piano-range: 3.0
skins:
  value-1:
    name: none
    custom-model-id: 0
    material: AIR
  value-2:
    name: upright piano
    custom-model-id: 108
    material: STICK
  value-3:
    name: grand piano
    custom-model-id: 109
    material: STICK
  value-4:
    name: electric piano
    custom-model-id: 110
    material: STICK
sounds:
  value-1:
    name: Default
    namespace: minecraft
    sound-category: VOICE

```

![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/commands.png)

``` yaml


commands: 
# /piano
  piano: 
    children: 
      - resourcepack
      - lang
      - Create
      - update
    permissions: 
      - piano.commands.piano
    description: opens GUI where you can Create/Edit/Delete pianos
    usage: /piano
# /piano resourcepack
  resourcepack: 
    description: downloads plugin resourcepack
    usage: /piano resourcepack

# /piano lang <language>
  lang: 
    permissions: 
      - piano.commands.lang
    arguments: 
      - language:
          type: text
          description: select language
          options: 
              - en
              - kr
              - pl
    description: Changes plugin languages, changes will be applied after server reload. Change be use both be player or console
    usage: /piano lang <language>

# Create
  Create: 

# /piano update
  update: 
    permissions: 
      - piano.commands.update
    description: download plugin latest version, can be trigger both by player or console
    usage: /piano update



```

![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/permissions.png)

``` yaml
permissions: 

# plugin
  piano: 
    description: Default permission for plugin
    children: 
      - commands
      - gui
      - piano.create
      - piano.remove
      - piano.volume
      - piano.rename
      - piano.skin
      - piano.active
      - piano.sound
      - piano.effects
      - piano.pedal
      - piano.bench
      - piano.bench.active
      - piano.teleport
      - piano.detect-key
      - piano.desktop-client
      - midi-player

  piano.create: 
    description: player can create piano

  piano.remove: 
    description: player can remove piano

  piano.volume: 
    description: player can edit piano volume in GUI

  piano.rename: 
    description: player can rename piano in GUI

  piano.skin: 
    description: player change piano skin in GUI

  piano.active: 
    description: player change piano state in GUI

  piano.sound: 
    description: player can change piano sound

  piano.effects: 
    description: player set piano particles in GUI

  piano.pedal: 
    description: player enable/disable piano pedal in GUI

  piano.bench: 
    description: open bench GUI

  piano.bench.active: 
    description: change visibility of bench

  piano.teleport: 
    description: player teleport to piano in GUI

  piano.detect-key: 
    description: player is able to click at the piano keys

  piano.desktop-client: 
    description: player can use desktop-client

# commands
  commands: 
    description: Default permission for commands
    children: 
      - piano.commands.lang
      - piano.commands.update
      - piano.commands.piano

  piano.commands.lang: 
    description: Allow player to change plugin language
    default: op

  piano.commands.update: 
    description: players with this permission can update plugin
    default: op

  piano.commands.piano: 
    description: player open gui to create/remove piano

# gui
  gui: 
    description: Default permission for gui

  midi-player: 
    description: opens midi player in gui
    children: 
      - midi-player.play
      - midi-player.song
      - midi-player.type
      - midi-player.speed

  midi-player.play: 
    description: can play/stop songs 

  midi-player.song: 
    description: can set current song, load song, delete song, skip

  midi-player.type: 
    description: change midi player type

  midi-player.speed: 
    description: change speed of midi song


```
