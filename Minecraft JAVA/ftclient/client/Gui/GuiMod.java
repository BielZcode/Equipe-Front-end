package net.ftclient.client.Gui;

import net.minecraft.client.gui.GuiScreen;

public class GuiMod extends GuiScreen {

    public GuiMod() {

    }

    @Override
    public void initGui() {
        super.initGui();
    }

    public void listMods() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiMod.drawRect(this.width / 2 - 55 , this.width / 2 - 55, this.width / 2 + 55, this.width / 2 + 55, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
