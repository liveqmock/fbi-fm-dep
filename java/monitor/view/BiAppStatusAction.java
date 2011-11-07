package monitor.view;

import monitor.common.BoolType;
import monitor.repository.model.BiNotifyMessage;
import monitor.service.BiNotifyMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.service.PlatformService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: ÏÂÎç2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class BiAppStatusAction {

    private static Logger logger = LoggerFactory.getLogger(BiAppStatusAction.class);
    private List<BiNotifyMessage> notifyMessageList;
    private BoolType boolType = BoolType.FALSE;
    private Map<String, String> bankMap;
    private Map<String, String> tradeMap;

    @ManagedProperty(value = "#{biNotifyMessageService}")
    private BiNotifyMessageService biNotifyMessageService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    @PostConstruct
    public void init() {
        notifyMessageList = biNotifyMessageService.qryLastBiNotifyMsgs();
        bankMap = platformService.selectEnuItemValueToVLMap("BANKCODE");
        tradeMap = platformService.selectEnuItemValueToVLMap("TRADECODE");
    }

    public BiNotifyMessageService getBiNotifyMessageService() {
        return biNotifyMessageService;
    }

    public void setBiNotifyMessageService(BiNotifyMessageService biNotifyMessageService) {
        this.biNotifyMessageService = biNotifyMessageService;
    }

    public List<BiNotifyMessage> getNotifyMessageList() {
        return notifyMessageList;
    }

    public void setNotifyMessageList(List<BiNotifyMessage> notifyMessageList) {
        this.notifyMessageList = notifyMessageList;
    }

    public Map<String, String> getTradeMap() {
        return tradeMap;
    }

    public void setTradeMap(Map<String, String> tradeMap) {
        this.tradeMap = tradeMap;
    }

    public BoolType getBoolType() {
        return boolType;
    }

    public void setBoolType(BoolType boolType) {
        this.boolType = boolType;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public Map<String, String> getBankMap() {
        return bankMap;
    }

    public void setBankMap(Map<String, String> bankMap) {
        this.bankMap = bankMap;
    }
}
