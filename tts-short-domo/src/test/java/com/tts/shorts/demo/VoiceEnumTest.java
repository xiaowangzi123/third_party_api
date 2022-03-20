package com.tts.shorts.demo;

import com.tts.shorts.demo.enums.VoiceEnum;
import org.junit.jupiter.api.Test;

/**
 * @author :wyq
 * @date ：Created in 2022/3/20
 * @description :
 */


public class VoiceEnumTest {
    
    @Test
    public void test1(){
        System.out.println(VoiceEnum.getVCN(3));
        
        System.out.println(VoiceEnum.getDesc(3));
    }
}