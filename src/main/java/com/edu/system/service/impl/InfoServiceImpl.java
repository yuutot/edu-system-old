package com.edu.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.system.repository.AbstractCadrRepository;
import com.edu.system.repository.InfoRepository;
import com.edu.system.service.ArticleService;
import com.edu.system.service.InfoService;
import com.edu.system.service.ServiceException;
import com.edu.system.vo.Article;
import com.edu.system.vo.Info;

@Service
public class InfoServiceImpl implements InfoService {

    private final InfoRepository infoRepository;
    private final AbstractCadrRepository abstractCadrRepository;
    private final ArticleService articleService;

    @Autowired
    public InfoServiceImpl(InfoRepository infoRepository, AbstractCadrRepository abstractCadrRepository, ArticleService articleService) {
        this.infoRepository = infoRepository;
        this.abstractCadrRepository = abstractCadrRepository;
        this.articleService = articleService;
    }

    @Override
    public void create(String name, String body, Long articleId) throws ServiceException {
        Info info = new Info();
        info.setArticle(articleService.get(articleId));
        info.setName(name);
        info.setBody(body);
        infoRepository.save(info);
    }

    @Override
    public Info get(Long id) throws ServiceException {
        return infoRepository.findById(id).orElseThrow(() -> new ServiceException("asdasda"));
    }

    @Override
    public void delete(Long id) throws ServiceException {
        infoRepository.delete(get(id));
    }

    @Override
    public Info findFirst(Long articleId) throws ServiceException {
        Article article = articleService.get(articleId);
        return infoRepository.findFirstByArticleOrderById(article);
    }

    @Override
    public void update(Info info) {
        infoRepository.save(info);
    }
}
