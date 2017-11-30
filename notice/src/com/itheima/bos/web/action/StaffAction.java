package com.itheima.bos.web.action;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.web.action.base.BaseAction;

import javax.servlet.ServletOutputStream;

/**
 * 取派员管理
 *
 * @author zhaoqx
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {

    //接收上传的文件
    private File myFile;

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    /**
     * 添加取派员
     */
    public String add() {
        staffService.save(model);
        return "list";
    }

    /**
     * 分页查询方法
     *
     * @throws IOException
     */
    public String pageQuery() throws IOException {


        String flag = ServletActionContext.getRequest().getParameter("export");

        // 在查询之前，封装条件
        DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
        String company = model.getCompany();
        if (company!=null && !"".equals(company)) {
            company = java.net.URLDecoder.decode(company, "utf-8");
        }
        String name = model.getName();
        if (name!=null && !"".equals(name)) {
            name = java.net.URLDecoder.decode(name, "utf-8");
        }

        String sid = model.getSid();
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        String isOutage = model.getIsOutage();
        String isNoPm = model.getIsNoPm();
        String isFire = model.getIsFire();
        String isContine = model.getIsContine();
        String needNotice = model.getNeedNotice();

        if (StringUtils.isNotBlank(company)) {
            // 按照地址关键字模糊查询
            detachedCriteria2.add(Restrictions.like("company", "%" + company + "%"));

        }
        if (StringUtils.isNotBlank(name)) {
            // 按照地址关键字模糊查询
            detachedCriteria2.add(Restrictions.like("name", "%" + name + "%"));
        }

        if (StringUtils.isNotBlank(sid)) {
            // 按照省进行模糊查询
            detachedCriteria2.add(Restrictions.eq("sid", sid));
        }
        if (StringUtils.isNotBlank(beginTime)) {

            beginTime = beginTime.replaceAll("\\-", ".");
            // 按照省进行模糊查询
            detachedCriteria2.add(Restrictions.ge("beginTime", beginTime));
        }

        if (StringUtils.isNotBlank(endTime)) {

            endTime = endTime.replaceAll("\\-", ".");
            // 按照省进行模糊查询
            detachedCriteria2.add(Restrictions.le("endTime", endTime));
        }

        if (StringUtils.isNotBlank(isOutage)) {
            // 按照省进行模糊查询
            detachedCriteria2.add(Restrictions.eq("isOutage", isOutage));
        }
        if (StringUtils.isNotBlank(isNoPm)) {
            // 按照省进行模糊查询
            detachedCriteria2.add(Restrictions.eq("isNoPm", isNoPm));
        }
        if (StringUtils.isNotBlank(isFire)) {
            // 按照省进行模糊查询
            detachedCriteria2.add(Restrictions.eq("isFire", isFire));
        }
        if (StringUtils.isNotBlank(isContine)) {
            // 按照省进行模糊查询
            detachedCriteria2.add(Restrictions.eq("isContine", isContine));
        }

        if (StringUtils.isNotBlank(needNotice)) {
            // 按照省进行模糊查询
            detachedCriteria2.add(Restrictions.eq("needNotice", needNotice));
        }


        if ("1".equals(flag)) {

            pageBean.setCurrentPage(1);
            pageBean.setPageSize(9999999);
        }

        staffService.pageQuery(pageBean);
        //将PageBean对象转为json返回
        List<Staff> rows = pageBean.getRows();


        for (Staff staff : rows) {
            Double salary = staff.getSalary() == null ? 0 : staff.getSalary();
            Double dateSalary = staff.getDateSalary() == null ? 0 : staff.getDateSalary();
            Double publicMoney = staff.getPublicMoney() == null ? 0 : staff.getPublicMoney();
            double pay = salary + dateSalary - publicMoney;

            if ("0".equals(staff.getIsNoPm())) {
                pay = pay - staff.getPersionalMoney();
            }
            staff.setPay(pay);

            staff.setTerm(staff.getBeginTime() + "-" + staff.getEndTime());
        }


        if ("1".equals(flag)) {
            exportXls(rows);
            return NONE;
        }


        this.writePageBean2Json(pageBean, new String[]{"currentPage", "detachedCriteria", "pageSize"});
        return NONE;
    }

    //接收ids参数
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * 批量删除功能
     *
     * @return
     */
    public String delete() {
        staffService.deleteBatch(ids);
        return "list";
    }

    /**
     * 修改取派员信息
     */
    public String edit() {
        //显查询数据库中原始数据
        Staff staff = staffService.findById(model.getId());

        //再按照页面提交的参数进行覆盖

        staff.setCompany(model.getCompany());
        staff.setAgreement(model.getAgreement());
        staff.setName(model.getName());
        staff.setSex(model.getSex());
        staff.setSid(model.getSid());
        staff.setAge(model.getAge());
        staff.setJob(model.getJob());
        staff.setSalary(model.getSalary());
        staff.setDateSalary(model.getDateSalary());
        staff.setPersionalMoney(model.getPersionalMoney());
        staff.setPublicMoney(model.getPublicMoney());
        staff.setOther(model.getOther());
        staff.setPay(model.getPay());
        staff.setCardNumber(model.getCardNumber());
        staff.setBank(model.getBank());
        staff.setBeginTime(model.getBeginTime());
        staff.setEndTime(model.getEndTime());
        staff.setPhone(model.getPhone());
        staff.setRegister(model.getRegister());
        staff.setCard(model.getCard());
        staff.setRemark(model.getRemark());
        staff.setIsOutage(model.getIsOutage());
        staff.setIsFire(model.getIsFire());
        staff.setIsNoPm(model.getIsNoPm());
        staff.setIsContine(model.getIsContine());
        staff.setNeedNotice(model.getNeedNotice());
        staff.setComeTime(model.getComeTime());
        staff.setGoTime(model.getGoTime());


        staffService.update(staff);
        return "list";
    }

    public String importXls() throws Exception {
        String flag = "1";
        //使用ＰＯＩ解析Ｅｘｃｅｌ文件
        try {
            FileInputStream in = new FileInputStream(myFile);
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            //获得第一个sheet页

            int num = workbook.getNumberOfSheets();
            List<Staff> list = new ArrayList<Staff>();

            for (int i = 0; i < num; i++) {

            HSSFSheet sheet = workbook.getSheetAt(i);

                String agreement = "";
                String company = "";
                for (Row row : sheet) {
                int rowNum = row.getRowNum();
                if (rowNum == 0 || rowNum == 1) {
                    continue;
                }

                if (rowNum == 2) {
                    agreement = getValue(row.getCell(2));
                    continue;

                }

                if (rowNum == 3) {
                    company = getValue(row.getCell(2));
                    if (company ==null) {
                        break;
                    }
                    continue;

                }

                if (rowNum == 4 || rowNum == 5) {
                    continue;
                }


                Staff staff = new Staff();
                staff.setAgreement(agreement);
                staff.setCompany(company);
                String name = getValue(row.getCell(1));
                if (name == null) {
                    break;
                }
                staff.setName(name);

                String sex = getValue(row.getCell(2));
                staff.setSex(sex);
                String sid = getValue(row.getCell(3));
                if (sid.length() != 18) {
                    throw new RuntimeException(company + " " + name + " 身份证位数错误");
                }
                staff.setSid(sid);
                int year = new Integer(sid.substring(6, 10));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                int now = new Integer(sdf.format(new Date()));

                staff.setAge(now - year);

                String job = getValue(row.getCell(5));
                staff.setJob(job);

                String value2 = getValue(row.getCell(6));
                if (value2==null) {
                    staff.setSalary(null);
                } else {
                    Double salary = null;
                    try {
                        salary = Double.parseDouble(value2);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    staff.setSalary(salary);
                }

                    String value3 = null;
                    try {
                        value3 = getValue(row.getCell(7));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (value3 == null) {
                    staff.setDateSalary(null);
                } else {

                    Double dateSalary = Double.parseDouble(value3);
                    staff.setDateSalary(dateSalary);
                }

                String value4 = getValue(row.getCell(8));
                if (value4 == null ){
                    staff.setPersionalMoney(null);
                } else {
                    Double persionalMon = Double.parseDouble(value4);
                    staff.setPersionalMoney(persionalMon);
                }
                Cell cell = row.getCell(9);
                String value = getValue(row.getCell(9));
                if (value ==null) {
                    staff.setPublicMoney(null);
                } else {

                    Double publicMoney = Double.parseDouble(value);
                    staff.setPublicMoney(publicMoney);
                }

                Cell othercell = row.getCell(10);
                String value1 = getValue(row.getCell(10));

                if (value1 == null) {
                    staff.setOther(null);
                } else {
                    Double other = Double.parseDouble(value1);
                    staff.setOther(other);
                }

                String cardNumber = getValue(row.getCell(12));
                staff.setCardNumber(cardNumber);

                String bank = getValue(row.getCell(13));
                staff.setBank(bank);

                String time = getValue(row.getCell(14));

                    try {
                        String[] split = time.split("\\-");
                        String beginTime = convertDate(split[0]);
                        String endTime = convertDate(split[1]);
                        staff.setBeginTime(beginTime);
                        staff.setEndTime(endTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                        staff.setBeginTime("");
                        staff.setEndTime("");
                    }

                String phone = getValue(row.getCell(15));
                staff.setPhone(phone);

                String register = getValue(row.getCell(16));
                staff.setRegister(register);

                //医保卡
                    String card = null;
                    try {
                        card = getValue(row.getCell(17));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    staff.setCard(card);


                String remark = getValue(row.getCell(18));
                staff.setRemark(remark);

                    String comeTime = null;
                    try {
                        comeTime = getValue(row.getCell(19));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    staff.setComeTime(comeTime);

                String goTime = getValue(row.getCell(20));
                staff.setGoTime(goTime);
                //13黄  11绿 15蓝 46紫
                int color = row.getCell(1).getCellStyle().getFillForegroundColor();

                if (color ==13) {
                    staff.setIsOutage("1");
                }

                if (color ==11) {
                    staff.setIsFire("1");
                }



                if (color ==15) {
                    staff.setIsNoPm("1");
                }

                if (color ==46) {
                    staff.setIsContine("1");
                }



                List<Staff> staffList = staffService.findByName(staff.getName());
                if (staffList != null && staffList.size() > 0) {
                    for (Staff oldStaff : staffList) {

                        if (staff.getEndTime().equals(oldStaff.getEndTime())) {
                            throw new RuntimeException(staff.getName() + " 相同数据已存在");
                        } else {
                            staff.setIsContine("1");
                        }
                    }

                }
                list.add(staff);
            }
            }

            staffService.saveBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
            flag = e.getMessage();
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }

    private DecimalFormat df = new DecimalFormat("0");

    private String getValue(Cell cell) throws Exception {

        int cellType = cell.getCellType();


        if (cellType == cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cellType == cell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            if (cell.getNumericCellValue() > 999999) {
                return df.format(cell.getNumericCellValue());
            } else {
                return String.valueOf(cell.getNumericCellValue());
            }
        } else if (cellType == cell.CELL_TYPE_BLANK) {
            // 返回数值类型的值
            return null;
        } else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue());
        }
    }

    private String convertDate(String str) {

        String[] split = str.split(".");
        if (split.length != 3) {
            return str;
        }
        String m = split[1];
        String d = split[2];
        if (m.length() == 1) {
            m = "0" + split[1];
        }
        if (d.length() == 1) {
            d = "0" + split[1];
        }

        return split[0] + "." + m + "." + d;
    }


    /**
     * 使用POI写入Excel文件，提供下载
     *
     * @throws IOException
     */
    private void exportXls(List<Staff> list) throws IOException {

        // 在内存中创建一个Excel文件，通过输出流写到客户端提供下载
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个sheet页
        HSSFSheet sheet = workbook.createSheet("sheet1");
        // 创建标题行
        HSSFRow headRow = sheet.createRow(0);
        HSSFRow headRow1 = sheet.createRow(1);
        HSSFRow headRow2 = sheet.createRow(2);
        Staff staff1 = list.get(0);
        headRow.createCell(0).setCellValue("主协议");
        headRow.createCell(1).setCellValue(staff1.getAgreement());

        headRow1.createCell(0).setCellValue("公司");
        headRow1.createCell(1).setCellValue(staff1.getCompany());

        headRow2.createCell(0).setCellValue("序号");
        headRow2.createCell(1).setCellValue("姓名");
        headRow2.createCell(2).setCellValue("性别");
        headRow2.createCell(3).setCellValue("身份证号码");
        headRow2.createCell(4).setCellValue("年龄");
        headRow2.createCell(5).setCellValue("职务");
        headRow2.createCell(6).setCellValue("工资");
        headRow2.createCell(7).setCellValue("其他工资");
        headRow2.createCell(8).setCellValue("五险");
        headRow2.createCell(9).setCellValue("公积金");
        headRow2.createCell(10).setCellValue("其他");
        headRow2.createCell(11).setCellValue("实发工资");
        headRow2.createCell(12).setCellValue("卡号");
        headRow2.createCell(13).setCellValue("开户银行");
        headRow2.createCell(14).setCellValue("合同期限");
        headRow2.createCell(15).setCellValue("联系方式");
        headRow2.createCell(16).setCellValue("户口");
        headRow2.createCell(17).setCellValue("医保卡号");
        headRow2.createCell(18).setCellValue("备注");
        headRow2.createCell(19).setCellValue("参加工作时间");
        headRow2.createCell(20).setCellValue("离职时间");

        int i = 1;

        HSSFCellStyle style_outofage = workbook.createCellStyle();
        style_outofage.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style_outofage.setFillForegroundColor((short) 13);

        HSSFCellStyle style_fire = workbook.createCellStyle();
        style_fire.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style_fire.setFillForegroundColor((short) 11);

        HSSFCellStyle style_nopon = workbook.createCellStyle();
        style_nopon.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style_nopon.setFillForegroundColor((short) 15);

        HSSFCellStyle style_contuine = workbook.createCellStyle();
        style_contuine.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style_contuine.setFillForegroundColor((short) 46);



        for (Staff staff : list) {


            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);


            dataRow.createCell(0).setCellValue(String.valueOf(i));
            dataRow.createCell(1).setCellValue(staff.getName());
            dataRow.createCell(2).setCellValue(staff.getSex());
            dataRow.createCell(3).setCellValue(staff.getSid());
            dataRow.createCell(4).setCellValue(staff.getAge());
            dataRow.createCell(5).setCellValue(staff.getJob());
            if ((staff.getSalary()==null)) {
                dataRow.createCell(6).setCellValue("");
            } else {
                dataRow.createCell(6).setCellValue(staff.getSalary());
            }

            if (staff.getDateSalary() == null) {
                dataRow.createCell(7).setCellValue("");
            } else {
                dataRow.createCell(7).setCellValue(staff.getDateSalary());
            }

            if (staff.getPersionalMoney() == null){
                dataRow.createCell(8).setCellValue("");
            } else {
                dataRow.createCell(8).setCellValue(staff.getPersionalMoney());
            }

            if (staff.getPublicMoney() == null) {
                dataRow.createCell(9).setCellValue("");
            } else {

                dataRow.createCell(9).setCellValue(staff.getPublicMoney());
            }

            if (staff.getOther() == null) {
                dataRow.createCell(10).setCellValue("");
            } else {

                dataRow.createCell(10).setCellValue(staff.getOther());
            }
            dataRow.createCell(11).setCellValue(staff.getPay());
            dataRow.createCell(12).setCellValue(staff.getCardNumber());
            dataRow.createCell(13).setCellValue(staff.getBank());
            dataRow.createCell(14).setCellValue(staff.getTerm());
            dataRow.createCell(15).setCellValue(staff.getPhone());
            dataRow.createCell(16).setCellValue(staff.getRegister());
            dataRow.createCell(17).setCellValue(staff.getCard());
            dataRow.createCell(18).setCellValue(staff.getRemark());
            dataRow.createCell(19).setCellValue(staff.getComeTime());
            dataRow.createCell(20).setCellValue(staff.getGoTime());

            if ("1".equals(staff.getIsOutage())){
                HSSFCell cell = dataRow.createCell(21);
                cell.setCellStyle(style_outofage);
                cell.setCellValue("超龄");
            }

            if ("1".equals(staff.getIsNoPm())){
                HSSFCell cell = dataRow.createCell(22);
                cell.setCellStyle(style_nopon);
                cell.setCellValue("单位交五险");
            }

            if ("1".equals(staff.getIsContine())){
                HSSFCell cell = dataRow.createCell(23);
                cell.setCellStyle(style_contuine);
                cell.setCellValue("续签合同");
            }
            if ("1".equals(staff.getIsFire())){
                HSSFCell cell = dataRow.createCell(24);
                cell.setCellStyle(style_fire);
                cell.setCellValue("解雇");
            }

            i++;
        }

        String filename = staff1.getCompany() + ".xls";
        String agent = ServletActionContext.getRequest().getHeader("User-Agent");
        filename = FileUtils.encodeDownloadFilename(filename, agent);
        //一个流两个头
        ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
        String contentType = ServletActionContext.getServletContext().getMimeType(filename);
        ServletActionContext.getResponse().setContentType(contentType);
        ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename="+filename);
        workbook.write(out);
        out = null;


    }
}
