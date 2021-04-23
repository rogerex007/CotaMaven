/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cotamaven.controllers;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.JOptionPane;
import services.Polly;
import utils.SuccessMessages;

/**
 *
 * @author roger
 */
public class FileConverter extends Thread {

    private List<LinkedHashMap<String, String>> textList;
    private Polly polly;

    public FileConverter(List<LinkedHashMap<String, String>> textList) {
        this.textList = textList;
        
    }

    @Override
    public void run() {
        if (textList.size() > 0) {
            textList.forEach(map -> {
                for (String key : map.keySet()) {
                    //System.out.println("Name: " + key);
                    //System.out.println("Text: " + map.get(key));
                    polly = new Polly(Region.getRegion(Regions.US_EAST_2),
                            "speechs/" + key + ".mp3", map.get(key));
                    polly.start();
                }
            });
        } else {
            System.out.println("Empy");
        }
        double time = System.nanoTime();
        JOptionPane.showMessageDialog(null, SuccessMessages.Covertion_Completed
                + "\n" + "Time: " + time);
    }


}
