package com.epam.esm.data_access.repository;

import com.epam.esm.data_access.entity.GiftTag;

public interface GiftTagRepository {
    GiftTag findById(long id);

    long save(GiftTag giftTag);

    boolean deleteById(long id);
}
