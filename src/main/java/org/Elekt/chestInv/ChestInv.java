package org.Elekt.chestInv;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.awt.print.Paper;

import java.nio.file.Path;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.md_5.bungee.api.chat.TextComponent.fromLegacyText;

public final class ChestInv extends JavaPlugin implements Listener {
    private Inventory gui;
    private Inventory ChooseGUI;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        createGui();
        createCHOOSEGui();
        getLogger().warning("Plugin Loaded VERSION 1.0.2111");
        getLogger().warning("Made by Elektron");

        getLogger().warning("STABLE build!");
        getServer().getPluginManager().registerEvents(this, this);







        getLogger().warning("starting main");
    }
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onCrystalDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.END_CRYSTAL) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onElytraBoost(PlayerElytraBoostEvent event) {
        Player player = event.getPlayer();
        event.getPlayer().getInventory().remove(Material.ELYTRA);
        ItemStack chest = player.getInventory().getChestplate();
        if (chest != null && chest.getType() == Material.ELYTRA) {
            player.getInventory().setChestplate(null);
        }
        event.setCancelled(true);
        player.sendActionBar("Elytras arent allowed on the Server");
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f );
    }
    @EventHandler
    public void onPortalTeleport(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

        if (event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            Objective netherObj = board.getObjective("netherEnabled");
            if (netherObj == null) {
                // No netherEnabled objective, block teleport
                player.sendTitle("§c§lNether Banned", "", 10, 70, 20);
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f );
            }
        } else if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            Objective endObj = board.getObjective("endEnabled");
            if (endObj == null) {
                // No endEnabled objective, block teleport
                player.sendTitle("§c§lEnd Banned", "", 10, 70, 20);
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f );
            }
        }
    }



    @EventHandler
    public void onEnderPearlPortal(EntityPortalEvent event) {
        Entity entity = event.getEntity();

        // Only target Ender Pearls
        if (entity instanceof EnderPearl) {
            event.setCancelled(true);

            // Optional: notify the player who threw the pearl
            if (((EnderPearl) entity).getShooter() instanceof Player) {
                Player player = (Player) ((EnderPearl) entity).getShooter();

                player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Nuh uh", "", 10, 70, 20);
            }
        }}












//CREATE INVENTORY



    private void createGui() {
        // Create a chest GUI with 9 slots
        gui = Bukkit.createInventory(null, 9, "Restore the EGG");

        // Add items to the GUI
        gui.setItem(4, createGuiItem(Material.DIAMOND, "Reclaim dragon Egg!"));
        gui.setItem(3, createGuiItem(Material.NETHER_STAR, "Costs 1 Netherite BLOCK!"));
        gui.setItem(5, createGuiItem(Material.NETHER_STAR, "Costs 1 Netherite BLOCK!"));
        gui.setItem(0, createGuiItem(Material.ENDER_EYE, "Claim the effect of the egg!"));
    }
    private void createCHOOSEGui() {
        // Create a chest GUI with 9 slots
        ChooseGUI = Bukkit.createInventory(null, 9, "Pick an ability!");

        // Add items to the GUI
        ChooseGUI.setItem(0, createGuiItem(Material.NETHER_STAR, "Stärke 4"));
        ChooseGUI.setItem(1, createGuiItem(Material.NETHER_STAR, "Speed 3"));
        ChooseGUI.setItem(2, createGuiItem(Material.NETHER_STAR, "Haste 3"));
        ChooseGUI.setItem(3, createGuiItem(Material.NETHER_STAR, "+10 Health"));
        ChooseGUI.setItem(4, createGuiItem(Material.NETHER_STAR, "Regeneration 3"));
        ChooseGUI.setItem(5, createGuiItem(Material.NETHER_STAR, "Jump boost 4"));
        ChooseGUI.setItem(6, createGuiItem(Material.NETHER_STAR, "Fire ressistance"));
        ChooseGUI.setItem(7, createGuiItem(Material.NETHER_STAR, "Luck 5"));
        ChooseGUI.setItem(8, createGuiItem(Material.NETHER_STAR, "Sonic Boom (shift rechts click)"));
    }

































    private ItemStack createGuiItem(Material material, String name) {
        // Create an item stack with a custom name
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }
    public void openGui(Player player) {
        // Open the GUI for a player
        player.openInventory(gui);
    }
    public void openCHOOSEGui(Player player) {
        // Open the GUI for a player
        player.openInventory(ChooseGUI);
    }












