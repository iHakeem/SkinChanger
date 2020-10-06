# SkinChanger
Basically, it is an uncompleted plugin there is no error handling but anyways 
It does change the skin by typing /nick <player> and whoever typed this command his skin will change to the player he typed

## GIF
![dd](https://j.gifs.com/3QAqZR.gif)

## Skin changing flow
- First, get the player UUID using any API ( i used Mojang API )
- After you get UUID get player texture data through Mojang API
- Then remove player texture and replace it with a new texture
- Then you have to respawn the player with the new properties

**Note: You can use packets to respawn player without showing the menu of respawning or whatever**

## Requests
| URL | Method |
| - | - |
| https://api.mojang.com/users/profiles/minecraft/{playerName} | `GET` |
| https://sessionserver.mojang.com/session/minecraft/profile/{UUID}?unsigned=false | `GET` |

## Parameters
- `playerName`: Target player username
- `UUID`: Target player UUID


I will try to update this project but no promises :p
