package chiharu.hagihara.zerorequest

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerPortalEvent
import org.bukkit.plugin.java.JavaPlugin


class ZeroRequest : JavaPlugin(), Listener {
    override fun onEnable() {
        // Plugin startup logic
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    //- ネザーゲート (使用不可/作成不可)
    //- エンドゲート (使用不可/作成不可)
    @EventHandler
    fun onPortal(e: PlayerPortalEvent) {
        val p = e.player
        if (!(p.hasPermission("portal.use"))) {
            e.isCancelled = true
        }
    }

    //- ドラゴンの卵 (殴ることによるTPを不可)
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val p = e.player
        val action: Action = e.action
        val block: Block? = e.clickedBlock
        if (e.clickedBlock!!.type == Material.DRAGON_EGG) {
            if (!(p.hasPermission("degg.break"))) {
                e.setUseInteractedBlock(Event.Result.DENY)
            }
        }
        if(e.action == Action.RIGHT_CLICK_BLOCK){
            if (block != null) {
                //- 金床 (使用不可/作成不可)
                when(block.type){
                    //- 金床 (使用不可/作成不可)
                    Material.ANVIL ->{e.isCancelled = true}

                    //- 醸造台 (使用不可/作成不可)
                    Material.BREWING_STAND ->{e.isCancelled = true}

                    //- エンチャント台 (使用不可/作成不可)
                    Material.ENCHANTING_TABLE ->{e.isCancelled = true}

                    //- 額縁 (破壊及び操作を不可に)
                    Material.ITEM_FRAME ->{e.isCancelled = true}

                    else-> {}
                }
            }
        }
    }

    //- エンドクリスタル (作成不可)
    @EventHandler
    fun BlockPlaceEvent(e: BlockPlaceEvent) {
        if (e.blockPlaced.type == Material.END_CRYSTAL) {
            e.isCancelled = true

        }
    }

    //- エンダーポータルフレーム (使用不可)
    @EventHandler
    fun PlaceEflame(e: BlockPlaceEvent) {
        val p = e.player
        if(e.block.type == Material.END_PORTAL_FRAME){
            if(!(p.hasPermission("eflame.place"))){
                e.isCancelled = true
            }
        }
    }

    //- 額縁 (破壊及び操作を不可に)
    @EventHandler
    fun BreakFlame(e: BlockBreakEvent){
        if(e.block.type == Material.ITEM_FRAME){
            e.isCancelled = true
        }
    }
}