//HANDLE GUI CLICKS!!!




    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Restore the EGG")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            if (event.getSlot() == 4) {
                handleIronClick(event.getWhoClicked());
            } else if (event.getSlot() == 0) {
                handleGetEffect(event.getWhoClicked());

            }


        } else if (event.getView().getTitle().equals("Pick an ability!")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            Player player = (Player) event.getWhoClicked();
            ItemStack egg = player.getInventory().getItemInMainHand().getType() == Material.DRAGON_EGG
                    ? player.getInventory().getItemInMainHand()
                    : player.getInventory().getItemInOffHand();

            switch (event.getSlot()) {
                case 0 -> handleStrengthClick(player, egg);
                case 1 -> handleSpeed(player, egg);
                case 2 -> handleHaste(player, egg);
                case 3 -> handleHealthBoost(player, egg);
                case 4 -> handleRegeneration(player, egg);
                case 5 -> handleJumpBoost(player, egg);
                case 6 -> handleFireResi(player, egg);
                case 7 -> handleLuck(player, egg);
                case 8 -> handleSonicBoom(player, egg);
            }
        }
    }
    public void removeLongEffects() {
        // Duration threshold: 1 hour in ticks
        int oneHourTicks = 20 * 60 * 60; // 20 ticks * 60 seconds * 60 minutes = 72000 ticks

        for (Player player : Bukkit.getOnlinePlayers()) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getDuration() > oneHourTicks) {
                    // Remove potion effect
                    player.removePotionEffect(effect.getType());
                } else if (effect.getDuration() == PotionEffect.INFINITE_DURATION) {
                    player.removePotionEffect(effect.getType());

                }
            }
        }
    }
    public void handleGetEffect(HumanEntity human) {
        if (!(human instanceof Player)) return;
        Player player = (Player) human;

        // Find the Dragon Egg in the player's inventory
        ItemStack egg = null;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.DRAGON_EGG && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                egg = item;
                break;
            }
        }

        if (egg == null) {
            player.sendMessage("§cYou do not have a Dragon Egg with an effect!");
            player.closeInventory();
            return;
        }

        // Read the lore
        List<String> lore = egg.getItemMeta().getLore();
        if (lore == null) return;

        for (String line : lore) {
            line = ChatColor.stripColor(line).toLowerCase();

            switch (line) {
                case "strength 4":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 2, true));
                    break;
                case "speed 3":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true));
                    break;
                case "haste 3":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, Integer.MAX_VALUE, 2, true));
                    break;
                case "+10 health":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 4, true));
                    break;
                case "regeneration 3":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2, true));
                    break;
                case "jump boost 4":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE, 3, true));
                    break;
                case "fire resistance":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, true));
                    break;
                case "luck 5":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, Integer.MAX_VALUE, 4, true));
                    break;
            }
        }

        player.sendMessage("§aYou claimed the effect from your Dragon Egg!");
        player.closeInventory();
    }




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("chestgui")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openGui(player);
                return true;
            } else {
                sender.sendMessage("Only players can use this command!");
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("enable_end")) {

            if (!sender.isOp()) {
                sender.sendMessage("You do not have permission.");
                return true;
            }

            sender.sendMessage("End is now ENABLED.");

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "scoreboard objectives add endEnabled dummy \"End Enabled\"");

            return true;
        } else if(command.getName().equalsIgnoreCase("enable_nether")) {

            if (!sender.isOp()) {
                sender.sendMessage("You do not have permission.");
                return true;
            }

            sender.sendMessage("Nether is now ENABLED.");

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "scoreboard objectives add netherEnabled dummy \"Nether Enabled\"");

            return true;

        }
        else if(command.getName().equalsIgnoreCase("opbook")) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            if (!player.isOp()) {
                player.sendMessage("You do not have permission.");
                return true;
            }

            // Make the player run a command
            player.performCommand("execute as "+ player.getName() + " run tellraw " + player.getName() + " /tellraw @p [\"\",{text:\"!DAS ADMIN BUCH!\",bold:true,underlined:true,color:\"aqua\"},{text:\"\\n\"},{text:\"\\n\"},{text:\"Border:\",bold:true,underlined:true},{text:\"\\n\"},{text:\"Tag 1: \"},{text:\"1000x1000 \",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"worldborder set 2000 200\"}},{text:\"\\n\"},{text:\"Tag 2: \"},{text:\"2000x2000\",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"worldborder set 4000 200\"}},{text:\"\\n\"},{text:\"Tag 3: \"},{text:\"3000x3000\",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"worldborder set 6000 200\"}},{text:\"\\n\"},{text:\"Tag 4: \"},{text:\"4000x4000\",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"worldborder set 8000 200\"}},{text:\"\\n\"},{text:\"Tag 5: \"},{text:\"5000x5000\",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"worldborder set 10000 200\"}},{text:\"\\n\"},{text:\"(dann nach end unlock halt andersrum xD)\",bold:true,color:\"aqua\"},{text:\"\\n\"},{text:\"FINALE: \",bold:true,underlined:true,color:\"dark_purple\"},{text:\"500x500\",bold:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"worldborder set 1000 2000\"}},{text:\"\\n\"},{text:\"IMMER VORHER ANKUENDIGEN DAS NIEMAND AUSSEHALB DER BORDER IST!\",bold:true,color:\"light_purple\"},{text:\"BORDER IST!\",bold:true,color:\"light_purple\"},{text:\"\\n\"},{text:\"Portale: \",bold:true,underlined:true},{text:\"\\n\"},{text:\"Nether AN\",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"scoreboard objectives add netherEnabled dummy \\\"Nether Enabled\\\"\"}},{text:\" AUS\",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"scoreboard objectives remove netherEnabled\"}},{text:\"\\n\"},{text:\"End AN\",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"scoreboard objectives add endEnabled dummy \\\"End Enabled\\\"\"}},{text:\" AUS\",underlined:true,color:\"dark_blue\",click_event:{action:\"run_command\",command:\"scoreboard objectives remove endEnabled\"}},{text:\"\\n\"},{text:\"\\n\"},{text:\"Ankuendigungen:\",bold:true,underlined:true,color:\"aqua\"},{text:\"\\n\"},{text:\"Server Start: /firststart\"},{text:\"\\n\"},{text:\"Wachsen Border: /bordergrow\"},{text:\"\\n\"},{text:\"Schrumpfen Border: /bordersmall\"},{text:\"\\n\"},{text:\"FINALE!: /finale\",bold:true,color:\"aqua\"},{text:\"\\n\"},{text:\"\\n\"},{text:\"Nether Open: /netherallow\"},{text:\"\\n\"},{text:\"End open: /endallow\"},{text:\"\\n\"},{text:\"Border schrumpft in 1h: /ALERTY\",bold:true,underlined:true,color:\"light_purple\"},{text:\"\\n\"},{text:\"dddddddddddddddddd\",obfuscated:true},{text:\"ddddddddddddddddddd\",obfuscated:true},{text:\"\\n\"},{text:\"By: Elektron12345    \",bold:true,underlined:true,color:\"dark_red\"},{text:\"dddddddddddddddddd\",obfuscated:true},{text:\"dddddddddddddddddd\",obfuscated:true}]"); // Example command


            return true;
        }
        return false;
    }












    private void handleHaste(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, PotionEffect.INFINITE_DURATION, 2, true));
        addLoreToDragonEgg(egg, "Haste 3");
        player.closeInventory();
    }

    private void handleStrengthClick(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, PotionEffect.INFINITE_DURATION, 2, true));
        addLoreToDragonEgg(egg, "Strength 4");
        player.closeInventory();
    }

    private void handleSpeed(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 2, true));
        addLoreToDragonEgg(egg, "Speed 3");
        player.closeInventory();
    }

    private void handleHealthBoost(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, PotionEffect.INFINITE_DURATION, 4, true));
        addLoreToDragonEgg(egg, "+10 Health");
        player.closeInventory();
    }

    private void handleRegeneration(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, PotionEffect.INFINITE_DURATION, 2, true));
        addLoreToDragonEgg(egg, "Regeneration 3");
        player.closeInventory();
    }

    private void handleJumpBoost(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, 3, true));
        addLoreToDragonEgg(egg, "Jump boost 4");
        player.closeInventory();
    }

    private void handleFireResi(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 1, true));
        addLoreToDragonEgg(egg, "Fire resistance");
        player.closeInventory();
    }

    private void handleLuck(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, PotionEffect.INFINITE_DURATION, 4, true));
        addLoreToDragonEgg(egg, "Luck 5");
        player.closeInventory();
    }

    private void handleSonicBoom(Player player, ItemStack egg) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 2, true));
        addLoreToDragonEgg(egg, "Sonic Boom");
        player.closeInventory();
    }



















    private void handleIronClick(HumanEntity player) {
        removeLongEffects();

        if(player.getInventory().contains(Material.NETHERITE_BLOCK)){
            if(player.getInventory().contains(Material.DRAGON_EGG)){
                player.getInventory().removeItem(new ItemStack(Material.NETHERITE_BLOCK, 1));
                player.getInventory().remove(Material.DRAGON_EGG);
                player.sendActionBar(Component.text("Dragon Egg has been resotred"));
                removeLongEffects();
                player.getInventory().addItem(new ItemStack(Material.DRAGON_EGG));


            }
            else {player.sendActionBar(Component.text("You need 1 Netherite Block and 1 Dragon Egg to restore the egg!"));}
        }
        else {player.sendActionBar(Component.text("You need 1 Netherite Block and 1 Dragon Egg to restore the egg!"));}

        // Add more actions here
    }




    //detect right click with dragon egg:
    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        // Only handle right-click actions
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();

        // Check both hands
        ItemStack main = player.getInventory().getItemInMainHand();
        ItemStack off = player.getInventory().getItemInOffHand();

        ItemStack item = null;

        // Prioritize main hand if both somehow have Dragon Eggs
        if (main != null && main.getType() == Material.DRAGON_EGG) {
            item = main;
        } else if (off != null && off.getType() == Material.DRAGON_EGG) {
            item = off;
        } else {
            return; // neither hand has a Dragon Egg
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        event.setCancelled(true);

        if (!meta.hasLore()) {
            // Run function for Dragon Egg without lore

            openCHOOSEGui(player);
        } else {
            // Run function for Dragon Egg with lore
            onDragonEggWithLore(item, player);
        }
    }

    private void addLoreToDragonEgg(ItemStack item, String lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.setDisplayName("§6Dragon Egg");
        meta.setLore(Collections.singletonList(lore));
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
    }

    private void onDragonEggWithLore(ItemStack item, Player sender) {
        // Now you can pass the player to your GUI
        openGui(sender);
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item dropped = event.getItemDrop(); // The item entity that spawned
        ItemStack itemStack = dropped.getItemStack();



        // Example: cancel dropping dragon eggs
        if (itemStack.getType() == Material.DRAGON_EGG) {

            removeLongEffects();
        }
    }
    @EventHandler
    public void onInventoryClic2k(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        ItemStack cursor = event.getCursor();           // For normal click
        ItemStack current = event.getCurrentItem();     // For shift-click

        // Determine which item is being moved
        ItemStack item = cursor != null && !cursor.getType().isAir() ? cursor : current;

        // Only block dragon egg
        if (item == null || item.getType() != Material.DRAGON_EGG) return;

        Inventory clicked = event.getClickedInventory();
        InventoryType clickedType = clicked.getType();

        // Handle shift-click separately
        if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
            Inventory top = event.getView().getTopInventory();
            Inventory bottom = event.getView().getBottomInventory();
            Inventory destination;

            // Determine destination inventory
            if (clickedType == InventoryType.PLAYER) {
                // Shift-clicking from player to top inventory
                destination = top;
            } else {
                // Shift-clicking from top inventory to player
                destination = bottom;
            }

            if (destination.getType() != InventoryType.PLAYER) {
                event.setCancelled(true);
                event.getWhoClicked().sendMessage("§cYou cannot put a dragon egg into this inventory!");
            }
            return; // Already handled shift-click
        }

        // Normal click: only allow placing into PLAYER inventory
        if (clickedType != InventoryType.PLAYER) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cYou cannot put a dragon egg into this inventory!");
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        ItemStack item = event.getOldCursor();
        if (item == null || item.getType() != Material.DRAGON_EGG) return;

        InventoryView view = event.getView();
        Inventory top = view.getTopInventory();

        for (int slot : event.getRawSlots()) {
            if (slot < top.getSize()) {
                if (top.getType() != InventoryType.PLAYER) {
                    event.setCancelled(true);
                    event.getWhoClicked().sendMessage("§cYou cannot put a dragon egg into this inventory!");
                    return;
                }
            }
        }
    }
    @EventHandler
    public void onDragonEggDespawn(ItemDespawnEvent event) {
        Item itemEntity = event.getEntity(); // The actual entity
        ItemStack item = itemEntity.getItemStack();

        if (item != null && item.getType() == Material.DRAGON_EGG) {
            event.setCancelled(true); // Prevent it from despawning

            // Reset despawn timer
            itemEntity.setTicksLived(20);

            // Get the location of the dragon egg
            Location loc = itemEntity.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            String world = loc.getWorld().getName();

            // Send a message to all players
            String message = String.format("§6Dragon Egg located at §e%s §7(X: %.1f, Y: %.1f, Z: %.1f)", world, x, y, z);
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(message);
            }
        }
    }




















































}

