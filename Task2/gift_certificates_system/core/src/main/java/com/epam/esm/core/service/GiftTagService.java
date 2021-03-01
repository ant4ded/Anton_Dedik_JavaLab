package com.epam.esm.core.service;

import com.epam.esm.data_access.entity.GiftTag;

import java.util.Optional;

public interface GiftTagService {
    Optional<GiftTag> findByName(String name);

    long save(GiftTag giftTag) throws InvalidEntityFieldException;

    boolean delete(GiftTag giftTag);
}
