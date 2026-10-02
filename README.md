# Battlefarer Forever https://fractalsoftworks.com/forum/index.php?topic=34274.0

A Starsector mod by **Sproginator** (original, for Starsector 0.35a), ported to
**Starsector 0.98a-RC8**.

Adds 2 whole new factions (the friendly **Battlefarer** with their Tradestation
and supply convoys in the Corvus system, and 9 mysterious **Unique** fleets),
along with 11 custom capital-grade ships you can fight — or command: pick the
new **"Battlefarer"** option in the new-game menu to start in the unique ship of
your choice (10000 credits, 3 character points, basic supplies).

## Installation

1. Copy the `Battlefarer Forever` folder into your Starsector `mods` directory.
2. Enable it in the launcher's mod manager.
3. Requires Starsector 0.98a-RC8. No other mod dependencies.

## Porting notes (0.35a → 0.98a-RC8, version 0.3)

- World generation rebuilt on the modern API: the Tradestation is a
  `addCustomEntity` station with a full market (open / black market / storage),
  stocked with the original cargo.
- The old character-creation plugin (removed API) is replaced by a
  `data/campaign/rules.csv` new-game option.
- Faction files updated with the keys 0.98a requires (`logo`, `crest`,
  `knownShips`, `knownWeapons`, ...).
- Fleet spawns use `createEmptyFleet`; spawn points extend a bundled copy of
  core's `BaseSpawnPoint`.
- Weapon emitters implement the current `CombatEntityAPI`; missing weapon
  sound ids remapped to existing core sounds.
- Ship/weapon data CSVs are unchanged (0.98a reads columns by header name).

See `changelog.txt` for the full list.

## Credits

- **Sproginator** — original mod (ships, weapons, factions, graphics).
- Port to 0.98a-RC8 by [Camellia895](https://github.com/Camellia895).

- <img width="264" height="408" alt="image" src="https://github.com/user-attachments/assets/7e1aab65-3187-46f1-8838-ad4a0681ce0c" />
<img width="1369" height="800" alt="image" src="https://github.com/user-attachments/assets/8d25cc9c-e16b-4424-8520-7107cdd415fb" />
<img width="1362" height="798" alt="image" src="https://github.com/user-attachments/assets/978ee267-33fd-487c-af8b-8873f4dff8dd" />

