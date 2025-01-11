package net.ftclient.clientcommon.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class GuiFunctions {

    private Minecraft mc = Minecraft.getMinecraft();

    public void drawPainel(int x, int y, ResourceLocation texture) {
        this.mc.getTextureManager().bindTexture(texture);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 300, 120, 300, 120);
    }
}
