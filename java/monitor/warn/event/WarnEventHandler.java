package monitor.warn.event;

public interface WarnEventHandler {
    void onWarn(WarnEvent warnEvent) throws Exception;
}
