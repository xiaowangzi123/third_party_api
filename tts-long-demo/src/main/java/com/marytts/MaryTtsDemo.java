package com.marytts;

import marytts.LocalMaryInterface;
import marytts.util.data.audio.MaryAudioUtils;

import javax.sound.sampled.AudioInputStream;
import java.util.Properties;

/**
 * @author wyq
 * @date 2025/1/16
 * @desc
 */
public class MaryTtsDemo {

    public static void main(String[] args) {
        try {
            String outputFileName = "C:\\test\\en-"+StringTools.dateStr()+ ".wav ";
            LocalMaryInterface marytts = new LocalMaryInterface();

            Properties properties = new Properties();

//            marytts.setVoice("cmu-slt-hsmm");
            marytts.setVoice("cmu-bdl-hsmm");

            AudioInputStream audio = marytts.generateAudio("hello, this is a test.");

            double[] samples = MaryAudioUtils.getSamplesAsDoubleArray(audio);

            MaryAudioUtils.writeWavFile(samples, outputFileName, audio.getFormat());
            System.out.println("Output written to " + outputFileName);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
