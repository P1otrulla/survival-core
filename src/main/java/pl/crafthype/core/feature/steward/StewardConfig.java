package pl.crafthype.core.feature.steward;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.config.item.ConfigItem;

import java.io.File;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

public class StewardConfig implements ReloadableConfig {

    public String title = "&6Klucznik";

    public int rows = 3;

    public ConfigItem avaiable = ConfigItem.builder()
        .material(Material.TRIPWIRE_HOOK)
        .name("&6Klucz dla: &e{NAME}")
        .lore(Collections.singletonList("&7Kliknij aby odebrać klucz"))
        .enchantment(Enchantment.LUCK, 1)
        .flag(ItemFlag.HIDE_ENCHANTS)
        .build();

    public ConfigItem cooldown = ConfigItem.builder()
        .material(Material.CLOCK)
        .name("&6Klucz dla: &e{NAME}")
        .lore(Collections.singletonList("&cKluczyk będzie dostępny za: &f{TIME}"))
        .enchantment(Enchantment.LUCK, 1)
        .flag(ItemFlag.HIDE_ENCHANTS)
        .build();

    public ConfigItem noPermission = ConfigItem.builder()
        .material(Material.BARRIER)
        .name("&6Klucz dla: &e{NAME}")
        .lore(Collections.singletonList("&cNie posiadasz uprawnień do odebrania tego klucza! &f({PERMISSION})"))
        .enchantment(Enchantment.LUCK, 1)
        .flag(ItemFlag.HIDE_ENCHANTS)
        .build();

    public Map<String, Steward> stewards = Map.of(
        "elita", new Steward("elita", 11, "crafthype.elita", Duration.ofHours(12), Collections.singletonList("key {PLAYER} 1")),
        "legenda", new Steward("legenda", 13, "crafthype.legenda", Duration.ofHours(12), Collections.singletonList("key {PLAYER} 2")),
        "hype", new Steward("hype", 15, "crafthype.hype", Duration.ofHours(12), Collections.singletonList("key {PLAYER} 3"))
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "steward.yml");
    }
}
