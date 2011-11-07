package monitor.view;

import monitor.common.BoolType;
import monitor.repository.model.BiNotifyMessage;
import monitor.service.BiNotifyMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
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
public class BiNotifyMsgAction {

    private static Logger logger = LoggerFactory.getLogger(BiNotifyMsgAction.class);
    private List<BiNotifyMessage> notifyMessageList;
    private BoolType boolType = BoolType.FALSE;
    private Map<String, String> bankMap;
    private Map<String, String> tradeMap;

     private String opCode;
    private String bankCode;
    private String startDate;
    private String endDate;

    private List<SelectItem> tradeCodeList;
    private List<SelectItem> bankCodeList;

    @ManagedProperty(value = "#{biNotifyMessageService}")
    private BiNotifyMessageService biNotifyMessageService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;

    @PostConstruct
    public void init() {
        tradeCodeList = toolsService.getEnuSelectItemList("TRADECODE", false, false);
        bankCodeList = toolsService.getEnuSelectItemList("BANKCODE", false, false);
        bankMap = platformService.selectEnuItemValueToVLMap("BANKCODE");
        tradeMap = platformService.selectEnuItemValueToVLMap("TRADECODE");
    }

    public void qryNotifyMsg() {
        notifyMessageList = biNotifyMessageService.qryRecords(opCode, bankCode, startDate, endDate);
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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public List<SelectItem> getBankCodeList() {
        return bankCodeList;
    }

    public void setBankCodeList(List<SelectItem> bankCodeList) {
        this.bankCodeList = bankCodeList;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public List<SelectItem> getTradeCodeList() {
        return tradeCodeList;
    }

    public void setTradeCodeList(List<SelectItem> tradeCodeList) {
        this.tradeCodeList = tradeCodeList;
    }
}
