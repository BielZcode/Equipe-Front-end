package net.ftclient.clientcommon.utils.Manager;

import net.ftclient.clientcommon.gui.GuiFunctions;
import net.ftclient.clientcommon.gui.GuiTextureButton;
import net.ftclient.clientcommon.utils.Manager.mojang.GuiLoginMojang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class AltManagerGui extends GuiScreen {

    private Minecraft mc = Minecraft.getMinecraft();
    public GuiFunctions painelRenderer = new GuiFunctions();

    public AltManagerGui(GuiMainMenu guiMainMenu) {
        super();
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, 200, 250, 20, 20, "") {

            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                if (this.visible) {
                    mc.getTextureManager().bindTexture(GuiTextureButton.EXIT); // Certifique-se de que o ResourceLocation EXIT está correto
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0.0F, 0.0F, 1000F); // Garantir que o botão esteja acima de outros elementos
                    Gui.drawModalRectWithCustomSizedTexture(566, 51, 0, 0, 17, 17, 17, 17); // Desenho fixo da imagem
                    GlStateManager.popMatrix();
                }
            }

            @Override
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                // Verifica se o clique está na área fixa definida pela imagem
                if (this.enabled && this.visible && mouseX >= 566 && mouseY >= 51
                        && mouseX < 566 + 17 && mouseY < 51 + 17) {
                    this.playPressSound(mc.getSoundHandler()); // Toca o som de clique
                    return true;
                }
                return false;
            }
        });

        this.buttonList.add(new GuiButton(1, width / 2 + 4 + 50, height - 48, 100, 20, "Login Offline"));
        this.buttonList.add(new GuiButton(2, width / 2 - 50, height - 48, 100, 20, "Login na Microsoft"));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(new GuiMainMenu());
        }

        if(button.id == 1) {
            mc.displayGuiScreen(new GuiLogin());
        }

        if(button.id == 3){
            mc.displayGuiScreen(new GuiLoginMojang());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        painelRenderer.drawPainel(150, 50, GuiTextureButton.CLOSE);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
