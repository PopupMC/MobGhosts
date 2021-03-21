package com.popupmc.mobghosts;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class MobGhosts extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {

        plugin = this;

        HostileMobs.setup();

        World world = Bukkit.getWorld("main_the_end");
        deathLocation = new Location(world, 24, 63, -15);
        deathLocation = deathLocation.toCenterLocation();

        Bukkit.getPluginManager().registerEvents(this, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                loop();
            }
        }.runTaskLater(this, 1);

        getLogger().info("MobGhosts is enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("MobGhosts is disabled");
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        // Not if on defend cooldown
        if(forcedDefendCooldown)
            return;

        // 20% chance of appearing
        if(random.nextInt(100 + 1) <= 20)
            return;

        // Only if not at grave
        if(ghostGolem != null)
            return;

        // Only if in end
        if(!event.getEntity().getWorld().getName().endsWith("the_end"))
            return;

        // Only if player
        if(event.getEntity().getType() != EntityType.PLAYER)
            return;

        // If not on list
        if(!HostileMobs.hashList.containsKey(event.getDamager().getType()))
            return;

        forcedDefendCooldown = true;

        // Spawn it
        ghostGolem = (IronGolem)event.getDamager().getWorld().spawnEntity(event.getDamager().getLocation(), EntityType.IRON_GOLEM);
        ghostGolem.setPlayerCreated(true);
        ghostGolem.setAI(true);
        ghostGolem.setAware(true);
        ghostGolem.setCanPickupItems(false);
        ghostGolem.setCustomName("[Ghost] Iron Golem");
        ghostGolem.setCollidable(false);
        ghostGolem.setGravity(true);
        ghostGolem.setCustomNameVisible(true);
        ghostGolem.setGlowing(true);
        ghostGolem.setInvulnerable(true);
        ghostGolem.attack(event.getDamager());

        new BukkitRunnable() {
            @Override
            public void run() {
                ghostGolem.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 0));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ghostGolem.remove();
                        ghostGolem = null;

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                forcedDefendCooldown = false;
                            }
                        }.runTaskLater(plugin, 20 * 60 * 5);
                    }
                }.runTaskLater(plugin, 20 * 5);
            }
        }.runTaskLater(plugin, 20 * 60 * 1);
    }

    public void loop() {

        if(Bukkit.getOnlinePlayers().size() <= 0) {
            endLoop();
            return;
        }

        MobGhosts plugin = this;

        boolean activate = random.nextInt(100 + 1) >= 50;

        if(!activate || ghostGolem != null) {
            endLoop();
            return;
        }

        ghostGolem = (IronGolem)deathLocation.getWorld().spawnEntity(deathLocation, EntityType.IRON_GOLEM);
        ghostGolem.setPlayerCreated(false);
        ghostGolem.setAI(false);
        ghostGolem.setAware(true);
        ghostGolem.setCanPickupItems(false);
        ghostGolem.setCustomName("[Ghost] Iron Golem");
        ghostGolem.setCollidable(false);
        ghostGolem.setGravity(false);
        ghostGolem.setCustomNameVisible(true);
        ghostGolem.setGlowing(true);
        ghostGolem.setInvulnerable(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                ghostGolem.setGravity(true);
                ghostGolem.setAI(true);
                ghostGolem.setPlayerCreated(true);
                ghostGolem.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 0));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ghostGolem.remove();
                        ghostGolem = null;

                        endLoop();
                    }
                }.runTaskLater(plugin, 20 * 5);
            }
        }.runTaskLater(plugin, 20 * 60 * 1);
    }

    public void endLoop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                loop();
            }
        }.runTaskLater(plugin, 20 * 60 * 5);
    }

    public boolean forcedDefendCooldown = false;
    public Location deathLocation;
    public IronGolem ghostGolem;
    public static final Random random = new Random();
    public static MobGhosts plugin;
}
