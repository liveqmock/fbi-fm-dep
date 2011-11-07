package dep.fmqd.service;

import dep.gateway.domain.BaseBean;
import dep.gateway.domain.T000.*;
import dep.gateway.domain.T200.T2004Req;
import dep.gateway.domain.T200.T2005Req;
import dep.fmqd.repository.dao.BiNotifyMessageMapper;
import dep.fmqd.repository.dao.common.CommonMapper;
import dep.fmqd.repository.model.*;
import dep.common.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-9-29
 * Time: 下午2:46
 * To change this template use File | Settings | File Templates.
 */
/*
接收账户交易冲正指令0003:BI_NOTIFY_REVERSE
接收账户付款退票指令0004:bi_notify_dishonour
接收账户利息记录0005:BI_NOTIFY_INTEREST
接收监管账户冻结解冻状态0006：BI_NOTIFY_ACCTSTATUS
接收对账明细信息0007:bi_notify_acctdetail
接收预售房监管账户计划付款记录2004:BI_NOTIFY_PAY
接收预售房合同收支记录2005:BI_NOTIFY_CONDOINOUT
 */
@Service
public class BiDbService {

    private static Logger logger = LoggerFactory.getLogger(BiDbService.class);
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private BiNotifyMessageMapper messageMapper;
    @Autowired
    private BiNotifyReverseService biNotifyReverseService;
    @Autowired
    private BiNotifyDishonourService biNotifyDishonourService;
    @Autowired
    private BiNotifyInterestService biNotifyInterestService;
    @Autowired
    private BiNotifyAcctstatusService biNotifyAcctstatusService;
    @Autowired
    private BiNotifyAcctdetailService biNotifyAcctdetailService;
    @Autowired
    private BiNotifyPayService biNotifyPayService;
    @Autowired
    private BiNotifyCondoinoutService biNotifyCondoinoutService;
    
    public int handleMessage(String opCode, String datagram) {
       int nOpCode = Integer.parseInt(opCode);
       switch (nOpCode) {
           case 3 : return save0003Reverse(datagram);
           case 4 : return save0004Dishonour(datagram);
           case 5 : return save0005Interest(datagram);
           case 6 : return save0006Acctstatus(datagram);
           case 7 : return save0007Acctdetail(datagram);
           case 2004 : return save2004Pay(datagram);
           case 2005 : return save2005Condoinout(datagram);
           default: return 0;
       }
    }

    // 接收账户交易冲正指令0003:BI_NOTIFY_REVERSE
    @Transactional
    private int save0003Reverse(String datagram) {

        T0003Req req = (T0003Req) BaseBean.toObject(T0003Req.class, datagram);

        BiNotifyReverse biNotifyReverse = new BiNotifyReverse();
        biNotifyReverse.setOpcode(req.head.OpCode);
        biNotifyReverse.setBankcode(req.head.BankCode);
        biNotifyReverse.setAcct(req.param.Acct);
        biNotifyReverse.setAcctname(req.param.AcctName);
        biNotifyReverse.setBankserial(req.param.BankSerial);
        biNotifyReverse.setReason(req.param.Reason);

        long rowID = commonMapper.selectNewRowID();
        biNotifyReverse.setRowId(rowID);
        biNotifyReverse.setGetFlag("0");
        biNotifyReverse.setCreatedBy(-9495L);
        biNotifyReverse.setCreatedDate(new Date());
        biNotifyReverse.setLastUpdBy(-9495L);
        biNotifyReverse.setLastUpdDate(new Date());
        biNotifyReverse.setModificationNum(1);

        long messageRowID = insertMessageByDatagram(datagram);
        if(messageRowID != 0L) {
             biNotifyReverse.setNotifyId(messageRowID);
             return biNotifyReverseService.insertRecord(biNotifyReverse);
        }else return -1;
    }

