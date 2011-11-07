package dep.common;

import java.util.Hashtable;

public enum BiDownType implements EnumApp {
    NORMAL("1", "正常通过"),
    DOWN("2", "落地待审核"),
    CHECKOK("3", "审核通过"),
    REFUSE("4", "退回");

    private String code = null;
    private String title = null;
    private static Hashtable<String, BiDownType> aliasEnums;

    BiDownType(String code, String title) {
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

    public static BiDownType valueOfAlias(String alias) {
        return aliasEnums.get(alias);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}
