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
      m_strErrMessage = "ENT-0021:����ǰ��ʧ��";
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
      m_strErrMessage = "ENT-0021:����ǰ��ʧ��";
      return false;
    }
    if (strSend == null) {
      m_iRet = SENDFAILED;
      m_strErrMessage = "ENT-0022:�������ݲ���Ϊ��";
      return false;
    }
    if (strSend.length() <= 0) {
      m_iRet = SENDFAILED;
      m_strErrMessage = "ENT-0023:�������ݳ��Ȳ���Ϊ��";
      return false;
    }
    //send master data
    try {
      m_ssocket.sendData(strSend);
    }
    catch (java.io.InterruptedIOException iioe) {
      m_iRet = SENDTIMEOUT; //���ͳ�ʱ
      m_strErrMessage = "ENT-0024:�������ݳ�ʱ";
      return false;
    }
    catch (Exception e) {
      m_iRet = SENDFAILED;
      m_strErrMessage = "ENT-0025:��������ʧ��";
      return false;
    }
    return true;
  }

  protected boolean recv() {
    if (!m_bIfConnect) {
      m_iRet = CONNECTFAILED;
      m_strErrMessage = "ENT-0021:����ǰ��ʧ��";
      return false;
    }

    try {
      m_strRecv = m_ssocket.recvData();
    }
    catch (java.io.InterruptedIOException iioe) {
      m_iRet = RECVTIMEOUT; //���ճ�ʱ
      m_strErrMessage = "ENT-0026:�������ݳ�ʱ";
      return false;
    }
    catch (Exception e) {
      m_iRet = RECVFAILED; //����ʧ��
      m_strErrMessage = "ENT-0027:��������ʧ��";
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
