package dep.gateway.socket;

import java.net.*;
import java.io.*;

public class StandardSocket {

  private int m_iPort = 9000;
  private int m_iHeadLen = 4;
  //private int m_iMaxSize = 5120;
  private InetAddress m_address;
  private Socket m_socket;
  private OutputStream m_out;
  private InputStream m_in;
  private int m_iTimeout;//million

  public StandardSocket() {
  }

  void setHeadLen(int iLen) {
    m_iHeadLen=iLen;
  }
  ///////////////////////////////////////////////////////////////////
  //发送数据块（内部调用）
  //sendData：待发送的数据
  public void sendData(String sendData) throws IOException{
    byte l_byte[]=sendData.getBytes("GBK");
    int l_iVLen=l_byte.length;

    m_out.write(longToString(l_iVLen, m_iHeadLen).getBytes(),0,m_iHeadLen);
    m_out.write(l_byte,0,l_iVLen);

    return;
  }
  /////////////////////////////////////////////////////
  //接收数据块（内部调用）
  //返回：接收的结果
  public String recvData() throws IOException {
    byte l_byteHead[]=new byte[m_iHeadLen+1];
    int l_iLeave=m_iHeadLen,l_iOffset=0,l_iRecv=0;
    String l_strRet=null;
    while(l_iLeave>0)
    {
      l_iRecv=m_in.read(l_byteHead,l_iOffset,l_iLeave);
      if(l_iRecv==-1)
      {
        throw new IOException();
      }
      l_iOffset+=l_iRecv;
      l_iLeave-=l_iRecv;
    }
    l_iLeave=Integer.parseInt(new String(l_byteHead,0,m_iHeadLen));
    byte l_byte[]=new byte[l_iLeave+1];
    int l_iLen=l_iLeave;
    l_iOffset=0;
    while(l_iLeave>0)
    {
      l_iRecv=m_in.read(l_byte,l_iOffset,l_iLeave);
      l_iOffset+=l_iRecv;
      l_iLeave-=l_iRecv;
    }
    l_strRet=new String(l_byte,0,l_iLen,"GBK");
    return l_strRet;
  }

  //联接Socket
  //iTime单位为秒
  public void connect(String strAddress, int iPort,int iTimeout) throws IOException {
    m_address = InetAddress.getByName(strAddress);
    m_iPort=iPort;
    m_iTimeout=iTimeout*1000;
    m_socket = new Socket(m_address, m_iPort);
    m_socket.setSoTimeout(m_iTimeout);
    m_out=m_socket.getOutputStream();
    m_in=m_socket.getInputStream();
  }
  //断开Socket
  public void disconnect() throws IOException {
    m_socket.close();
  }
  //将long类型转换为右对齐左补零的指定长度的String
  public static String longToString(long value, int len) {
    String strValue = "" + value;
    String returnValue;
    StringBuffer tempBuf;
    String tempValue;
    int maxLen = len;

    tempBuf = new StringBuffer("");
    if (value < 0) {
      //对于负数，负号要占用第一个位置
      maxLen--;
      tempBuf.append("-");
      tempValue = "" + (- value);
    }
    else {
      tempValue = "" + value;
    }

    if (maxLen >= tempValue.length()) {
      //若长度富余，补0
      int i;
      for (i=0;i<maxLen-tempValue.length();i++) {
        tempBuf.append("0");
      }
      tempBuf.append(tempValue);
    }
    else {
      //若长度不够，截取后面一段
      tempBuf.append(tempValue.substring(tempValue.length() - maxLen, tempValue.length()));
    }

    returnValue = tempBuf.toString();
    return returnValue;
  }

  //将字符串转换成指定长度的左对齐右补空形式的字符串
  public static String fillString(String value, int length) {
    if (value == null) return null;
    int l_iVLen;//length of length
    StringBuffer tempBuf = new StringBuffer("");
    byte l_byte[]=null;
    try{
      l_byte=value.getBytes("GBK");
    }catch(Exception exc)
    {
      exc.printStackTrace();
    }
    l_iVLen=l_byte.length;
    if (length >= l_iVLen){
      //若长度富余，右补空
      tempBuf.append(value);
      int i;
      for (i=0;i<length-l_iVLen;i++) {
        tempBuf.append(" ");
      }
    }
    else{
      //若长度不够，截取前length个字节
      tempBuf.append(value.substring(0,length/2));//modify by Capcom 2004-06-11 for external transact exception
      //tempBuf.append(value.substring(0, length));
    }

    return tempBuf.toString();
  }
  //byte to String
  public static String getByteString(byte [] pbyte,int iStart,int iLen) {
    if(iStart>pbyte.length||iLen>(pbyte.length-iStart))
    {
      return null;
    }
    String l_str=new String(pbyte,iStart,iLen);
//add by dongxuefeng on 2002-11-27 for 中文
    try{
    l_str = new String(l_str.getBytes("8859_1"),"GBK");
    }catch(Exception exc)
    {}
//end add
    return l_str;
  }
  //double to string
  public static String Double2String(double d) {
    long l_l=(long)(d);
    return Long.toString(l_l);
  }
}
