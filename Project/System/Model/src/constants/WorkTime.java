package constants;

public class WorkTime {

    public static final String TYPE_STRING_START = "Dolazak na posao";
    public static final String TYPE_STRING_END = "Odlazak sa posla";
    public static final String TYPE_STRING_PAUSE_START = "Odlazak na pauzu";
    public static final String TYPE_STRING_PAUSE_END = "Dolazak sa pauze";

    public static final int TYPE_START = 0x4534;
    public static final int TYPE_END =0xab45;
    public static final int TYPE_PAUSE_START = 0x9043;
    public static final int TYPE_PAUSE_END = 0x41bb;

    public static String resolveType(int type) {

        switch (type) {
            case TYPE_START:
                return TYPE_STRING_START;
            case TYPE_END:
                return TYPE_STRING_END;
            case TYPE_PAUSE_START:
                return TYPE_STRING_PAUSE_START;
            case TYPE_PAUSE_END:
                return TYPE_STRING_PAUSE_END;
            default:
                return null;
        }
    }

    public static int resolveTypeFromString(String type) {
        switch (type) {
            case TYPE_STRING_START:
                return TYPE_START;
            case TYPE_STRING_END:
                return TYPE_END;
            case TYPE_STRING_PAUSE_START:
                return TYPE_PAUSE_START;
            case TYPE_STRING_PAUSE_END:
                return TYPE_PAUSE_END;
            default:
                return -1;
        }
    }

    public static String[] getStatusTypes() {
        return new String[] {
                TYPE_STRING_START,
                TYPE_STRING_END,
                TYPE_STRING_PAUSE_START,
                TYPE_STRING_PAUSE_END
        };
    }
}