    // 接收账户付款退票指令0004:bi_notify_dishonour
    @Transactional
    private int save0004Dishonour(String datagram) {

        T0004Req req = (T0004Req) BaseBean.toObject(T0004Req.class, datagram);

        BiNotifyDishonour biNotifyDishonour = new BiNotifyDishonour();
        biNotifyDishonour.setOpcode(req.head.OpCode);
        biNotifyDishonour.setBankcode(req.head.BankCode);
        biNotifyDishonour.setAcct(req.param.Acct);
        biNotifyDishonour.setAcctname(req.param.AcctName);
        biNotifyDishonour.setBankserial(req.param.BankSerial);
        biNotifyDishonour.setReason(req.param.Reason);
        biNotifyDishonour.setTradedate(req.param.Date);
        biNotifyDishonour.setTradetime(req.param.Time);
        biNotifyDishonour.setRowId(commonMapper.selectNewRowID());
        biNotifyDishonour.setGetFlag("0");
        biNotifyDishonour.setCreatedBy(-9495L);
        biNotifyDishonour.setCreatedDate(new Date());
        biNotifyDishonour.setLastUpdBy(-9495L);
        biNotifyDishonour.setLastUpdDate(new Date());
        biNotifyDishonour.setModificationNum(1);

        long messageRowID = insertMessageByDatagram(datagram);
        logger.info("bi_notify_message.notifyId = " + messageRowID);
        if(messageRowID != 0L) {
             biNotifyDishonour.setNotifyId(messageRowID);
             return biNotifyDishonourService.insertRecord(biNotifyDishonour);
        }else return -1;
    }

    // 接收账户利息记录0005:BI_NOTIFY_INTEREST
    @Transactional
    private int save0005Interest(String datagram) {

        logger.info("处理利息记录报文数据：" + datagram);
        T0005Req req = (T0005Req) BaseBean.toObject(T0005Req.class, datagram);

        BiNotifyInterest biNotifyInterest = new BiNotifyInterest();
        biNotifyInterest.setOpcode(req.head.OpCode);
        biNotifyInterest.setBankcode(req.head.BankCode);
        biNotifyInterest.setAcct(req.param.Acct);
        biNotifyInterest.setAcctname(req.param.AcctName);
        biNotifyInterest.setBankserial(req.param.BankSerial);
        biNotifyInterest.setAmt(req.param.Amt);
        biNotifyInterest.setPurpose(req.param.Purpose);
        biNotifyInterest.setTradedate(req.param.Date);
        biNotifyInterest.setTradetime(req.param.Time);

        long rowID = commonMapper.selectNewRowID();
        biNotifyInterest.setRowId(rowID);

        biNotifyInterest.setRowId(commonMapper.selectNewRowID());
        biNotifyInterest.setGetFlag("0");
        biNotifyInterest.setCreatedBy(-9495L);
        biNotifyInterest.setCreatedDate(new Date());
        biNotifyInterest.setLastUpdBy(-9495L);
        biNotifyInterest.setLastUpdDate(new Date());
        biNotifyInterest.setModificationNum(1);

        long messageRowID = insertMessageByDatagram(datagram);
        if(messageRowID != 0L) {
             biNotifyInterest.setNotifyId(messageRowID);
             return biNotifyInterestService.insertRecord(biNotifyInterest);
        }else return -1;

    }

    // 接收监管账户冻结解冻状态0006：BI_NOTIFY_ACCTSTATUS
    @Transactional
    private int save0006Acctstatus(String datagram) {

        T0006Req req = (T0006Req) BaseBean.toObject(T0006Req.class, datagram);

        BiNotifyAcctstatus biNotifyAcctstatus = new BiNotifyAcctstatus();
        biNotifyAcctstatus.setOpcode(req.head.OpCode);
        biNotifyAcctstatus.setBankcode(req.head.BankCode);
        biNotifyAcctstatus.setAcct(req.param.Acct);
        biNotifyAcctstatus.setAcctname(req.param.AcctName);
        biNotifyAcctstatus.setStatus(req.param.Status);
        biNotifyAcctstatus.setBalance(req.param.Balance);
        biNotifyAcctstatus.setLockamt(req.param.LockAmt);
        biNotifyAcctstatus.setRowId(commonMapper.selectNewRowID());
        biNotifyAcctstatus.setGetFlag("0");
        biNotifyAcctstatus.setCreatedBy(-9495L);
        biNotifyAcctstatus.setCreatedDate(new Date());
        biNotifyAcctstatus.setLastUpdBy(-9495L);
        biNotifyAcctstatus.setLastUpdDate(new Date());
        biNotifyAcctstatus.setModificationNum(1);

        long messageRowID = insertMessageByDatagram(datagram);
        if(messageRowID != 0L) {
             biNotifyAcctstatus.setNotifyId(messageRowID);
             return biNotifyAcctstatusService.insertRecord(biNotifyAcctstatus);
        }else return -1;
    }

