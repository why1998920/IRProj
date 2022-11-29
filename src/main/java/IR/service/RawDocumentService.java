package IR.service;

import IR.commen.StopWordRemover;
import IR.commen.WordNormalizer;
import IR.commen.WordTokenizer;
import IR.controller.CorpusController;
import IR.dao.TweetMapper;
import IR.entity.RawTweet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class RawDocumentService {
    //max tweets being processed from a publisher
    int maxCnt=10000;
    int processedCnt=0;
    @Autowired
    private TweetMapper tweetMapper;

    public String processRawTweets(CorpusController lcc) throws Exception {
        //normalize, tokenize, stem the rawTweets and write them into mongodb

        WordNormalizer normalizer= new WordNormalizer();
        StopWordRemover remover=new StopWordRemover();

        RawTweet tmptweet= lcc.nextRawDocument();



        while(processedCnt<maxCnt&&tmptweet!=null){
            processedCnt++;
            char[] content=tmptweet.getText().toCharArray();
            WordTokenizer tokenizer=new WordTokenizer(content);
            char[] word = null;
            StringBuffer processedContent=new StringBuffer();
            //process the word within the document
            while((word=tokenizer.nextWord())!=null){
                //transfer word into lowercase
                word=normalizer.lowercase(word);

                //filter out stopword
                if(!remover.isStopword(word)){
                    processedContent.append(normalizer.stem(word));
                    processedContent.append(" ");
                }

            }

            if (tmptweet==null) {
                return "1";}

            //invoke dao to write document<id,ProcessedContent> into mongodb
            System.out.println("Id: " +tmptweet.getId());
            System.out.println("Text: " +tmptweet.getText());
            System.out.println("ProcessedContent: " +processedContent.toString());
            System.out.println("************");

            String publisher= lcc.publisherId;
            tweetMapper.insertADocumentToCorpus(tmptweet.getId(),processedContent.toString(),publisher);

            tmptweet= lcc.nextRawDocument();
        }
        return "1";
    }

}
