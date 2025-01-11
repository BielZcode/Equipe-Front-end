package net.ftclient.clientcommon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    // Caminho base para o diretório de logs no AppData\Roaming
    private static final String LOG_DIR = System.getenv("APPDATA") + "\\.minecraft\\ftclient\\client\\logs";
    private static final String LOG_FILE = LOG_DIR + "\\furytigris_client_log.txt";

    public static void log(String message) {
        // Exibe no console
        System.out.println(message);

        // Cria o diretório e o arquivo, se necessário
        try {
            File logDir = new File(LOG_DIR);
            File logFile = new File(LOG_FILE);

            // Cria o diretório se não existir
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Salva a mensagem no arquivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
