package net.ftclient.clientcommon.gui.Button;

import net.ftclient.clientcommon.utils.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class GuiFTCButton extends GuiButton {

    private UnicodeFontRenderer ufr;
    private ResourceLocation normalImage;
    private ResourceLocation hoverImage;
    private boolean isHovered;
    private String buttonText;

    private int xPos, yPos, width, height;

    // Construtor da classe, passando as imagens, o texto e as posições do botão
    public GuiFTCButton(int buttonId, int xPos, int yPos, int width, int height, ResourceLocation normalImage, ResourceLocation hoverImage, GameSettings.Options forceUnicodeFont, String buttonText) {
        super(buttonId, xPos, yPos, width, height, buttonText);  // Passando o texto para o super construtor
        this.normalImage = normalImage;
        this.hoverImage = hoverImage;
        this.buttonText = buttonText;

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        isHovered = mouseX >= xPos && mouseX <= xPos + width && mouseY >= yPos && mouseY <= yPos + height;

        ResourceLocation texture = isHovered ? hoverImage : normalImage;
        mc.getTextureManager().bindTexture(texture);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(xPos, yPos, 0, 0, width, height, width, height);

        // Calcula a posição do texto
        int textWidth = mc.fontRendererObj.getStringWidth(buttonText);
        int textHeight = mc.fontRendererObj.FONT_HEIGHT;
        int xTextPos = xPos + (width - textWidth) / 2;
        int yTextPos = yPos + (height - textHeight) / 2 + 6; // Ajuste vertical para centralizar

        // Renderiza o texto
        mc.fontRendererObj.drawString(buttonText, xTextPos, yTextPos, -1);
    }

    // Verifica se o botão foi pressionado
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            // A lógica de quando o botão é pressionado pode ser implementada aqui
            return true;
        }
        return false;
    }
}
