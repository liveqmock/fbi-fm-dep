package monitor.view;

import monitor.common.BoolType;
import monitor.repository.model.DepBirouteLog;
import monitor.service.BiRouteLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;
import skyline.service.PlatformService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-10-27
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class BiRouteLogAction {

    private static Logger logger = LoggerFactory.getLogger(BiRouteLogAction.class);
    private Map<String, String> appMap;
    private Map<String, String> tradeMap;
    private Map<String, String> bankMap;
    private Map<String, String> downMap;

    private String opCode;
    private String bankCode;
    private String startDate;
    private String endDate;

    private BoolType boolType = BoolType.TRUE;

    private List<SelectItem> tradeCodeList;
    private List<SelectItem> bankCodeList;

    @ManagedProperty(value = "#{biRouteLogService}")
    private BiRouteLogService biRouteLogService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;

    private List<DepBirouteLog> logList;

    @PostConstruct
    public void init() {
        tradeCodeList = toolsService.getEnuSelectItemList("TRADECODE", false, false);
        bankCodeList = toolsService.getEnuSelectItemList("BANKCODE", false, false);
        appMap = platformService.selectEnuItemValueToVLMap("APPCODE");
        bankMap = platformService.selectEnuItemValueToVLMap("BANKCODE");
        tradeMap = platformService.selectEnuItemValueToVLMap("TRADECODE");
        downMap = platformService.selectEnuItemValueToVLMap("DOWNCODE");

    }

    public void qryLogList() {
        try {
            logList = biRouteLogService.qryRecords(opCode, bankCode, startDate, endDate);
        } catch (ParseException e) {
            MessageUtil.addError("日期输入错误，请检查日期格式！");
        }
    }

    //======================================================

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

    public Map<String, String> getAppMap() {
        return appMap;
    }

    public void setAppMap(Map<String, String> appMap) {
        this.appMap = appMap;
    }

    public Map<String, String> getDownMap() {
        return downMap;
    }

    public void setDownMap(Map<String, String> downMap) {
        this.downMap = downMap;
    }

    public Map<String, String> getBankMap() {
        return bankMap;
    }

    public void setBankMap(Map<String, String> bankMap) {
        this.bankMap = bankMap;
    }

    public BoolType getBoolType() {
        return boolType;
    }

    public void setBoolType(BoolType boolType) {
        this.boolType = boolType;
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

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
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

    public Map<String, String> getTradeMap() {
        return tradeMap;
    }

    public void setTradeMap(Map<String, String> tradeMap) {
        this.tradeMap = tradeMap;
    }

    public BiRouteLogService getBiRouteLogService() {
        return biRouteLogService;
    }

    public void setBiRouteLogService(BiRouteLogService biRouteLogService) {
        this.biRouteLogService = biRouteLogService;
    }

    public List<DepBirouteLog> getLogList() {
        return logList;
    }

    public void setLogList(List<DepBirouteLog> logList) {
        this.logList = logList;
    }
}
