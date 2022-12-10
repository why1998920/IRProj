package IR.service;

import IR.commen.Path;
import IR.entity.RawTweet;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class TestService {

    List<RawTweet> searchIndex(String query){
        List<String> returnedIdList=new ArrayList();
        String id1="1584866108393201664";
        String id2="1595499929224347648";
        String id3="1587482153146777601";
        returnedIdList.add(id1);
        returnedIdList.add(id2);
        returnedIdList.add(id3);

        List<RawTweet> tweetList=new ArrayList<>();
        for (String id: returnedIdList
             ) {
            tweetList.add(getTweetFromTwitterById(id));
        }

        return tweetList;
    }


    @Autowired
    private RestTemplate restTemplate;


    public RawTweet getTweetFromTwitterById(String id){
        Path path=new Path();
        String bearer= path.getTwKey();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearer);
        String tmpUrl="https://api.twitter.com/2/tweets/"+id;

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                tmpUrl, HttpMethod.GET, new HttpEntity(String.class, headers), String.class);
        Map body = (Map) JSON.parse(responseEntity.getBody());
        //get rawtweets list from responded JSON and add them to glblist
        RawTweet tmpTweets = JSON.parseObject(body.get("data").toString(),RawTweet.class);
        return tmpTweets;
    }

}
