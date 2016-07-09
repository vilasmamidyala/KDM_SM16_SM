package wordnet;

import rita.RiWordNet;

/**
 * Created by Mayanka on 28-Jun-16.
 */
public class WordNetMain {
    public static void main(String args[])
    {

        RiWordNet wordnet = new RiWordNet("C:\\WordNet-3.0");

       // Demo finding parts of speech
        String word = "win";
        System.out.println("\nFinding parts of speech for " + word + ".");
        String[] partsofspeech = wordnet.getPos(word);
        for (int i = 0; i < partsofspeech.length; i++) {
            System.out.println(partsofspeech[i]);
        }

        // Added by vikesh

        String word1 = "loss";
        System.out.println("\nFinding parts of speech for " + word1 + ".");
        String[] partsofspeech1 = wordnet.getPos(word);
        for (int i = 0; i < partsofspeech1.length; i++) {
            System.out.println(partsofspeech1[i]);
        }

        word = "loss";
        String pos = wordnet.getBestPos(word);
        System.out.println("\n\nDefinitions for " + word + ":");
        // Get an array of glosses for a word
        String[] glosses = wordnet.getAllGlosses(word, pos);
        // Display all definitions
        for (int i = 0; i < glosses.length; i++) {
            System.out.println(glosses[i]);
        }

        // Demo finding a list of related words (synonyms)
        word = "win";
        String[] poss = wordnet.getPos(word);
        for (int j = 0; j < poss.length; j++) {
            System.out.println("\n\nSynonyms for " + word + " (pos: " + poss[j] + ")");
            String[] synonyms = wordnet.getAllSynonyms(word,poss[j],10);
            for (int i = 0; i < synonyms.length; i++) {
                System.out.println(synonyms[i]);
            }
        }

        // Demo finding a list of related words
        // X is Hypernym of Y if every Y is of type X
        // Hyponym is the inverse
        word = "loss";
        pos = wordnet.getBestPos(word);
        System.out.println("\n\nHyponyms for " + word + ":");
        String[] hyponyms = wordnet.getAllHyponyms(word, pos);
        for (int i = 0; i < hyponyms.length; i++) {
            System.out.println(hyponyms[i]);
        }

        System.out.println("\n\nHypernyms for " + word + ":");
        String[] hypernyms = wordnet.getAllHypernyms(word, pos);
        for (int i = 0; i < hypernyms.length; i++) {
            System.out.println(hypernyms[i]);
        }


        String start = "politics";
        String end = "Donald";
        pos = wordnet.getBestPos(start);

        // Wordnet can find relationships between words
        System.out.println("\n\nRelationship between: " + start + " and " + end);
        float dist = wordnet.getDistance(start,end,pos);
        String[] parents = wordnet.getCommonParents(start, end, pos);
        System.out.println(start + " and " + end + " are related by a distance of: " + dist);

        //Added by vikesh

        String start1 = "politics";
        String end1 = "Hillary";
        pos = wordnet.getBestPos(start);

        // Wordnet can find relationships between words
        System.out.println("\n\nRelationship between: " + start + " and " + end);
        float dist1 = wordnet.getDistance(start,end,pos);
        String[] parents1 = wordnet.getCommonParents(start1, end1, pos);
        System.out.println(start1 + " and " + end1 + " are related by a distance of: " + dist1);



        // These words have common parents (hyponyms in this case)
        System.out.println("Common parents: ");
        if (parents != null) {
            for (int i = 0; i < parents.length; i++) {
                System.out.println(parents[i]);
            }
        }

        //System.out.println("\n\nHypernym Tree for " + start);
        //int[] ids = wordnet.getSenseIds(start,wordnet.NOUN);
        //wordnet.printHypernymTree(ids[0]);

    }
}
