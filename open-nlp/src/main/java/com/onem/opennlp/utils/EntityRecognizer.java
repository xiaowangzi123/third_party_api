
package com.onem.opennlp.utils;


/**
 * @author wyq
 * @date 2023/4/24
 * @desc
 */


import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

public class EntityRecognizer {

    public static void main(String[] args) {
        String content = "Beijing [China], July 10 (ANI): China has stepped up its incursions activities into Taiwan's air defense identification zone (ADIZ) by sending its combat planes to the region.\n" +
                "\n" +
                "According to Taiwan's defense ministry, the People's Liberation Army conducted 555 sorties in the first six months of the year, of which 398 involved combat aircraft, compared with 187 in the same period last year.\n" +
                "\n" +
                "Defense analysts say the mainland's forces are developing their ability to control the skies over a longer range as part of preparations for a possible conflict.\n" +
                "\n" +
                "China sent 29 warplanes into Taiwan's ADIZ in late June this year, according to media reports, marking the third-largest fly-by in the country this year.\n" +
                "\n" +
                "The warplanes including 17 fighter jets, six bombers and other supporting aircraft, entered the island's air defense identification zone, The Star newspaper reported citing the island's defense ministry.\n" +
                "\n" +
                "While the fly-bys were generally seen as one of Beijing's tactics to intimidate the island, the increased use of combat planes was worth noting, observers said.\n" +
                "\n" +
                "But in recent months, the PLA had stepped up training exercises with its combat aircraft in the airspace near Taiwan and further south to the Bashi Channel, an important gateway to the western Pacific, Wang said.\n" +
                "\n" +
                "Beijing has been sending patrols into Taiwan's ADIZ on almost a daily basis since late 2020 to ramp up pressure on the island.\n" +
                "\n" +
                "China had breached Taiwan's air defense identification zone in January also when 35 of its military aircraft, including J-16s and one H-6 bomber joined four other support planes entered its ADIZ.\n" +
                "\n" +
                "At the end of May, 22 fighter jets joined eight other support planes in breaching Taiwan's ADIZ area.\n" +
                "\n" +
                "The issue of Taiwan has been at the forefront of US-China relations in recent months.\n" +
                "\n" +
                "Tensions between Washington, which is committed to supporting the island's self-defense, and Beijing over Taiwan were in the open earlier this month when their respective defense chiefs met at the Shangri-La Dialogue defense conference in Singapore.\n" +
                "\n" +
                "Taiwan and mainland China have been governed separately since the defeated Nationalists retreated to the island at the end of the Chinese civil war more than 70 years ago.\n" +
                "\n" +
                "But China's ruling Chinese Communist Party (CCP) views the self-ruled island as part of its territory -- despite having never controlled it.\n" +
                "\n" +
                "Beijing has not ruled out military force to take Taiwan and has kept the pressure on the democratic island over the past few years with frequent warplane flights into the island's ADIZ.\n" +
                "\n" +
                "An ADIZ is unilaterally imposed and distinct from sovereign airspace, which is defined under international law as extending 12 nautical miles from a territory's shoreline. (ANI)";
        String[] split = content.split(" +");
        EntityRecognizer obj = new EntityRecognizer();
        System.out.println(Arrays.toString(obj.findEntities(split)));
    }

    public String[] findEntities(String[] tokens){
        try {
//            InputStream modelIn = getClass().getResourceAsStream("/en-ner-person.bin");
            InputStream modelIn = Files.newInputStream(new File("D:\\github\\third_party_api\\open-nlp\\src\\main\\resources\\static\\en-ner-person.bin").toPath());
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);
            Span[] spans = nameFinder.find(tokens);
            String[] entities = Span.spansToStrings(spans, tokens);
            return entities;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}

