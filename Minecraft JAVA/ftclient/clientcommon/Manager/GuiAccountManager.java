package net.ftclient.clientcommon.Manager;

import net.ftclient.clientcommon.gui.Button.GuiFTCButton;
import net.ftclient.clientcommon.gui.GuiTextureButton;
import net.ftclient.clientcommon.utils.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class GuiAccountManager extends GuiScreen {

    private Minecraft mc = Minecraft.getMinecraft();
    private UnicodeFontRenderer ufr;

    public void initGui() {
        int j = this.height / 4 + 48;

        this.buttonList.clear();
        this.buttonList.add(new GuiFTCButton(0, 260, 275, 200, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, I18n.format("Voltar", new Object[0])));
        this.buttonList.add(new GuiFTCButton(1, 260, 250, 200, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, I18n.format("Logar conta Offline", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(null);
        }
        if(button.id == 1){
            mc.displayGuiScreen(new GuiLogin());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawBackground(0, 0, GuiTextureButton.BACKGROUND);

        super.drawScreen(mouseX, mouseY, partialTicks);

        if(ufr == null) {
            ufr = UnicodeFontRenderer.getFontOnPC("Arial", 20);
        }

        ufr.drawString("Autenticação de Conta", 305, 190, -1);

    }

    private void drawBackground(int x, int y, ResourceLocation texture) {
        this.mc.getTextureManager().bindTexture(texture);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false; // Não pausa o jogo
    }
}
