package monitor.common;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-10
 * Time: ÏÂÎç1:46
 * To change this template use File | Settings | File Templates.
 */
public class QueueMessage {

    private String messageId;
    private String sendTime;
    private String text;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
