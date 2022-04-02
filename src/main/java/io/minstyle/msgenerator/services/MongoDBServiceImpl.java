package io.minstyle.msgenerator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.minstyle.msgenerator.model.CustomCSSModel;
import io.minstyle.msgenerator.repository.CustomCSSRepository;

/**
 * Service implementation allowing to interact with the DB, for CustomCSSModel object.
 *
 * @author Rémi Marion
 * @version 0.0.1
 */
@Service
public class MongoDBServiceImpl implements MongoDBService<CustomCSSModel> {

    private final CustomCSSRepository customCSSRepository;

    @Autowired
    public MongoDBServiceImpl(CustomCSSRepository customCSSRepository) {
        this.customCSSRepository = customCSSRepository;
    }

    /**
     * Write CustomCSSModel in databse, just for log.
     * @param customCSSModel CustomCSSModel object to write.
     * @return CustomCSSModel wrote.
     */
    public CustomCSSModel writeInDB(CustomCSSModel customCSSModel) {
        return customCSSRepository.save(customCSSModel);
    }
}
