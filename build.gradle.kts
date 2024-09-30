import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "pl.crafthype"
version = "1.0.2"
val mainPackage = "pl.crafthype.core"

repositories {
    gradlePluginPortal()
    mavenCentral()

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repository.minecodes.pl/releases")
    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.eternalcode.pl/releases")
    maven("https://maven.enginehub.org/repo")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.auxilor.io/repository/maven-public/")
    maven("https://maven.citizensnpcs.co/repo")
}

dependencies {
    // Engine
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    // Messages
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")

    // Configs
    implementation("net.dzikoysk:cdn:1.14.4") {
        exclude("kotlin")
    }

    // Citizens
    implementation("net.citizensnpcs:citizensapi:2.0.33-SNAPSHOT")

    // database
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")

    // command framework
    implementation("dev.rollczi.litecommands:bukkit:2.8.9")

    // skull api
    implementation("dev.rollczi:liteskullapi:1.3.0")

    // Inventory framework
    implementation("dev.triumphteam:triumph-gui:3.1.7")

    // caffeine for ultra fast cache and eliminate google's guava cache bugs.
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

    // Economy
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    // LuckPerms
    compileOnly("net.luckperms:api:5.4")

    // Database
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")

    // placeholders
    compileOnly("me.clip:placeholderapi:2.11.5")

    // worldugard for autolapis only on spawn.
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8")

    // anvilgui
    implementation("net.wesjd:anvilgui:1.9.0-SNAPSHOT")

    //BookAPI
    implementation("xyz.upperlevel.spigot.book:spigot-book-api:1.6")

    // benchmarks
    testImplementation("org.openjdk.jmh:jmh-core:1.37")
    testAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    testImplementation("com.mysql:mysql-connector-j:8.2.0")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

bukkit {
    main = "$mainPackage.CorePlugin"
    apiVersion = "1.13"
    prefix = "chCore"
    author = "Piotrulla and bug fixes by Martin Sulikowski"
    description = "All-in-one function plugin for CraftHype.pl network."
    name = "chCore"
    depend = listOf("Vault", "PlaceholderAPI", "WorldGuard", "LuckPerms")
    version = "${project.version}"
}

tasks.compileJava {
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    options.encoding = "UTF-8"
    options.javaModuleVersion.set("17")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    runServer {
        minecraftVersion("1.20.1")

        downloadPlugins {
            hangar("PlaceholderAPI", "2.11.5")
            github("MilkBowl", "Vault", "1.7.3", "Vault.jar")
            url("https://download.luckperms.net/1521/bukkit/loader/LuckPerms-Bukkit-5.4.108.jar")
            url("https://mediafilez.forgecdn.net/files/4807/510/worldedit-bukkit-7.3.0-beta-02.jar")
            url("https://mediafilez.forgecdn.net/files/4675/318/worldguard-bukkit-7.0.9-dist.jar")
        }

    }
}

tasks.shadowJar {
    archiveFileName.set("chCore v${project.version}.jar")

    val prefix = "$mainPackage.libs"

    listOf(
        "panda",
        "org.panda_lang",
        "org.bstats",
        "net.dzikoysk",
        "net.kyori",
        "com.j256",
        "dev.triumphteam",
        "dev.rollczi",
        "com.github.ben-manes",
    ).forEach { relocate(it, prefix) }
}