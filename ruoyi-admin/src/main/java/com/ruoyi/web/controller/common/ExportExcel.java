package com.ruoyi.web.controller.common;

import com.ruoyi.system.domain.experimental.ExpStep;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.List;

/**
 * 实验数据横向导出Excel
 */
public class ExportExcel {

    private static final Logger log = LoggerFactory.getLogger(ExportExcel.class);

    /**
     * 导出实验步骤数据（横向布局）
     *
     * @param response HTTP响应
     * @param list     按实验分组的步骤列表
     */
    public void exportData(HttpServletResponse response, List<List<ExpStep>> list) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=experiment.xlsx");

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        try {
            Sheet sheet = workbook.createSheet("数据表");

            // 通用文本样式
            CellStyle textStyle = workbook.createCellStyle();
            Font textFont = workbook.createFont();
            textFont.setFontHeightInPoints((short) 15);
            textStyle.setFont(textFont);
            textStyle.setBorderTop(BorderStyle.NONE);
            textStyle.setBorderBottom(BorderStyle.NONE);
            textStyle.setBorderLeft(BorderStyle.NONE);
            textStyle.setBorderRight(BorderStyle.NONE);
            textStyle.setAlignment(HorizontalAlignment.LEFT);
            textStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);

            // 步骤名称加粗样式
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 20);
            headerStyle.setFont(headerFont);
            headerStyle.setBorderTop(BorderStyle.NONE);
            headerStyle.setBorderBottom(BorderStyle.NONE);
            headerStyle.setBorderLeft(BorderStyle.NONE);
            headerStyle.setBorderRight(BorderStyle.NONE);
            headerStyle.setAlignment(HorizontalAlignment.LEFT);
            headerStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);

            // 空行下边框样式
            CellStyle blankStyle = workbook.createCellStyle();
            blankStyle.setBorderBottom(BorderStyle.THICK);
            blankStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

            // 计算最大步骤数，决定表格宽度
            int maxStepCount = 0;
            for (List<ExpStep> steps : list) {
                if (steps != null && steps.size() > maxStepCount) {
                    maxStepCount = steps.size();
                }
            }

            // 每行总列数 = (最大步骤数 + 1) * 3，第0列放实验名称
            int totalColumns = (maxStepCount + 1) * 3;

            int currentRow = 0;
            for (List<ExpStep> steps : list) {
                if (steps == null || steps.isEmpty()) {
                    continue;
                }

                // 4行一组：要素行、步骤名称行、步骤结果行、空行
                Row eleRow = sheet.createRow(currentRow++);
                Row stepRow = sheet.createRow(currentRow++);
                Row resultRow = sheet.createRow(currentRow++);
                Row blankRow = sheet.createRow(currentRow++);
                blankRow.setHeightInPoints(8.0f);

                // 空行铺满下边框
                for (int col = 0; col < totalColumns; col++) {
                    Cell blankCell = blankRow.createCell(col);
                    blankCell.setCellValue("");
                    blankCell.setCellStyle(blankStyle);
                }

                // 第0列写入实验名称（取第一个步骤的创建人+实验名）
                ExpStep firstStep = steps.get(0);
                String expLabel = firstStep.getCreateStaffName() + "/" + firstStep.getExpName();
                Cell expCell = stepRow.createCell(0);
                expCell.setCellValue(expLabel);
                expCell.setCellStyle(textStyle);

                // 每个步骤占3列，内容写在第 (j+1)*3 列
                for (int j = 0; j < steps.size(); j++) {
                    ExpStep step = steps.get(j);
                    int colNum = (j + 1) * 3;

                    Cell eleCell = eleRow.createCell(colNum);
                    eleCell.setCellValue(step.getStepBriefEle());
                    eleCell.setCellStyle(textStyle);

                    Cell stepCell = stepRow.createCell(colNum);
                    stepCell.setCellValue(step.getStepName());
                    stepCell.setCellStyle(headerStyle);

                    Cell resultCell = resultRow.createCell(colNum);
                    resultCell.setCellValue(step.getStepResult());
                    resultCell.setCellStyle(textStyle);
                }
            }

            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("导出Excel异常：{}", e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly((Closeable) workbook);
        }
    }
}
