package net.ftclient.clientcommon;

import net.ftclient.client.Discord.DiscordRP;
import net.ftclient.clientcommon.gui.GuiSplashFTC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.Display;

public class FuryTigris extends GuiScreen {

    private static final FuryTigris INSTANCE = new FuryTigris();
    public static final FuryTigris getInstance() {
        return INSTANCE;
    }

    private DiscordRP discordRP = new DiscordRP();

    public static String name = "FuryTigris ", version = "1.8.9";

    public static void startClient() {
        Display.setTitle(name + "" + version);
    }

    public void init() {
        GuiSplashFTC.setProgress(1, "[FuryTigris client - 1.8.8] Client Inicializando DiscordRP");
        net.ftclient.clientcommon.Logger.log("[FuryTigris client - 1.8.8] Client Inicializando DiscordRP");
        discordRP.start();
    }

    public void shutdown() {
        discordRP.shutdown();
    }

    public DiscordRP getDiscordRP() {
        return discordRP;
    }
}
