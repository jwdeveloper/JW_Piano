
![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/banner.png)

<p align="center">
<a href="https://discord.gg/2hu6fPPeF7"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png"  /></a><a href="https://github.com/jwdeveloper/JW_Instruments"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png"  /></a><a href="https://www.spigotmc.org/resources/piano.103490/"><img src="https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png"  /></a></p>
JW Piano is a one in own kind plugin that give you opportunity to play piano with your friends! But this is only top on the iceberg.
If you want to play MIDI file or send notes from real piano, download desktop app and connect to your server!

### Common problems:
 -  When you've got error 'Can't connect to server' on desktop app open new Port at your hosting panel. Then go to plugins/jw_piano/config.yml and change value of port
 -  When you have some problems with resourcepack download it directly

[Download Desktop App](https://github.com/jwdeveloper/JW_Piano_Desktop/releases/latest/download/JW_Piano_Desktop.jar)


[Download Resourcepack](https://github.com/jwdeveloper/JW_Instruments/releases/latest/download/instrumentpack.rar)



[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/F4iKXAMIioo/0.jpg)](https://www.youtube.com/watch?v=F4iKXAMIioo&feature=emb_logo&ab_channel=JW)


[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/AxljLMjh4Ac/0.jpg)](https://www.youtube.com/watch?v=AxljLMjh4Ac&feature=emb_logo&ab_channel=JW)


![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/style.png)


![alt text](https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/webclient.png)


![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/configuration.png)

``` yaml
#
# plugin.language
# -> If you want add your language open `languages` folder copy `en.yml` call it as you want 
# set `language` property to your path name and /reload server 
#
# plugin.saving-frequency
# -> Determinate how frequent data is saved to files, value in minutes
#
#
# piano-config.models-limit
# -> Limit of pianos that could be spawn on the server
#
# plugin.auto-download-resourcepack
# -> automatically download texture pack when player joins to server
#
# piano-server.enable
# -> runs piano server so players could join with desktop app
#
# piano-server.customer-server-ip
# -> When value is empty IP of your server will be detected automatically 
#  Common issues: 
#  - if you server IP has port ignore port WRONG -> craftplayer.com:22225  GOOD -> craftplayer.com
#  - if you are running server locally set value to 'localhost'
#  - if your server use proxy put here proxy IP
#  - if above solutions does not help set IP that players are connecting to
#
# piano-server.port
# -> Make sure port is open on your hosting
#

plugin:
  version: 1.1.3
  language: en
  saving-frequency: 5
  auto-download-resourcepack: true
piano-config:
  models-limit: 10
  minDistanceToPiano: 3.0
  maxDistanceFromPiano: 3.0
  maxDistanceFromKeys: 2.0
piano-server:
  enable: true
  customer-server-ip: ''
  port: 2022

```

![alt text](https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/banners/commands.png)

``` yaml


commands: 
# /piano
  piano: 
    children: 
      - lang
      - update
    description: opens GUI where you can Create/Edit/Delete pianos
    usage: /piano
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
      - piano.effects
      - piano.pedal
      - piano.bench
      - piano.teleport
      - piano.detect-key
      - piano.desktop-client

  piano.create: 
    description: player create piano

  piano.remove: 
    description: player remove piano

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

# commands
  commands: 
    description: Default permission for commands
    children: 
      - piano.commands.lang
      - piano.commands.update

  piano.commands.lang: 
    description: Allow player to change plugin language
    default: op

  piano.commands.update: 
    description: players with this permission can update plugin
    default: op

# gui
  gui: 
    description: Default permission for gui


```
