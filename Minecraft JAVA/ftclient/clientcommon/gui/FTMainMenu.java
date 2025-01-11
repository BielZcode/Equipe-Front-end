package net.ftclient.clientcommon.gui;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import net.ftclient.client.Gui.GuiFTCConfigs;
import net.ftclient.clientcommon.Cosmetics.GuiCosmetics;
import net.ftclient.clientcommon.FuryTigris;
import net.ftclient.clientcommon.Manager.Account;
import net.ftclient.clientcommon.Manager.GuiAccountManager;
import net.ftclient.clientcommon.gui.Button.GuiFTCButton;
import net.ftclient.clientcommon.gui.Button.GuiFTCButtonIcon;
import net.ftclient.clientcommon.utils.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class FTMainMenu extends GuiScreen implements GuiYesNoCallback
{
    /*
    * By Games-Static Equipe Back-end
    * Classe FTMainMenu.java
    * Data.10.01.2025
    */

    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private static UnicodeFontRenderer ufr;
    private static UnicodeFontRenderer ufrmedium;
    private boolean isOpening = false; // Indica se o painel está abrindo ou fechando
    public static final ResourceLocation BURGER = new ResourceLocation("ftclient/gui/icons/burger.png");
    private boolean buttonexit = false;
    private float updateCounter;
    private GuiButton buttonResetDemo;
    private int panoramaTimer;
    private DynamicTexture viewportTexture;
    private final Object threadLock = new Object();
    private String openGLWarning1;
    private String openGLWarning2;
    private String openGLWarningLink;
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/furytigrislogo.png");
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {new ResourceLocation("ftclient/textures/background/panorama_0.png"), new ResourceLocation("ftclient/textures/background/panorama_1.png"), new ResourceLocation("ftclient/textures/background/panorama_2.png"), new ResourceLocation("ftclient/textures/background/panorama_3.png"), new ResourceLocation("ftclient/textures/background/panorama_4.png"), new ResourceLocation("ftclient/textures/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;

    private GuiButton realmsButton;

    public FTMainMenu()
    {
        this.openGLWarning2 = field_96138_a;

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported())
        {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    public void updateScreen()
    {
        ++this.panoramaTimer;
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    public void initGui()
    {
        /*
        * FuryTigris.getInstance().getDiscordRP().update("Parado", "Main Menu"); Atualizado Estado do Main Menu
        * Libraries DiscordRP
        */

        FuryTigris.getInstance().getDiscordRP().update("Parado", "Main Menu");
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);

        int i = 24;
        int j = this.height / 4 + 48;

        GuiButtonsFTC();

        if (this.mc.isDemo())
        {
            this.addDemoButtons(j, 24);
        }
        else
        {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }

        /*
        * Botões em Icones do FTMainMenu.java
        * By GameStatic
        * usando GuiFTCButtonIcon.java
        */

        this.buttonList.add(new GuiFTCButtonIcon(4, this.width / 2 + 330, j + 72 + -220, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/assets/exit-17x17.png"), "Sair"));
        this.buttonList.add(new GuiFTCButtonIcon(58, this.width / 2 - 76, this.height - 28, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/mainmenu/globe-20x20.png"), "Forum"));
        this.buttonList.add(new GuiFTCButtonIcon(0, this.width / 2 - 54, this.height - 28, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/mainmenu/cog-20x20.png"), "Configurações"));
        this.buttonList.add(new GuiFTCButtonIcon(45, this.width / 2 - 32, this.height - 28, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/assets/language.png"), "Linguagens"));
        this.buttonList.add(new GuiFTCButtonIcon(59, this.width / 2 - 10, this.height - 28, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/assets/discord-64.png"), "Discord"));
        this.buttonList.add(this.realmsButton = new GuiFTCButtonIcon(14, this.width / 2 + 12, this.height - 28, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/mainmenu/realms-24x24.png"), "Minecraft Realms"));
        this.buttonList.add(new GuiFTCButtonIcon(57, this.width / 2 + 34, this.height - 28, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/assets/info.png"), "Faq"));
        this.buttonList.add(new GuiFTCButtonIcon(56, this.width / 2 + 56, this.height - 28, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/assets/cosmetics-20x20.png"), "Cosmeticos Em Breve"));

        synchronized (this.threadLock)
        {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }

        this.mc.func_181537_a(false);
    }

    public void GuiButtonsFTC() {
        /*
        * Carrega a conta que esta na pasta /AppData/Roaming/ftclient/profiles/accounts/conta.json
        * By GameStatic
        * Logica de Criação/Redifinição em GuiAccountManager.java/GuiLogin.java
        * */

        File accountFile = new File(System.getProperty("user.home") + "/AppData/Roaming/ftclient/profiles/accounts/conta.json");

        int j = this.height / 4 + 48;

        this.buttonList.add(new GuiFTCButtonIcon(51, this.width / 2 - 347, j + 72 + -220, 19, 19, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-button-medium.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), new ResourceLocation("ftclient/textures/icons/assets/man-user-16x16.png"), "Contas"));
        this.buttonList.add(new GuiFTCButton(2, this.width / 2 - 100, j + 72 + -0, 200, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "LOJA"));
    }

    private static void logCrashToFile(Exception e) {

        String logFilePath = System.getProperty("user.home") + "/AppData/Roaming/ftclient/logs/crash.data." +
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".txt";
        File logFile = new File(logFilePath);

        try (FileWriter writer = new FileWriter(logFile, true)) {

            writer.write("Timestamp: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
            writer.write("Exception: " + e.toString() + "\n");
            writer.write("Stack Trace:\n");

            for (StackTraceElement element : e.getStackTrace()) {

                writer.write("\t" + element.toString() + "\n");

            }
            writer.write("\n\n");

        } catch (IOException ioException) {
            System.err.println("Erro ao salvar o log de crash: " + ioException.getMessage());
        }
    }

    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_)
    {
        int j = this.height / 4 + 48;

        this.buttonList.add(new GuiFTCButton(1, this.width / 2 - 100, j + 72 + -50, 200, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "SINGLEPLAYER"));
        this.buttonList.add(new GuiFTCButton(2, this.width / 2 - 100, j + 72 + -25, 200, 20, new ResourceLocation("minecraft", "ftclient/textures/menu/home/regular-outline-button.svg_large.png"), new ResourceLocation("minecraft", "ftclient/textures/menu/home/hoverstate-button.svg_large.png"), GameSettings.Options.FORCE_UNICODE_FONT, "MULTIPLAYER"));
    }

    private void addDemoButtons(int p_73972_1_, int p_73972_2_)
    {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null)
        {
            this.buttonResetDemo.enabled = false;
        }
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        /*
         * AccountManager
         */
        if (button.id == 29)
        {
            this.mc.displayGuiScreen(new GuiAccountManager());

        }

        /*
         * Em breve Cosmeticos
         */
        if (button.id == 56) {
            this.mc.displayGuiScreen(new GuiCosmetics());
        }

        /*
        * Faq
        */
        if (button.id == 57) {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://furytigrisnet.forumeiros.com/faq"));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        /*
         * Forum
         */
        if (button.id == 58) {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://furytigrisnet.forumeiros.com/f1-forum-furytigris"));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        /*
         * Configs
         */
        if (button.id == 26)
        {
            this.mc.displayGuiScreen(new GuiFTCConfigs());
        }

        /*
         * Discord
         */
        if (button.id == 59) {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://discord.gg/ajd8tNKG"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
         * Botão sidebar
         */
        if (button.id == 44) {
            isOpening = !isOpening;
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer());
        }

        if (button.id == 14 && this.realmsButton.visible)
        {
            this.switchToRealms();
        }

        if (button.id == 4)
        {
            this.mc.shutdown();
        }

        if (button.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (button.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }

        if (button.id == 45) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 50) {
            buttonexit = true;
        }

        if (button.id == 51) {
            this.mc.displayGuiScreen(new GuiAccountManager());
        }
    }

    private void switchToRealms()
    {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    public void confirmClicked(boolean result, int id)
    {
        if (result && id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (id == 13)
        {
            if (result)
            {
                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.openGLWarningLink)});
                }
                catch (Throwable throwable)
                {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    @Override
    public void onGuiClosed() {
        buttonexit = false;
    }

    private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int i = 8;

        for (int j = 0; j < i * i; ++j)
        {
            GlStateManager.pushMatrix();
            float f = ((float)(j % i) / (float)i - 0.5F) / 64.0F;
            float f1 = ((float)(j / i) / (float)i - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float)this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float)this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int k = 0; k < 6; ++k)
            {
                GlStateManager.pushMatrix();

                if (k == 1)
                {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 2)
                {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 3)
                {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 4)
                {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (k == 5)
                {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                this.mc.getTextureManager().bindTexture(titlePanoramaPaths[k]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                float f3 = 0.0F;
                worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox(float p_73968_1_)
    {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        int i = 3;

        for (int j = 0; j < i; ++j)
        {
            float f = 1.0F / (float)(j + 1);
            int k = this.width;
            int l = this.height;
            float f1 = (float)(j - i / 2) / 256.0F;
            worldrenderer.pos((double)k, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos((double)k, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_)
    {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
        float f1 = (float)this.height * f / 256.0F;
        float f2 = (float)this.width * f / 256.0F;
        int i = this.width;
        int j = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, (double)j, (double)this.zLevel).tex((double)(0.5F - f1), (double)(0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos((double)i, (double)j, (double)this.zLevel).tex((double)(0.5F - f1), (double)(0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos((double)i, 0.0D, (double)this.zLevel).tex((double)(0.5F + f1), (double)(0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(0.5F + f1), (double)(0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, partialTicks);
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

        if(ufrmedium == null) {
            ufrmedium = UnicodeFontRenderer.getFontOnPC("Arial", 16);
        }

        ufrmedium.drawString("FuryTigris Client 1.8.8 (1.0.0/master)", 6, this.height - 15, 0x0544C4C);super.drawScreen(mouseX, mouseY, partialTicks);

        ufr.drawString("- Client de minecraft Pirata Completo de Minecraft 1.8.8 -", this.width / 2 - 115, this.width / 5 + 14, 0x0898989);

        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0)
        {
            drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        drawLogo(this.width / 2 - 30, this.height / 15 + 5, GuiTextureButton.LOGO);
        drawImage(this.width / 2 - 100, this.height / 5 + 30, GuiTextureButton.LOGOCLIENT);
    }


    private void drawImage(int x, int y, ResourceLocation texture) {
        this.mc.getTextureManager().bindTexture(texture);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 200, 20, 200, 20);
    }

    private void drawLogo(int x, int y, ResourceLocation texture) {
        this.mc.getTextureManager().bindTexture(texture);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 60, 60, 60, 60);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock)
        {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }
}
