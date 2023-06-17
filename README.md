<h1 align="center"><img height="35" src="https://emoji.gg/assets/emoji/7333-parrotdance.gif"> Cosmos</h1>
<div align="center">

![GitHub Repo stars](https://img.shields.io/github/stars/IllusionTheDev/ItemRarity?style=for-the-badge) 
![GitHub watchers](https://img.shields.io/github/watchers/IllusionTheDev/ItemRarity?style=for-the-badge) 
![GitHub issues](https://img.shields.io/github/issues/IllusionTheDev/ItemRarity?style=for-the-badge)

</div>

#### This project consists of a basic Rarity plugin that adds client-sided lore to all items

As of June 17th, 2023 this project is archived and read-only. The code shall be used for educational purposes.

For developers attempting to replicate:
The process of client-sided lore (and item modification in general) is quite basic. The idea is to intercept the outgoing packets (WINDOW_ITEMS and SET_SLOT) and modify the items contained in this packets. 
Keep in mind that players in the creative mode can copy the fake items, so there's some logic to strip away duplicate contents by removing the lore before re-adding it.

------------

### Technologies Used:
- SpigotAPI
- Gradle

#### Plugin Hooks
- ProtocolLib

------------
