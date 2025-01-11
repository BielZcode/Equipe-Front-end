package net.ftclient.clientcommon.gui.Servidor.FeaturedServer;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ResourceLocation;

public class ServerDataFeatured extends ServerData {

    //public static final ResourceLocation START_ICON = new ResourceLocation("ftclient/textures/menu/serverlist/favorite-icon.png");

    public ServerDataFeatured(String serverName, String serverIP) {
        super(serverName, serverIP, false);
    }
}
