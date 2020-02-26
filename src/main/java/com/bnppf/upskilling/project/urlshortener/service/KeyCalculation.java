package com.bnppf.upskilling.project.urlshortener.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KeyCalculation {

        public static String URLKeyCalculation() {

            Random charKeyPosition = new Random();
            List base62CharList = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z');

            /**
             * while generated key exist in URLLink database
             * create a new URLLink
             */
            StringBuilder generatedKey = new StringBuilder();

            for (int i = 0; i < 8; i++){
                int charKeySelected = charKeyPosition.nextInt(62);
                generatedKey.append(base62CharList.get(charKeySelected));
            }

            return generatedKey.toString();
        }
 }

