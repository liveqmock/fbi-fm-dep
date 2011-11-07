package monitor.common;

import java.util.Hashtable;

public enum BoolType implements EnumApp {
    FALSE("0", "·ñ"),
    TRUE("1", "ÊÇ");

    private String code = null;
    private String title = null;
    private static Hashtable<String, BoolType> aliasEnums;

    BoolType(String code, String title) {
        this.init(code, title);
    }

    @SuppressWarnings("unchecked")
    private void init(String code, String title) {
        this.code = code;
        this.title = title;
        synchronized (this.getClass()) {
            if (aliasEnums == null) {
                aliasEnums = new Hashtable();
            }
        }
        aliasEnums.put(code, this);
        aliasEnums.put(title, this);
    }

    public static BoolType valueOfAlias(String alias) {
        return aliasEnums.get(alias);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}
