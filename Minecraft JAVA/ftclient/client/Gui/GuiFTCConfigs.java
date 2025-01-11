package net.ftclient.client.Gui;

import net.ftclient.clientcommon.gui.Button.GuiFTCButton;
import net.ftclient.clientcommon.utils.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiFTCConfigs extends GuiScreen
{

    private UnicodeFontRenderer ufr;
    
    public GuiFTCConfigs() {

    }

    @Override
    public void initGui() {

        configsbutton();

    }

    public void configsbutton() {
        int i = 24;
        int j = this.height / 4 + 48;

        this.buttonList.add(new GuiFTCButton(0, this.width / 2 - 100, j + 72 + 24, 98, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "MC Options"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground(0, 0, new ResourceLocation("textures/gui/title/background/panorama_0.png"));

        GlStateManager.disableAlpha();
        GlStateManager.enableAlpha();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int i = 274;
        int j = this.width / 2 - i / 2;
        int k = 30;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
        float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
        GlStateManager.scale(f, f, f);
        GlStateManager.popMatrix();

        if(ufr == null) {
            ufr = UnicodeFontRenderer.getFontOnPC("Arial", 20);
        }

    }

    private void drawBackground(int x, int y, ResourceLocation texture) {
        this.mc.getTextureManager().bindTexture(texture);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 1080, 1080, 1080, 1080);
    }
}
