package com.example.demo.rest;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PDFService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PDFRest {

    @Autowired
    private PDFService pDFService;

    @GetMapping("pdf")
    public String pdfReader() throws FileNotFoundException {
        File pdfFile = getFile("C:/pdf", ".pdf");

        String textFileName = getTextFileName(pdfFile);
        String destFileName = getDestTextFileName(pdfFile);
        try {
            pDFService.read(pdfFile, textFileName, destFileName);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return "success";
    }

    private File getFile(String path, String suffix) throws FileNotFoundException {
        File dir = new File(path);
        File[] tempList = dir.listFiles();
        for (File file : tempList) {
            String fileName = file.getName();
            if (file.isFile()) {
                String fileSuffix = fileName.substring(fileName.lastIndexOf('.'));
                if (suffix.equals(fileSuffix)) {
                    return file;
                }
            }

        }

        throw new FileNotFoundException("PDF File not exists");
    }

    private String getTextFileName(File sourceFile) {
        String sourceFileName = sourceFile.getAbsolutePath();
        String pathPreFix = sourceFileName.substring(0, sourceFileName.lastIndexOf('.') + 1);
        return pathPreFix + "txt";
    }

    private String getDestTextFileName(File sourceFile) {
        String parent = sourceFile.getParent();
        String sourceFileName = sourceFile.getName();
        return parent + File.separator + "result" + File.separator + sourceFileName;
    }

}
