package net.ftclient.client.Gui;

import java.io.IOException;

import net.ftclient.clientcommon.gui.Button.GuiFTCButton;
import net.ftclient.clientcommon.gui.Button.GuiFTCButtonIcon;
import net.ftclient.clientcommon.gui.FTMainMenu;
import net.ftclient.clientcommon.gui.GuiTextureButton;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;

public class GuiFTCInGame extends GuiScreen
{
    private boolean isOpening = false;
    private int field_146445_a;
    private int field_146444_f;
    private GuiButton button1;
    private GuiButton button2;
    private GuiButton button3;
    private GuiButton button4;
    private GuiButton button5;
    private GuiButton button6;

    private static final int PANEL_WIDTH = 200; // Largura do painel
    private static final int PANEL_HEIGHT = 150;
    private float panelX = -200;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.field_146445_a = 0;
        this.buttonList.clear();
        int i = -16;
        int j = 98;
        this.buttonList.add(new GuiFTCButton(1, this.width / 2 - 100, this.height / 4 + 145 + i, 200, 20, new ResourceLocation("ftclient/textures/menu/esc/disconnect-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/esc/hover-state-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, I18n.format("menu.returnToMenu")));

        if (!this.mc.isIntegratedServerRunning())
        {
            ((GuiFTCButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
        }

        sidebar();

        this.buttonList.add(new GuiFTCButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, 200, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiFTCButton(0, this.width / 2 - 100, this.height / 4 + 121 + i, 98, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "MC Options"));
        GuiButton guibutton;
        this.buttonList.add(guibutton = new GuiFTCButton(8, this.width / 2 + 2, this.height / 4 + 121 + i, 98, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "FTC Options"));
        this.buttonList.add(new GuiFTCButton(9, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "lista de Servers"));
        this.buttonList.add(guibutton = new GuiFTCButton(10, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Cosmetics"));
        this.buttonList.add(new GuiFTCButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Emotes"));
        this.buttonList.add(new GuiFTCButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, new ResourceLocation("ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "Sprays"));
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
    }

    private void sidebar() {
        int i = 24;
        int j = this.height / 4 + 48;

        button1 = new GuiFTCButtonIcon(45, this.width / 2 - 355, j + 72 + - 230, 20, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/esc/chat_notifications.png"), "");
        button2 = new GuiFTCButtonIcon(7, -200, 80, 20, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/esc/lan-icon.svg_large.png"), "");
        button3 = new GuiFTCButtonIcon(5, -200, 60, 20, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/esc/stats-icon.svg_large.png"), "");
        button4 = new GuiFTCButtonIcon(6, -200, 60, 20, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/esc/achievements-icon.svg_large.png"), "");

        this.buttonList.add(button1);
        this.buttonList.add(button2);
        this.buttonList.add(button3);
        this.buttonList.add(button4);

        this.buttonList.add(new GuiFTCButtonIcon(44, this.width / 2 - 350, j + 72 + - 230, 20, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/menu/home/burger.png"), ""));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 9) {
            this.mc.displayGuiScreen(new GuiMultiplayer());
        }

        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 1:
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.func_181540_al();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);

                if (flag)
                {
                    this.mc.displayGuiScreen(new FTMainMenu());
                }
                else if (flag1)
                {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                }
                else
                {
                    this.mc.displayGuiScreen(new GuiMultiplayer());
                }

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;

            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;

            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;

            case 7:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
        }
        if (button.id == 44) {
            isOpening = !isOpening;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146444_f;
    }

    private void drawImage(int x, int y, ResourceLocation texture) {
        this.mc.getTextureManager().bindTexture(texture);
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 120, 18 + 3, 0.0F, 0.0F, 250, 45, 250, 45);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        // Animação do painel
        if (isOpening && panelX < 0) {
            panelX += 10; // Move o painel para a direita
        } else if (!isOpening && panelX > -PANEL_WIDTH) {
            panelX -= 10; // Move o painel para a esquerda
        }

        button1.xPosition = (int) (panelX + 10);
        button1.yPosition = 50;
        button1.visible = panelX >= -PANEL_WIDTH + 10;

        button2.xPosition = (int) (panelX + 10);
        button2.yPosition = 80;
        button2.visible = panelX >= -PANEL_WIDTH + 10;

        button3.xPosition = (int) (panelX + 10);
        button3.yPosition = 110;
        button3.visible = panelX >= -PANEL_WIDTH + 10;

        button4.xPosition = (int) (panelX + 10);
        button4.yPosition = 140;
        button4.visible = panelX >= -PANEL_WIDTH + 10;

        this.drawDefaultBackground();

        drawImage(160, 70 + 150, GuiTextureButton.NAMECLIENT);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
