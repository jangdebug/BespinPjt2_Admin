package com.oneteam.dormeaseadmin.upload;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/upload")
public class ExcelUploadController {

    @Autowired
    ExcelUploadService excelUploadService;

    @PostMapping("/excel")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("uploadExcelFile()");

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);           // 첫 번째 시트 가져오기

        List<ExcelDataDto> excelDataDtos = new ArrayList<>();

        for (Row row : sheet) {
            //if (row.getRowNum() == 0) continue;       //만약 헤더가 있다면 활성화

            String name = row.getCell(0).getStringCellValue();
            String zip_code = row.getCell(1).getStringCellValue();
            String address = row.getCell(2).getStringCellValue();

            ExcelDataDto excelDataDto = new ExcelDataDto();
            excelDataDto.setName(name);
            excelDataDto.setZip_code(zip_code.trim());      //데이터에 공백이 있음을 확인
            excelDataDto.setAddress(address);

            excelDataDtos.add(excelDataDto);
        }

        int result = excelUploadService.insertExcelData(excelDataDtos);

        System.out.println("result => " + result);

        return "redirect:/";
    }
}