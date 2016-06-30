import edu.stanford.nlp.hcoref.CorefCoreAnnotations;
import edu.stanford.nlp.hcoref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Mayanka on 13-Jun-16.
 */
public class Main {
    public static void main(String args[]) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

// read some text in the text variable
        String text = "@realDonaldTrump: #HillaryClinton may be the most corrupt person ever to seek the presidency of the United States.\n" +
                "@realDonaldTrump Man you are a god damn idiot with no business even being a janitor at the White House!  Scotland voted to remain! #moron\n" +
                "@realDonaldTrump @Jolena3 So many anti-Americans trolling our next President's tweets. You can sense their nervousness rising. \n" +
                "@realDonaldTrump I pray the cartels get ahold of you before the US elections. You are by far the most arrogant ignorant evil son of a bitch\n" +
                "@realDonaldTrump @Jolena3 And we will have a President Trump working for the common man not Wall Street. MAKE AMERICA GREAT AGSIN!\n" +
                "@HillaryClinton Don't know how to say this, but Scotland liking you more than Trump does not mean Scotland likes you\n" +
                "@HillaryClinton They should have thought of that before they crossed into our Country with no Passport or Visa. Price they pay breaking law\n" +
                "@HillaryClinton: 44 years since #TitleIX passed, truer than ever: All stude*nts deserve equal opportunity, no matter their gender.\n" +
                "@HillaryClinton Plz tell that BREXIT & TAKIN R COUNTRY BAC has NOTH 2DO WITH lil hands supporters BUT DEMS TAKIN R COUNTRY BAC FROM REPUBS\n" +
                "@HillaryClinton I hear one of your super deligates is going to prison  .... you're next!\n"; // Add your text here!



// create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

// run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                System.out.println("\n" + token);

                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                System.out.println("Text Annotation");
                System.out.println(token + ":" + word);
                // this is the POS tag of the token

                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                System.out.println("Lemma Annotation");
                System.out.println(token + ":" + lemma);
                // this is the Lemmatized tag of the token


                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println("POS");
                System.out.println(token + ":" + pos);

                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println("NER");
                System.out.println(token + ":" + ne);

                System.out.println("\n\n");
            }

            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println(tree);
            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println(dependencies.toString());

            Map<Integer, CorefChain> graph =
                    document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
            System.out.println(graph.values().toString());
        }

    }
}