    // 接收对账明细信息0007:bi_notify_acctdetail
    @Transactional
    private int save0007Acctdetail(String datagram) {

        T0007Req req = (T0007Req) BaseBean.toObject(T0007Req.class, datagram);

        long messageRowID = insertMessageByDatagram(datagram);
        if(messageRowID != 0L) {
             BiNotifyAcctdetail biNotifyAcctdetail = null;
            for (T0007Req.Param.Record record : req.param.recordList) {
                biNotifyAcctdetail = new BiNotifyAcctdetail();
                biNotifyAcctdetail.setOpcode(req.head.OpCode);
                biNotifyAcctdetail.setBankcode(req.head.BankCode);
                biNotifyAcctdetail.setBankserial(record.BankSerial);
                biNotifyAcctdetail.setTradedate(record.Date);
                biNotifyAcctdetail.setTradetime(record.Time);
                biNotifyAcctdetail.setInoutflag(record.Flag);
                biNotifyAcctdetail.setTradetype(record.Type);
                biNotifyAcctdetail.setContractnum(record.ContractNum);
                biNotifyAcctdetail.setPlandetailno(record.PlanDetailNO);
                biNotifyAcctdetail.setAcctname(record.AcctName);
                biNotifyAcctdetail.setAcct(record.Acct);
                biNotifyAcctdetail.setToname(record.ToName);
                biNotifyAcctdetail.setToacct(record.ToAcct);
                biNotifyAcctdetail.setTobankname(record.ToBankName);
                biNotifyAcctdetail.setAmt(record.Amt);
                biNotifyAcctdetail.setPurpose(record.Purpose);
                biNotifyAcctdetail.setRowId(commonMapper.selectNewRowID());
                biNotifyAcctdetail.setGetFlag("0");
                biNotifyAcctdetail.setCreatedBy(-9495L);
                biNotifyAcctdetail.setCreatedDate(new Date());
                biNotifyAcctdetail.setLastUpdBy(-9495L);
                biNotifyAcctdetail.setLastUpdDate(new Date());
                biNotifyAcctdetail.setModificationNum(1);
                biNotifyAcctdetail.setNotifyId(messageRowID);
                if (biNotifyAcctdetailService.insertRecord(biNotifyAcctdetail) != 1) {
                    throw new RuntimeException("新增接口数据表失败：" + biNotifyAcctdetail.getBankcode());
                } else {
                    biNotifyAcctdetail = null;
                }
            }
            return 1;
        }else return -1;

    }

