package com.baizhi.zcy;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.zcy.dao.LogDao;
import com.baizhi.zcy.entity.Log;
import com.baizhi.zcy.entity.User;
import com.baizhi.zcy.service.UserService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiTests {
    @Resource
    LogDao logDao;
    @Resource
    UserService userService;

    @Test
    public void easyPoi() {
        List<User> users = userService.selectAll();
        //yingx_zcy/src/main/webapp/upload/photo/1590455210216-4.jpg
        for (User user : users) {
            user.setHeadImg("src/main/webapp/upload/photo/" + user.getHeadImg());
            System.out.println("user.getHeadImg() = " + user.getHeadImg());
        }
        ExportParams exportParams = new ExportParams("用户信息", "用户信息表");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);

        try {
            workbook.write(new FileOutputStream(new File("E://javase//zcy//后期项目//用户信息导入.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void exportTest() {
        List<Log> logs = logDao.queryAll();
        //id,adminName,times,description,status
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("学生信息表");
        Row row = sheet.createRow(0);
        String[] title = {"id", "名字", "创建时间", "描述", "状态"};
        for (int i = 0; i < title.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        //给日期设置格式
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd日");

        //创建样式对象
        CellStyle cellStyleDataFormat = workbook.createCellStyle();
        cellStyleDataFormat.setDataFormat(format);  //设置日期样式
        //处理数据行数据
        for (int i = 0; i < logs.size(); i++) {
            //遍历集合  有几条数据创建几行
            Row row1 = sheet.createRow(i + 1);
            //数据
            //创建单元格

            row1.createCell(0).setCellValue(logs.get(i).getId());  //name
            row1.createCell(1).setCellValue(logs.get(i).getAdminName());  //name

            Cell cell1 = row1.createCell(2);  //创建单元格
            cell1.setCellStyle(cellStyleDataFormat);  //设置日期样式
            cell1.setCellValue(logs.get(i).getTimes());   //给单元格赋值  birthday

            row1.createCell(3).setCellValue(logs.get(i).getDescription());  //name
            row1.createCell(4).setCellValue(logs.get(i).getStatus());  //name


        }
        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("E://javase//zcy//后期项目/day10/TestPoi.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
