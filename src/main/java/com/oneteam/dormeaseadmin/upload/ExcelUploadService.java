package com.oneteam.dormeaseadmin.upload;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ExcelUploadService {

    @Autowired
    IExcelUploadMapper excelUploadMapper;

    public int insertExcelData(List<ExcelDataDto> excelDataDtos) {
        log.info("insertExcelData()");

        return excelUploadMapper.insertExcelData(excelDataDtos);
    }
}
