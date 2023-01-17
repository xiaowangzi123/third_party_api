package com.example.evaluate.utils;

public class GTM {

    public static Double eval(String candidate, String reference) {

        String[] splitC;
        String[] splitR1;

        String tempC;

        int p1 = 0;
        int countspaceR = 0, countspaceC = 0;


        double GTM;
        double precision;
        double recall;
        double avgLengthOfReferences, noOfwordsIncandi;
        //int flagR3,flagR2;
        //int flagp2R3,flagp2R2;
        //int flagp3R3 ,flagp3R2;
        //int ifBreak = 0;

        // splitting the candidate and reference sentences into individual words
        String temp = candidate.replaceAll("[\\n]", " ");
        splitC = temp.replaceAll("[.,!?:;'।]", "").replaceAll("-", " ").split(" ");

        for (int vx = 0; vx < splitC.length; vx++) {
            if ("".equals(splitC[vx])) {
                countspaceC++;
            }
        }
        int lenCand = (splitC.length - countspaceC);

        temp = reference.replaceAll("\\n]", " ");
        splitR1 = temp.replaceAll("[.,!?:;'।]", "").replaceAll("-", " ").split(" ");

        for (int zx = 0; zx < splitR1.length; zx++) {
            if ("".equals(splitR1[zx])) {
                countspaceR++;
            }
        }
        int lenRef = (splitR1.length - countspaceR);

        //calculating c for the current candidate sentence
        noOfwordsIncandi = lenCand;
        avgLengthOfReferences = lenRef;

        //calculating p1

        //loop that traverses through each word of the candidate
        //one by one that is 1 gram precision
        for (int i = 0; i < lenCand; i++) {

            //ith word stored in tempC
            tempC = splitC[i];

            //loop traverses R1 word by word
            for (int j = 0; j < lenRef; j++) {

                //indication used later to
                //see whether the word needs to be checked in R2
                //flagR2=0;

                if (tempC.equalsIgnoreCase(splitR1[j])) {
                    p1++; //word found in R1
                    splitR1[j] = "";//empty the word in R1 to avoid matching it again
                    //flagR2=1;//no need to check in R2 for this word
                    break;//comes out of the j loop
                }

            } //for for R1 ends
        }

        precision = p1 / (double) lenCand;
        recall = p1 / (double) lenRef;

        GTM = (2 * precision * recall) / (precision + recall);

        return GTM;
    }
}
        

    