    // 接收预售房监管账户计划付款记录2004:BI_NOTIFY_PAY
    @Transactional
    private int save2004Pay(String datagram) {

        T2004Req req = (T2004Req) BaseBean.toObject(T2004Req.class, datagram);

        BiNotifyPay biNotifyPay = new BiNotifyPay();
        biNotifyPay.setOpcode(req.head.OpCode);
        biNotifyPay.setBankcode(req.head.BankCode);
        biNotifyPay.setAcct(req.param.Acct);
        biNotifyPay.setAcctname(req.param.AcctName);
        biNotifyPay.setBankserial(req.param.BankSerial);
        biNotifyPay.setTradedate(req.param.Date);
        biNotifyPay.setTradetime(req.param.Time);
        biNotifyPay.setInoutflag(req.param.Flag);
        biNotifyPay.setTradetype(req.param.Type);
        biNotifyPay.setToname(req.param.ToAcctName);
        biNotifyPay.setToacct(req.param.ToAcct);
        biNotifyPay.setTobankname(req.param.ToBankName);
        biNotifyPay.setAmt(req.param.Amt);
        biNotifyPay.setPurpose(req.param.Purpose);
        biNotifyPay.setPlandetailno(req.param.PlanDetailNO);
        biNotifyPay.setRowId(commonMapper.selectNewRowID());
        biNotifyPay.setGetFlag("0");
        biNotifyPay.setCreatedBy(-9495L);
        biNotifyPay.setCreatedDate(new Date());
        biNotifyPay.setLastUpdBy(-9495L);
        biNotifyPay.setLastUpdDate(new Date());
        biNotifyPay.setModificationNum(1);

        long messageRowID = insertMessageByDatagram(datagram);
        if(messageRowID != 0L) {
             biNotifyPay.setNotifyId(messageRowID);
             return biNotifyPayService.insertRecord(biNotifyPay);
        }else return -1;

    }

    // 接收预售房合同收支记录2005:BI_NOTIFY_CONDOINOUT
    @Transactional
    private int save2005Condoinout(String datagram) {

        T2005Req req = (T2005Req) BaseBean.toObject(T2005Req.class, datagram);
        
        BiNotifyCondoinout biNotifyCondoinout = new BiNotifyCondoinout();
        biNotifyCondoinout.setOpcode(req.head.OpCode);
        biNotifyCondoinout.setBankcode(req.head.BankCode);
        biNotifyCondoinout.setAcct(req.param.Acct);
        biNotifyCondoinout.setAcctname(req.param.AcctName);
        biNotifyCondoinout.setContractnum(req.param.ContractNum);
        biNotifyCondoinout.setBankserial(req.param.BankSerial);
        biNotifyCondoinout.setTradedate(req.param.Date);
        biNotifyCondoinout.setTradetime(req.param.Time);
        biNotifyCondoinout.setInoutflag(req.param.Flag);
        biNotifyCondoinout.setTradetype(req.param.Type);
        biNotifyCondoinout.setToname(req.param.ToAcctName);
        biNotifyCondoinout.setToacct(req.param.ToAcct);
        biNotifyCondoinout.setTobankname(req.param.ToBankName);
        biNotifyCondoinout.setAmt(req.param.Amt);
        biNotifyCondoinout.setPurpose(req.param.Purpose);
        biNotifyCondoinout.setRowId(commonMapper.selectNewRowID());
        biNotifyCondoinout.setGetFlag("0");
        biNotifyCondoinout.setCreatedBy(-9495L);
        biNotifyCondoinout.setCreatedDate(new Date());
        biNotifyCondoinout.setLastUpdBy(-9495L);
        biNotifyCondoinout.setLastUpdDate(new Date());
        biNotifyCondoinout.setModificationNum(1);

        long messageRowID = insertMessageByDatagram(datagram);
        if(messageRowID != 0L) {
             biNotifyCondoinout.setNotifyId(messageRowID);
             return biNotifyCondoinoutService.insertRecord(biNotifyCondoinout);
        }else return -1;

    }

    //===============================================

    private long insertMessageByDatagram(String datagram) {

        String opCode = StringHelper.getSubstrBetweenStrs(datagram, "<OpCode>", "</OpCode>");
        String bankCode = StringHelper.getSubstrBetweenStrs(datagram, "<BankCode>", "</BankCode>");

        BiNotifyMessage message = new BiNotifyMessage();
        message.setOpcode(opCode);
        message.setBankcode(bankCode);
        Date date = new Date();
        message.setNotifydate(date);

        long rowID = commonMapper.selectNewRowID();
        message.setRowId(rowID);
        message.setGetFlag("0");
        message.setCreatedBy(-9495L);
        message.setCreatedDate(date);
        message.setLastUpdBy(-9495L);
        message.setLastUpdDate(date);
        message.setModificationNum(1);

        if(messageMapper.insertSelective(message) == 1) {
            return rowID;
        }else {
            return 0L;
        }
    }
}
