package com.epam.core.strategy.mongodb;

import com.epam.core.entity.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * The type Sequence dao.
 */
@Repository
@Scope("prototype")
class SequenceDao {
    private final MongoTemplate mongoTemplate;

    /**
     * Instantiates a new Sequence dao.
     *
     * @param mongoTemplate the mongo template
     */
    @Autowired
    public SequenceDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Gets next sequence id.
     *
     * @param key the key
     * @return the next sequence id
     */
    Long getNextSequenceId(String key) {
        Query query = new Query(Criteria.where("id").is(key));
        Update update = new Update();
        update.inc("sequence", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        Sequence sequence = mongoTemplate.findAndModify(query, update, options, Sequence.class);
        return sequence.getSequence();
    }
}
