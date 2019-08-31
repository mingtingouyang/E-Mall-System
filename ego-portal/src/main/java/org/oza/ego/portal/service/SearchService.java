package org.oza.ego.portal.service;

import org.oza.ego.base.vo.SearchResult;

public interface SearchService {
    SearchResult query(String q, Integer page);
}
