package pl.crafthype.core.feature.pass;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import pl.crafthype.core.config.ReloadableConfig;
import pl.crafthype.core.config.item.ConfigItem;
import pl.crafthype.core.notification.Notification;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// TODO: dodać metody do PassSettings i nie używać czystego configu
public class PassConfig implements ReloadableConfig, PassSettings {

    public List<Notification> noAcces = Arrays.asList(
        Notification.chat("&cNie masz dostępu do tego świata!"),
        Notification.chat("&cWykup go pod komendą &e/wejsciowka&c!")
    );
    public Notification noMoney = Notification.chat("&cNie masz wystarczająco pieniędzy!");
    public Notification bought = Notification.chat("&7Zakupiłeś &ewejściówkę&7!");

    public String title = "&6Wejściówki";
    public int rows = 3;

    public double price = 30000;

    public ConfigItem alreadyHas = ConfigItem.builder()
        .material(Material.BARRIER)
        .name("&cPosiadasz już tą wejściówkę!")
        .enchantment(Enchantment.LUCK, 1)
        .flag(ItemFlag.HIDE_ENCHANTS)
        .build();

    public ConfigItem filler = ConfigItem.builder()
        .material(Material.GRAY_STAINED_GLASS_PANE)
        .name("&8-")
        .build();

    public Map<Material, String> passesByMaterial = Map.of(
        Material.NETHERRACK, "world_nether",
        Material.END_STONE, "world_the_end"
    );

    public Map<String, ConfigItem> passes = Map.of(
        "nether", ConfigItem.builder()
            .material(Material.NETHERRACK)
            .name("&cWejściówka do &eNETHERU")
            .lore(
                "",
                "&7Po zakupie otrzymasz dostęp do &cNETHERU",
                "&7Koszt: &e30.000",
                "",
                "&aKliknij aby zakupić!"
            )
            .slot(11)
            .build(),
        "end", ConfigItem.builder()
            .material(Material.END_STONE)
            .name("&cWejściówka do &eENDU")
            .lore(
                "",
                "&7Po zakupie otrzymasz dostęp do &cENDU",
                "&7Koszt: &e30.000",
                "",
                "&aKliknij aby zakupić!"
            )
            .slot(15)
            .build()
    );

    public List<Notification> noAccesTime = Arrays.asList(
            Notification.chat("&eGodziny otwarcia:"),
            Notification.chat(""),
            Notification.chat("&cNether &e15:00 &8- &e20:00"),
            Notification.chat("&cEnd &e20:00 &8- &e23:00")
        );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "pass.yml");
    }

    @Override
    public Collection<String> blockedWorlds() {
        return Collections.unmodifiableCollection(this.passesByMaterial.values());
    }
}
