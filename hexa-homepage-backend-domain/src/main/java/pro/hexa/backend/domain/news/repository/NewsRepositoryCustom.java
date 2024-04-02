package pro.hexa.backend.domain.news.repository;

import pro.hexa.backend.domain.news.domain.News;

import java.util.List;

public interface NewsRepositoryCustom {

    List<News> findForMainPageByQuery();

    List<News> findAllWithPaging(int pageNum, int perPage);

    int getMaxPage(Integer perPage);
}
