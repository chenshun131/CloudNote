package com.chenshun.test.concurrency.forkandjoin.merge;

import java.util.Random;

/**
 * User: mew <p />
 * Time: 17/9/11 17:00  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Document {

    private String words[] = {"the", "hello", "goodbye", "packt", "java", "thread", "pool",
            "random", "class", "main"};

    public String[][] generateDocument(int numLines, int numWords, String word) {
        int counter = 0;
        String document[][] = new String[numLines][numWords];
        Random random = new Random();
        for (int i = 0; i < numLines; i++) {
            for (int j = 0; j < numWords; j++) {
                int index = random.nextInt(words.length);
                document[i][j] = words[index];
                if (document[i][j].equals(word)) {
                    counter++;
                }
            }
        }
        System.out.println("DocumentMock: The word appears " + counter + " times in the document");
        return document;
    }

}
