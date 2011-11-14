package monitor.view;

import monitor.repository.model.BiNotifyMsgCnt;
import monitor.service.BiNotifyMsgCntService;
import org.primefaces.model.chart.PieChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.DateFormatter;
import skyline.common.utils.MessageUtil;
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
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class BankDataFlowAction {

    private static Logger logger = LoggerFactory.getLogger(BankDataFlowAction.class);

    private String opCode;
    private String bankCode;
    private String startDate = DateFormatter.getSdfdate6() + "-01";
    private String endDate = DateFormatter.getSdfdate8();

    private List<SelectItem> tradeCodeList;
    private List<SelectItem> bankCodeList;
    private Map<String, String> bankMap;
    private Map<String, String> tradeMap;
    private List<BiNotifyMsgCnt> notifyMsgCntList;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{biNotifyMsgCntService}")
    private BiNotifyMsgCntService biNotifyMsgCntService;

    private PieChartModel pieModel;

    @PostConstruct
    public void init() {
        //tradeCodeList = toolsService.getEnuSelectItemList("TRADECODE", false, false);
        bankCodeList = toolsService.getEnuSelectItemList("BANKCODE", false, false);
        bankMap = platformService.selectEnuItemValueToVLMap("BANKCODE");
        tradeMap = platformService.selectEnuItemValueToVLMap("TRADECODE");
        pieModel = new PieChartModel();

    }

    public String showChart() {
        //notifyMsgCntList = biNotifyMsgCntService.qryRecordsCnt("2005", bankCode, startDate, endDate);
        notifyMsgCntList = biNotifyMsgCntService.qryRecordsCnt(opCode, bankCode, startDate, endDate);
        if (notifyMsgCntList.isEmpty()) {
            MessageUtil.addWarn("查询数据记录结果为空！");
        }
        if (pieModel != null) {
            pieModel = new PieChartModel();
        }
        createPieModel();
        return null;
    }

    private void createPieModel() {
        if (notifyMsgCntList != null && !notifyMsgCntList.isEmpty()) {
            for (BiNotifyMsgCnt bnmc : notifyMsgCntList) {
                pieModel.set(tradeMap.get(bnmc.getOpcode()), bnmc.getCount());
            }
        }

    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public BiNotifyMsgCntService getBiNotifyMsgCntService() {
        return biNotifyMsgCntService;
    }

    public void setBiNotifyMsgCntService(BiNotifyMsgCntService biNotifyMsgCntService) {
        this.biNotifyMsgCntService = biNotifyMsgCntService;
    }

    public List<BiNotifyMsgCnt> getNotifyMsgCntList() {
        return notifyMsgCntList;
    }

    public void setNotifyMsgCntList(List<BiNotifyMsgCnt> notifyMsgCntList) {
        this.notifyMsgCntList = notifyMsgCntList;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
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

    public List<SelectItem> getBankCodeList() {
        return bankCodeList;
    }

    public void setBankCodeList(List<SelectItem> bankCodeList) {
        this.bankCodeList = bankCodeList;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public Map<String, String> getTradeMap() {
        return tradeMap;
    }

    public void setTradeMap(Map<String, String> tradeMap) {
        this.tradeMap = tradeMap;
    }


}
