package net.ftclient.client.Discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {

    private boolean running = true;
    private long created = 0;

    public void start() {

        this.created = System.currentTimeMillis();

        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {

            @Override
            public void apply(DiscordUser discordUser) {
                System.out.println("Nome DC " + discordUser.username + "#" + discordUser.discriminator + ".");
                update("Iniciando", "");
            }
        }).build();

        DiscordRPC.discordInitialize("1325815929401118730", handlers, true);

        new Thread("Discord RPC Callback") {

            @Override
            public void run() {

                while(running) {
                    DiscordRPC.discordRunCallbacks();
                }

            }
        }.start();
    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String firstline, String secondLine) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("Large", "");
        b.setDetails(firstline);
        b.setStartTimestamps(created);

        DiscordRPC.discordUpdatePresence(b.build());
    }
}
