/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cotamaven.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author roger
 */
public class ExtractTextProcess {

    private List<LinkedHashMap<String, String>> textList;
    private List<String> files;

    public ExtractTextProcess(List<String> files) {
        this.textList = new ArrayList();
        this.files = files;
    }

    public synchronized void extract() {
        String text;
        LinkedHashMap<String, String> local = new LinkedHashMap<String, String>();
        for (String fileName : files) {
            File file = new File(fileName);

            if ("doc".equals(FilenameUtils.getExtension(fileName))) {
                try (InputStream doc = new FileInputStream(file)) {

                    WordExtractor docExtractor = new WordExtractor(doc);

                    text = docExtractor.getText();
                    local.put(FilenameUtils.getName(fileName).replace(".doc", "")
                            + "_" + UUID.randomUUID(), text);
                    textList.add(local);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ExtractTextProcess.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ExtractTextProcess.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            if ("docx".equals(FilenameUtils.getExtension(fileName))) {
                try (InputStream docx = new FileInputStream(file)) {

                    XWPFDocument fl = new XWPFDocument(OPCPackage.open(docx));
                    XWPFWordExtractor docx_ext = new XWPFWordExtractor(fl);
                    text = docx_ext.getText();
                    local.put(FilenameUtils.getName(fileName).replace(".docx", "")
                            + "_" + UUID.randomUUID(), text);
                    textList.add(local);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ExtractTextProcess.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException | InvalidFormatException ex) {
                    Logger.getLogger(ExtractTextProcess.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if ("txt".equals(FilenameUtils.getExtension(fileName))) {
                Scanner scanner;
                try {
                    scanner = new Scanner(file);
                    String container = "";
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        container = container + "" + line;
                    }
                    scanner.close();
                    text = container;
                    local.put(FilenameUtils.getName(fileName).replace(".txt", "")
                            + "_" + UUID.randomUUID(), text);
                    textList.add(local);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ExtractTextProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ("dat".equals(FilenameUtils.getExtension(fileName))) {
                FileReader fl = null;

                String container = "";
                String str;

                try {
                    fl = new FileReader(fileName);
                    try (BufferedReader bffr = new BufferedReader(fl)) {
                        while ((str = bffr.readLine()) != null) {
                            container = container + "" + str;
                            
                        }
                    }

                    text = container;
                    local.put(FilenameUtils.getName(fileName).replace(".dat", "")
                            + "_" + UUID.randomUUID(), text);
                    textList.add(local);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ExtractTextProcess.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ExtractTextProcess.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        System.out.println("TEXTO: " + textList);
    }

    public List<LinkedHashMap<String, String>> getTextList() {
        return textList;
    }

}
