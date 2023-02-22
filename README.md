# BlossomEnderChest

BlossomEnderChest is a Minecraft Fabric mod in the Blossom-series mods that provides /echest command

## Table of contents

- [Dependencies](#dependencies)
- [Config](#config)
- [Commands & their permissions](#commands--their-permissions)
- [Translation keys](#translation-keys)

## Dependencies

* [BlossomLib](https://github.com/BlossomMods/BlossomLib)
* [fabric-permissions-api](https://github.com/lucko/fabric-permissions-api) / [LuckPerms](https://luckperms.net/) /
  etc. (Optional)

## Config

This mod's config file can be found at `config/BlossomMods/BlossomEnderChest.json`, after running the server with
the mod at least once.

`commands`: String\[] - list of commands that trigger this mod  
`nameTranslationKey`: String - translation key for the ender chest name (default: `"blossom.ender-chest.name"`), can
be set to `"container.enderchest"` to use vanilla translations  
`nameColor`: String - the text color for the ender chest name

## Commands & their permissions

- `/<command>` - opens the player's ender chest  
  Permission: `blossom.ender-chest` (default: true)

A player with the permission `blossom.ender-chest.disallowed` (default: false) will not be able to use the commands.

## Translation keys

Only keys with available arguments are shown, for full list, please see
[`src/main/resources/data/blossom/lang/en_us.json`](src/main/resources/data/blossom/lang/en_us.json)
