package skyline.quartz.common;

import java.util.Hashtable;

public enum JobStatusType implements EnumApp {
    ACQUIRED("ACQUIRED", "运行中"),
    PAUSED("PAUSED", "已暂停"),
    WAITING("WAITING", "等待中");

    private String code = null;
    private String title = null;
    private static Hashtable<String, JobStatusType> aliasEnums;

    JobStatusType(String code, String title) {
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

    public static JobStatusType valueOfAlias(String alias) {
        return aliasEnums.get(alias);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}
