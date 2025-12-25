# KOBB(KubeJS Otherworld Block Builder)
A simple KubeJS plugin that enables adding new blocks that adapt the third eye effect from Occultism.
## Example
```javascript
StartupEvents.registry('block', event => {
    // Use the type 'otherworld' to register a otherworld block
    event.create('custom_otherworld_block', 'otherworld')
        .hardness(1)
        .resistance(1)
        .requiresTool(true)
        .tagBlock('minecraft:mineable/pickaxe')
        .tagBlock('minecraft:needs_iron_tool')
        // Additional methods provided by KOBB
        // Mandatory
        .uncoveredBlock('minecraft:gold_ore')
        .coveredBlock('minecraft:stone')
        .tier(1)
        // Optional
        .useCustomLoot()
})
```
## CurseForge
TODO