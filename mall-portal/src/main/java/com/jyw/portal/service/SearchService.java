package com.jyw.portal.service;

import com.jyw.portal.dto.GoodDTO;

import java.io.IOException;

public interface SearchService {
    void addDocument() throws IOException;
    GoodDTO SearchDocument(String key, int currentPage, int pageSize,int priceMax,int priceMin,String sort,int quick) throws IOException;
}
