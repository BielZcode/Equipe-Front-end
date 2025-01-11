package net.ftclient.clientcommon.utils.Manager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.ftclient.clientcommon.Manager.GuiAccountManager;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GuiLogin extends GuiScreen {

    private GuiTextField username;

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) { // ID do botão de login
            String newUsername = this.username.getText();

            if (newUsername.equals("")) {
                this.mc.displayGuiScreen(new GuiLogin());
            } else {
                saveUsernameToFile(newUsername);
                net.ftclient.clientcommon.utils.Manager.SessionChanger.getInstance().setUserOffline(newUsername);
                this.mc.displayGuiScreen(new GuiAccountManager());
            }
        }
    }

    private void saveUsernameToFile(String newUsername) {
        try {
            File accountFile = new File(System.getProperty("user.home") + "/AppData/Roaming/ftclient/profiles/accounts/conta.json");

            // Crie o arquivo se não existir
            if (!accountFile.exists()) {
                accountFile.createNewFile();
            }

            // Use o Gson para salvar o novo nome de usuário no arquivo
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", newUsername);

            // Salve o arquivo JSON
            FileWriter writer = new FileWriter(accountFile);
            writer.write(jsonObject.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void drawScreen(final int x2, final int y2, final float z2) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.username.drawTextBox();
        Gui.drawCenteredString(this.mc.fontRendererObj, "Username", (int) (this.width / 2), (int) (sr.getScaledHeight() / 2 - 65), -1);
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 10, this.height / 2 - 20, 120, 20, I18n.format("Login (Cracked)", new Object[0])));

        this.username = new GuiTextField(100, this.fontRendererObj, this.width / 2 - 50 - 10, sr.getScaledHeight() / 2 - 50, 120, 20);
        this.username.setFocused(true);

        // Carrega o nome do usuário do arquivo JSON
        String loadedUsername = loadUsernameFromFile();
        if (loadedUsername != null) {
            this.username.setText(loadedUsername);
        }

        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(final char character, final int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t' && !this.username.isFocused()) {
            this.username.setFocused(true);
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(final int x2, final int y2, final int button) {
        try {
            super.mouseClicked(x2, y2, button);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.loadEntityShader(null);
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
    }

    /**
     * Carrega o nome do usuário do arquivo JSON.
     *
     * @return Nome do usuário ou null se não encontrado.
     */
    private String loadUsernameFromFile() {
        try {
            File accountFile = new File(System.getProperty("user.home") + "/AppData/Roaming/ftclient/profiles/accounts/conta.json");
            if (accountFile.exists()) {
                FileReader reader = new FileReader(accountFile);
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(reader);
                reader.close();

                if (jsonObject.has("username")) {
                    return jsonObject.get("username").getAsString();
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar o nome de usuário: " + e.getMessage());
        }
        return null;
    }
}
