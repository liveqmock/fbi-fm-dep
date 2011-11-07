package dep.common;

import java.util.Hashtable;

public enum BiDownType implements EnumApp {
    NORMAL("1", "����ͨ��"),
    DOWN("2", "��ش����"),
    CHECKOK("3", "���ͨ��"),
    REFUSE("4", "�˻�");

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
