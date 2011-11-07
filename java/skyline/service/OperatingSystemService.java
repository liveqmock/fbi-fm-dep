package skyline.service;

import com.sun.management.OperatingSystemMXBean;
import monitor.pojo.SystemElmtBean;
import sun.management.ManagementFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-11-7
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class OperatingSystemService {

    private static final int CPUTIME = 500;
    //private static final int PERCENT = 100;
    private static final int FAULTLENGTH = 10;

    public static List<SystemElmtBean> getAllSystemElmts() throws IOException, InterruptedException {

        List<SystemElmtBean> elmtBeanList = new ArrayList<SystemElmtBean>();
        elmtBeanList.add(getCPUInfosForWindows());
        elmtBeanList.add(getMemeryInfos());
        elmtBeanList.addAll(getDiskInfos());
        return elmtBeanList;
    }

    /**
     * 读取内存信息
     *
     * @return
     */
    public static SystemElmtBean getMemeryInfos() {

        SystemElmtBean diskElmt = new SystemElmtBean();
        diskElmt.setElmtName("物理内存");
        diskElmt.setPicName("memory.png");

        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存
        long totalPhysicalMemory = osmxb.getTotalPhysicalMemorySize();
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();

        diskElmt.setTotalSize(String.format("%.2fM", totalPhysicalMemory * 1.0 / 1024 / 1024));
        diskElmt.setFreeSize(String.format("%.2fM", freePhysicalMemorySize * 1.0 / 1024 / 1024));
        diskElmt.setUsedPart(1 - freePhysicalMemorySize * 1.0 / totalPhysicalMemory);

        return diskElmt;
    }

    /**
     * 磁盘信息
     *
     * @return
     */
    public static List<SystemElmtBean> getDiskInfos() {

        List<SystemElmtBean> elmtBeanList = new ArrayList<SystemElmtBean>();
        SystemElmtBean diskElmt = null;

        File[] roots = File.listRoots();//获取磁盘分区列表
        for (File file : roots) {
            diskElmt = new SystemElmtBean();
            diskElmt.setElmtName("磁盘：" + file.getPath());
            diskElmt.setPicName("disk.png");
            long totalSize = (long) file.getTotalSpace();
            long freeSize = (long) file.getFreeSpace();
            diskElmt.setTotalSize(String.format("%.2fG", totalSize * 1.0 / 1024 / 1024 / 1024));
            diskElmt.setFreeSize(String.format("%.2fG", freeSize * 1.0 / 1024 / 1024 / 1024));
            if (new Long(0).longValue() != totalSize) {
                diskElmt.setUsedPart(1 - freeSize * 1.0 / totalSize);
            }else {
                diskElmt.setUsedPart(0.00);
            }
            elmtBeanList.add(diskElmt);
        }

        return elmtBeanList;
    }

    //获得cpu使用率
    public static SystemElmtBean getCPUInfosForWindows() throws IOException, InterruptedException {
        SystemElmtBean diskElmt = new SystemElmtBean();
        diskElmt.setElmtName("处理器");
        diskElmt.setPicName("cpu.png");
        diskElmt.setTotalSize("100.0%");
        String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
        // 取进程信息
        long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
        Thread.sleep(CPUTIME);
        long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
        if (c0 != null && c1 != null) {
            long idletime = c1[0] - c0[0];
            long busytime = c1[1] - c0[1];
            diskElmt.setUsedPart(busytime * 1.0 / (busytime + idletime));
        } else {
            diskElmt.setUsedPart(0.0);
        }
        diskElmt.setFreeSize(String.format("%.2f", (1.0 - diskElmt.getUsedPart()) * 100) + "%");
        return diskElmt;

    }

    //读取cpu相关信息
    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = subString(line, capidx, cmdidx - 1).trim();
                String cmd = subString(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                String s1 = subString(line, kmtidx, rocidx - 1).trim();
                String s2 = subString(line, umtidx, wocidx - 1).trim();
                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    if (s1.length() > 0)
                        idletime += Long.valueOf(s1).longValue();
                    if (s2.length() > 0)
                        idletime += Long.valueOf(s2).longValue();
                    continue;
                }
                if (s1.length() > 0)
                    kneltime += Long.valueOf(s1).longValue();
                if (s2.length() > 0)
                    usertime += Long.valueOf(s2).longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {

        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
     *
     * @param src       要截取的字符串
     * @param start_idx 开始坐标（包括该坐标)
     * @param end_idx   截止坐标（包括该坐标）
     * @return
     */
    private static String subString(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        StringBuilder tgtBuilder = new StringBuilder();
        for (int i = start_idx; i <= end_idx; i++) {
            tgtBuilder.append((char) b[i]);
        }
        return tgtBuilder.toString();
    }
}
