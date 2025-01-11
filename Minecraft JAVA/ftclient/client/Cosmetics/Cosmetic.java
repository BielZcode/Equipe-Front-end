package net.ftclient.client.Cosmetics;

public enum Cosmetic {
    NATAL("Natal 2024", "ftclient/textures/cosmetics/templates/capanatal.png"),
    UNIVERSO("Capa Universo", "ftclient/textures/cosmetics/templates/universo.png"),
    OLHODRAGÂO("Olho Dragão", "ftclient/textures/cosmetics/templates/olhodragão.png"),
    ZINHOCAPE("Capa ZinhoZin", "ftclient/textures/cosmetics/templates/zinhocape.png");

    private final String name;
    private final String texturePath;

    Cosmetic(String name, String texturePath) {
        this.name = name;
        this.texturePath = texturePath;
    }

    public String getName() {
        return name;
    }

    public String getTexturePath() {
        return texturePath;
    }
}

