package org.linbo.demo.es.dao;

import org.linbo.demo.es.entity.es.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author LinBo
 * @date 2020/2/11 16:12
 */
public interface AccountRespository extends ElasticsearchRepository<Account, Integer> {

}
