package si.um.feri.tevzki.gameElements;

public enum TileType {

    NONE(0),
    SNOW(1),
    ROAD(2),
    GRASS(3),
    ICE(4);

    private final int id;

    TileType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Convert number → TileType
    public static TileType fromId(int id) {
        for (TileType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return NONE; // safe fallback
    }
}
