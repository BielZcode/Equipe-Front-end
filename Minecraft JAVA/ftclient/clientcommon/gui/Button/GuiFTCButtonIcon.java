package net.ftclient.clientcommon.gui.Button;

import net.ftclient.clientcommon.utils.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiFTCButtonIcon extends GuiButton {

    private final ResourceLocation normalImage;
    private final ResourceLocation hoverImage;
    private final ResourceLocation icon;
    private final String tooltipText; // Texto do tooltip
    private boolean isHovered;
    private static UnicodeFontRenderer ufr;

    public GuiFTCButtonIcon(int buttonId, int xPos, int yPos, int width, int height, ResourceLocation normalImage, ResourceLocation hoverImage, ResourceLocation icon, String tooltipText) {
        super(buttonId, xPos, yPos, width, height, "");
        this.normalImage = normalImage;
        this.hoverImage = hoverImage;
        this.icon = icon;
        this.tooltipText = tooltipText;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!this.visible) return;

        // Verificar se o mouse está sobre o botão
        isHovered = mouseX >= this.xPosition && mouseX <= this.xPosition + this.width &&
                mouseY >= this.yPosition && mouseY <= this.yPosition + this.height;

        // Selecionar a textura (hover ou normal)
        ResourceLocation texture = isHovered ? hoverImage : normalImage;
        mc.getTextureManager().bindTexture(texture);

        // Renderizar o botão
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0, 0, this.width, this.height, this.width, this.height);

        // Renderizar o ícone no centro do botão
        if (icon != null) {
            mc.getTextureManager().bindTexture(icon);

            int iconSize = (int) (Math.min(this.width, this.height) / 2.2f);  // Definir tamanho do ícone
            int iconX = this.xPosition + (this.width - iconSize) / 2;
            int iconY = this.yPosition + (this.height - iconSize) / 2;

            drawModalRectWithCustomSizedTexture(iconX, iconY, 0, 0, iconSize, iconSize, iconSize, iconSize);
        }

        // Renderizar o tooltip se o mouse estiver sobre o botão
        if (isHovered && tooltipText != null && !tooltipText.isEmpty()) {
            drawTooltip(mc, tooltipText, mouseX, mouseY - 10); // Ajustar posição do tooltip
        }
    }

    private void drawTooltip(Minecraft mc, String text, int x, int y) {
        if (ufr == null) {
            ufr = UnicodeFontRenderer.getFontOnPC("Arial", 19); // Inicializar fonte
            if (ufr == null) {
                System.err.println("Erro ao carregar UnicodeFontRenderer");
                return;
            }
        }

        int textWidth = ufr.getStringWidth(text);
        int padding = 4;

        int bgX1 = x - padding;
        int bgY1 = y - padding;
        int bgX2 = x + textWidth + padding;
        int bgY2 = y + ufr.FONT_HEIGHT + padding;

        // Desenhar fundo
        drawRect(bgX1, bgY1, bgX2, bgY2, 0xAA000000);

        // Desenhar texto
        ufr.drawStringWithShadow(text, x, y, 0xFFFFFF);
    }

    // Verifica se o botão foi pressionado
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible &&
                mouseX >= this.xPosition && mouseX <= this.xPosition + this.width &&
                mouseY >= this.yPosition && mouseY <= this.yPosition + this.height;
    }
}
