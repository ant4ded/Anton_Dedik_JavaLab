package com.epam.esm.core.service.impl;

import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.core.service.GiftTagService;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GiftTagServiceImpl implements GiftTagService {
    private final GiftTagRepository repository;
    private final EntityValidatorService validatorService;

    @Autowired
    public GiftTagServiceImpl(GiftTagRepository repository, EntityValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    @Override
    public Optional<GiftTag> findByName(String name) {
        return Optional.ofNullable(repository.findByName(name));
    }

    @Override
    public long save(GiftTag giftTag) throws InvalidEntityFieldException {
        validatorService.validateTag(giftTag);
        return repository.save(giftTag);
    }

    @Override
    public boolean delete(GiftTag giftTag) {
        return repository.deleteById(findByName(giftTag.getName()).orElse(new GiftTag()).getId());
    }
}
