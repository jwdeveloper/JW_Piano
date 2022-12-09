
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

### Desktop app configuration, `config.yml` > `plugin.websocket.custom-id`
 -  Make sure port you are trying to use is open
 -  When you've got problems with connection try to change `plugin.websocket.custom-id` or  `plugin.websocket.port`
 -  Check if you need to create new port in the server hosting panel and then set in to `plugin.websocket.port`
 -  When your server use proxy use Proxy IP to `plugin.websocket.custom-id`
 -  When you server IP has port ignore port. Example: 

Wrong: `craftplayer.com:22225`

Correct: `craftplayer.com`

 -  When you are running server locally set value to `localhost` to `plugin.websocket.custom-id`
 -  When above solutions does not help set IP that you use in Minecraft server lists to `plugin.websocket.custom-id`
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

plugin:
  version: 1.1.4
  resourcepack:
    url: https://download.mc-packs.net/pack/6fd6764e874d973fecd2d6debce416671399782b.zip
    load-on-join: false
  websocket:
    run: true
    port: 443
    custom-id: ''
  saving-frequency: 5
  language: en
piano-config:
  models-limit: 10
  minDistanceToPiano: 3.0
  maxDistanceFromPiano: 3.0
  maxDistanceFromKeys: 2.0

```

![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/commands.png)

``` yaml


commands: 
# /piano
  piano: 
    children: 
      - update
      - resourcepack
      - lang
    permissions: 
      - piano.commands.piano
    description: opens GUI where you can Create/Edit/Delete pianos
    usage: /piano
# /piano update
  update: 
    permissions: 
      - piano.commands.update
    description: download plugin latest version, can be trigger both by player or console
    usage: /piano update

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
      - piano.effects
      - piano.pedal
      - piano.bench
      - piano.teleport
      - piano.detect-key
      - piano.desktop-client
      - piano.show-gui-hitbox

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

  piano.effects: 
    description: player set piano particles in GUI

  piano.pedal: 
    description: player enable/disable piano pedal in GUI

  piano.bench: 
    description: player enable/disable piano bench in GUI

  piano.teleport: 
    description: player teleport to piano in GUI

  piano.detect-key: 
    description: player is able to click at the piano keys

  piano.desktop-client: 
    description: player can use desktop-client

  piano.show-gui-hitbox: 
    description: player can disable or enable gui hitbox

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


```
