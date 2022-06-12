package org.linbo.demo.es.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.watcher.WatchStatus;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.linbo.demo.es.dao.AccountRespository;
import org.linbo.demo.es.entity.es.Account;
import org.linbo.demo.es.entity.in.AccountIo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinBo
 * @date 2020/2/7 17:09
 */
@Slf4j
@RestController
public class AccountService {

    @Autowired
    private AccountRespository accountRespository;

    @GetMapping("/")
    public Page<Account> list(int pageNo, int pageSize) {
        Pageable page = PageRequest.of(pageNo, pageSize);
        Page<Account> all = accountRespository.findAll(page);
        return all;
    }

    @RequestMapping("/search")
    public Page<Account> search(AccountIo params) {
//        POST /account/_search
//        {
//            "query": {
//            "bool": {
//                "should": [
//                {
//                    "match_phrase": {
//                    "name": "王"
//                }
//                },
//                {
//                    "match_phrase": {
//                    "mobile": "158"
//                }
//                }
//      ]
//            }
//        }
//        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("name", params.getName()))
                .should(QueryBuilders.matchPhraseQuery("mobile", params.getMobile()));
        Page<Account> page = (Page<Account>) accountRespository.search(query);
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        System.out.println("查询耗时： " + totalTimeMillis);
        System.out.println(query.toString());
        return page;
    }
}
