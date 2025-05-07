package com.example.demo.analyzers;

import com.example.demo.Util.StemmerHelper;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.Files.getAttribute;

@Component
public class MyAnalyzer extends Analyzer {

    private final CharArraySet stopWords;
    private final Set<String> protectedTerms;

    public MyAnalyzer(Set<String> stopWordsList,Set<String> protectedTermsList){
        this.stopWords = new CharArraySet(stopWordsList,true);
        this.protectedTerms = new HashSet<>(protectedTermsList);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        WhitespaceTokenizer tokenizer = new WhitespaceTokenizer();
        TokenStream tokenStream = new StopFilter(tokenizer, stopWords);
        tokenStream = new PorterStemFilter(tokenStream);
        return new TokenStreamComponents(tokenizer, tokenStream);

        @Override
        public boolean incrementToken() throws IOException{
            if(!input.incrementToken()){
                return false;
            }
            CharTermAttribute termAttribute = getAttribute(CharTermAttribute.class);
            String term = termAttribute.toString();
            return protectedTerms.contains(term);
        };
        tokenStream = new PorterStemFilter(tokenStream);
        return new TokenStreamComponents(tokenizer,tokenStream);
    }

    public String stem(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        Pair<String,String> placeHolders = StemmerHelper.getPlaceHolders(text, protectedTerms);
        text = placeHolders.getFirst();

        try(TokenStream tokenStream = tokenStream(null, new StringReader(text))) {
            StringBuilder result = new StringBuilder();
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);

            tokenStream.reset();
            while (tokenStream.incrementToken()){
                result.append(charTermAttribute.toString()).append(" ");
            }
           tokenStream.end();
            String stemmedText = result.toString();
            stemmedText = stemmedText.replace("_PLACEHOLDER_",placeHolders.getSecond());
            return stemmedText.trim();
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
}
