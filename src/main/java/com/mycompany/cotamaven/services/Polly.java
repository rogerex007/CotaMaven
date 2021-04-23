/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyAsyncClient;
import com.amazonaws.services.polly.AmazonPollyClient;

import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;

import com.amazonaws.services.polly.model.VoiceId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *
 * @author roger
 */
public class Polly extends Thread {

    private final AmazonPollyClient polly;
    private String fileName;
    private String text;

    public Polly(Region region, String fileName, String text) {
        this.polly = new AmazonPollyAsyncClient(new DefaultAWSCredentialsProviderChain(),
                new ClientConfiguration());
        this.polly.setRegion(region);
        this.fileName = fileName;
        this.text = text;
    }

    @Override
    public void run() {
        SynthesizeSpeechRequest synthesizeSpeechRequest = new SynthesizeSpeechRequest()
                .withOutputFormat(OutputFormat.Mp3)
                .withVoiceId(VoiceId.Miguel)
                .withText(text);
        
        try (FileOutputStream outputStream = new FileOutputStream(new File(fileName))) {
            SynthesizeSpeechResult synthesizeSpeechResult = polly.synthesizeSpeech(synthesizeSpeechRequest);
            byte[] buffer = new byte[2 * 1024];
            int readBytes;

            try (InputStream in = synthesizeSpeechResult.getAudioStream()) {
                while ((readBytes = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readBytes);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception caught: " + e);
        }
    }

}
