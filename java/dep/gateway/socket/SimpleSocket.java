package dep.gateway.socket;

public class SimpleSocket {

  public static final int CONNECTFAILED = 1;
  public static final int SENDFAILED = 2;
  public static final int SENDTIMEOUT = 3;
  public static final int RECVFAILED = 4;
  public static final int RECVTIMEOUT = 5;

  protected int m_iRet = CONNECTFAILED;
  protected String m_strErrMessage;
  private StandardSocket m_ssocket = null;

  protected String m_strRecv = null;

  private String m_strIP;
  private int m_iPort;
  private int m_iTimeout;

  boolean m_bIfConnect = false;

  public SimpleSocket(String strIP, int iPort, int iTimeout) {
    m_strIP = strIP;
    m_iPort = iPort;
    m_iTimeout = iTimeout;
  }

  public boolean go(String strSend) {
    if (!conn(m_strIP, m_iPort, m_iTimeout)) {
      return false;
    }
    if (!send(strSend)) {
      disconn();
      return false;
    }
    System.out.println("send:"+strSend);
    if (!recv()) {
      disconn();
      return false;
    }
    System.out.println("recv:"+m_strRecv);
    disconn();
    return true;
  }

  protected boolean conn(String strIP, int iPort, int iTimeout) {
    try {
      m_ssocket = new StandardSocket();
      m_ssocket.connect(strIP, iPort, iTimeout);
      m_ssocket.setHeadLen(11);//modify by Capcom 2010.2.25 for trial
      m_bIfConnect = true;
    }
    catch (Exception e) {
      m_strErrMessage = "ENT-0021:连接前置失败";
      m_bIfConnect = false;
      m_iRet = CONNECTFAILED;
    }
    return m_bIfConnect;
  }

  protected void disconn() {
    try {
      m_ssocket.disconnect();
    }
    catch (Exception e) {
    }
    finally {
      m_bIfConnect = false;
    }
  }

  protected boolean send(String strSend) {
    if (!m_bIfConnect) {
      m_iRet = CONNECTFAILED;
      m_strErrMessage = "ENT-0021:连接前置失败";
      return false;
    }
    if (strSend == null) {
      m_iRet = SENDFAILED;
      m_strErrMessage = "ENT-0022:发送数据不能为空";
      return false;
    }
    if (strSend.length() <= 0) {
      m_iRet = SENDFAILED;
      m_strErrMessage = "ENT-0023:发送数据长度不能为零";
      return false;
    }
    //send master data
    try {
      m_ssocket.sendData(strSend);
    }
    catch (java.io.InterruptedIOException iioe) {
      m_iRet = SENDTIMEOUT; //发送超时
      m_strErrMessage = "ENT-0024:发送数据超时";
      return false;
    }
    catch (Exception e) {
      m_iRet = SENDFAILED;
      m_strErrMessage = "ENT-0025:发送数据失败";
      return false;
    }
    return true;
  }

  protected boolean recv() {
    if (!m_bIfConnect) {
      m_iRet = CONNECTFAILED;
      m_strErrMessage = "ENT-0021:连接前置失败";
      return false;
    }

    try {
      m_strRecv = m_ssocket.recvData();
    }
    catch (java.io.InterruptedIOException iioe) {
      m_iRet = RECVTIMEOUT; //接收超时
      m_strErrMessage = "ENT-0026:接收数据超时";
      return false;
    }
    catch (Exception e) {
      m_iRet = RECVFAILED; //接收失败
      m_strErrMessage = "ENT-0027:接收数据失败";
      return false;
    }
    return true;
  }

  public int getRet() {
    return m_iRet;
  }

  public String getErrMessage() {
    return m_strErrMessage;
  }

  public String getRecv() {
    return m_strRecv;
  }
}
