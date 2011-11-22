package monitor.warn.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("simpleService")
public class SimpleService implements Serializable{

    private static final long serialVersionUID = 122323233244334343L;
    private static final Logger logger = LoggerFactory.getLogger(SimpleService.class);
    private static int times = 0;

    public void testMethod(String str){
        //����ִ�ж�ʱ����ҵ��
        System.out.println(str);
        System.out.println(" ==================  ִ�е�������testMethod����, ����:" + times++ );
    }
}
