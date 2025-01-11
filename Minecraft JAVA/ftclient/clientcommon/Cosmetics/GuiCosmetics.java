package net.ftclient.clientcommon.Cosmetics;

import net.ftclient.clientcommon.gui.Button.GuiFTCButton;
import net.ftclient.clientcommon.gui.GuiTextureButton;
import net.ftclient.clientcommon.utils.UnicodeFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiCosmetics extends GuiScreen
{
    private static UnicodeFontRenderer ufr;

    public GuiCosmetics() {

    }

    @Override
    public void initGui() {
        int i = 24;
        int j = this.height / 4 + 48;
        this.buttonList.clear();
        this.buttonList.add(new GuiFTCButton(0, this.width / 2 + 2, j + 72 + -0, 98, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Seus Cosmeticos"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 0) {

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground(0, 0, GuiTextureButton.BACKGROUND);


        if(ufr == null) {
            ufr = UnicodeFontRenderer.getFontOnPC("Arial", 20);
        }

        this.ufr.drawString("Em breve - em Desevolvimento!!!", 0, 0, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawBackground(int x, int y, ResourceLocation texture) {
        this.mc.getTextureManager().bindTexture(texture);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
    }
}
