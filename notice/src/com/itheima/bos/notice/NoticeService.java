package com.itheima.bos.notice;




import com.itheima.bos.domain.Config;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IConfigService;
import com.itheima.bos.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NoticeService {
    @Autowired
    private IStaffService staffService;

    @Autowired
    private IConfigService configService;


    public void doNotice() {



        Config config = configService.findById(1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int noticeDay = config.getNoticeDay();
        String email = config.getEmail();

        calendar.add(Calendar.DAY_OF_MONTH, noticeDay);  //提前一个月
        Date time = calendar.getTime();
        String endTime = sdf.format(time);
        staffService.updateByEndTime(endTime);
        List<Staff> staffs = staffService.findByNotice();
        if (staffs != null && staffs.size()>0) {
            sendEmail(staffs, email);
        }

    }

    private void sendEmail( List<Staff> staffs, String email) {

       StringBuffer message = new StringBuffer();

            for (Staff staff : staffs) {
                message.append("[");
                message.append(staff.getName());
                message.append("]");
            }
        message.append("合同即将到期，请处理！");

        EmailUtil emailUtil = new EmailUtil(email);
        try {
            emailUtil.sendEmail(message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
