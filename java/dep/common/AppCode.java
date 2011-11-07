package dep.common;

import java.util.Hashtable;

public enum AppCode implements EnumApp {
    FMQD("fmqd", "房产交易资金监管系统"),
    DEP("dep", "数据交换平台");

    private String code = null;
    private String title = null;
    private static Hashtable<String, AppCode> aliasEnums;

    AppCode(String code, String title) {
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

    public static AppCode valueOfAlias(String alias) {
        return aliasEnums.get(alias);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}
