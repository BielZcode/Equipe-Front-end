package net.ftclient.clientcommon.gui.Servidor;

import net.ftclient.clientcommon.gui.Button.GuiFTCButton;
import net.ftclient.clientcommon.utils.UnicodeFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiFTMultiplayer extends GuiScreen {

    private static final Logger logger = LogManager.getLogger();

    private UnicodeFontRenderer ufr;
    private GuiButton btnEditServer;
    private GuiButton btnSelectServer;
    private GuiButton btnDeleteServer;

    private ServerList savedServerList;
    private final OldServerPinger oldServerPinger = new OldServerPinger();

    private ServerSelectionList serverListSelector;

    /**
     * The text to be displayed when the player's cursor hovers over a server listing.
     */
    private String hoveringText;
    private ServerData selectedServer;
    private LanServerDetector.LanServerList lanServerList;
    private LanServerDetector.ThreadLanServerFind lanServerDetector;
    private boolean initialized;

    public GuiFTMultiplayer() {
    }

    @Override
    public void initGui() {
        createButtons();
    }

    public void createButtons()
    {
        this.buttonList.add(this.btnEditServer = new GuiFTCButton(0, this.width / 2 - 154, this.height - 28, 70, 20, new ResourceLocation("ftclient/textures/menu/serverlist/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/serverlist/hover-state-regular-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Editar"));
        this.buttonList.add(this.btnDeleteServer = new GuiFTCButton(1, this.width / 2 - 74, this.height - 28, 70, 20, new ResourceLocation("ftclient/textures/menu/serverlist/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/serverlist/hover-state-regular-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Deletar"));
        this.buttonList.add(this.btnSelectServer = new GuiFTCButton(2, this.width / 2 - 154, this.height - 52, 100, 20, new ResourceLocation("ftclient/textures/menu/serverlist/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/serverlist/hover-state-regular-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Entrar no Servidor"));
        this.buttonList.add(new GuiFTCButton(3, this.width / 2 - 50, this.height - 52, 100, 20, new ResourceLocation("ftclient/textures/menu/serverlist/small-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/serverlist/hover-state-button-small.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Entrar Diretamente"));
        this.buttonList.add(new GuiFTCButton(4, this.width / 2 + 4 + 50, this.height - 52, 100, 20, new ResourceLocation("ftclient/textures/menu/serverlist/small-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/serverlist/hover-state-button-small.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Adicionar Servidor"));
        this.buttonList.add(new GuiFTCButton(5, this.width / 2 + 4, this.height - 28, 70, 20, new ResourceLocation("ftclient/textures/menu/serverlist/small-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/serverlist/hover-state-button-small.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Atualizar"));
        this.buttonList.add(new GuiFTCButton(6, this.width / 2 + 4 + 76, this.height - 28, 75, 20, new ResourceLocation("ftclient/textures/menu/serverlist/small-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/serverlist/hover-state-button-small.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Cancelar"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        if(button.id == 0) {

        }

        if(button.id == 1) {

        }

        if(button.id == 2) {

        }

        if(button.id == 3) {

        }

        if(button.id == 4) {

        }

        if(button.id == 5) {

        }

        if(button.id == 6) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {


        drawBackground(0, 0, new ResourceLocation("textures/gui/title/background/panorama_0.png"));

        drawPanel((int) (this.width / 3.5), this.height / 115, this.width / 4 + 335, this.height / 4 + 330, 0xFF222222, 0x0222222);

        if(ufr == null) {
            ufr = UnicodeFontRenderer.getFontOnPC("Arial", 25);
        }

        ufr.drawString("Jogar Multijogador", this.width / 2.3F, this.height / 3 + -145, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    private void drawBackground(int x, int y, ResourceLocation texture) {
        this.mc.getTextureManager().bindTexture(texture);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 1080, 1080, 1080, 1080);
    }

    private void drawPanel(int x1, int y1, int x2, int y2, int borderColor, int fillColor) {
        // Desenha borda
        drawRect(x1 - 2, y1 - 2, x2 + 2, y2 + 2, borderColor);

        // Preenche com uma cor sólida
        drawRect(x1, y1, x2, y2, fillColor);

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void drawRect(int x1, int y1, int x2, int y2, int color) {
        float alpha = (color >> 24 & 255) / 255.0F;
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(red, green, blue, alpha);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
    }
}
