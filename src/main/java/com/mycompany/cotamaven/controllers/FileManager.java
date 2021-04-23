/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cotamaven.controllers;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;
import utils.ErrorMessages;

/**
 *
 * @author roger
 */

public class FileManager implements Serializable {

    private List<String> files;
    private JFileChooser selectorFiles;
    private JFileChooser selectorFolder;

    public FileManager() {
        this.files = new ArrayList();
        this.selectorFiles = new JFileChooser();
        this.selectorFolder = new JFileChooser();

    }

    public void selectFiles() {
        System.out.println("Pressed");
        selectorFiles.setDialogTitle("Select files to convert...");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("File text",
                "txt", "dat", "doc", "docx");
        selectorFiles.setAcceptAllFileFilterUsed(false);
        selectorFiles.setFileFilter(filter);
        selectorFiles.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selectorFiles.setMultiSelectionEnabled(true);

        if (selectorFiles.showOpenDialog(selectorFiles) == JFileChooser.APPROVE_OPTION) {
            File[] currentFiles = selectorFiles.getSelectedFiles();
            for (File file : currentFiles) {
                try {
                    String fileName = file.getCanonicalPath();
                    if (fileName.contains(".dat") || fileName.contains(".txt")
                            || fileName.contains(".doc") || fileName.contains(".docx")) {
                        files.add(fileName);
                    } else {
                        System.out.println(ErrorMessages.Invalid_MIME_TYPE);
                        JOptionPane.showMessageDialog(selectorFiles,
                                ErrorMessages.Invalid_MIME_TYPE, "Illegal Action", 
                                JOptionPane.ERROR_MESSAGE);
                        selectFiles();

                    }
                } catch (IOException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void selectFolder() {
        selectorFolder.setDialogTitle("Select files folder to convert...");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File",
                "txt", "dat", "doc", "docx");
        selectorFolder.setFileFilter(filter);
        selectorFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selectorFolder.setAcceptAllFileFilterUsed(false);

        if (selectorFolder.showOpenDialog(selectorFolder) == JFileChooser.APPROVE_OPTION) {
            File folder = selectorFolder.getCurrentDirectory();
            System.out.println(folder);
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                try {
                    String fileName = file.getCanonicalPath();
                    if (fileName.contains("doc") || fileName.contains("docx")
                            || fileName.contains("txt") || fileName.contains("dat")) {
                        System.out.println(fileName);
                        files.add(fileName);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void listAudioFiles() {
        File folder = new File("speechs/");
        System.out.println("Folder: " + folder);
        File[] listOfFiles = folder.listFiles();
        System.out.println("Files: " + Arrays.toString(listOfFiles));
        for(File file : listOfFiles){
            try {
                String fileName = file.getCanonicalPath();
                if ("mp3".equals(FilenameUtils.getExtension(fileName))) {
                    System.out.println("Name: " + FilenameUtils.getName(fileName));
                    System.out.println("Path: " + fileName);
                }
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setFiles(String fileName) {
        files.add(fileName);
    }

    public List<String> getFiles() {
        return files;
    }
}
