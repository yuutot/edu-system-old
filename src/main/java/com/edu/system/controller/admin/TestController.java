package com.edu.system.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.system.controller.AccessRoles;
import com.edu.system.controller.Roles;
import com.edu.system.service.ServiceException;
import com.edu.system.service.TestService;
import com.edu.system.vo.Test;

@Controller
@RequestMapping("admin/test")
@AccessRoles(Roles.ADMIN)
public class TestController {

    private final TestService testService;
    private final ArticleController articlePage;

    @Autowired
    public TestController(TestService testService, ArticleController articlePage) {
        this.testService = testService;
        this.articlePage = articlePage;
    }


    @GetMapping("{id}")
    public String home(@PathVariable("id") Long id, Model model) throws ServiceException {
        model.addAttribute("test", testService.get(id));
        return "admin_test";
    }


    @PostMapping("{id}")
    public String delete(@PathVariable("id") Long id, Model model) throws ServiceException {
        Test test = testService.get(id);
        testService.delete(id);
        return articlePage.home(test.getArticle().getId(), model);
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) throws ServiceException {
        model.addAttribute("test", testService.get(id));
        return "edit_test";
    }

    @PostMapping("{id}/edit")
    public void editTest(@PathVariable("id") Long id, @RequestParam("name") String name, @RequestParam("body") String body,
                           @RequestParam("condition") String condition, HttpServletResponse response) throws ServiceException, IOException {
        Test test = testService.get(id);
        test.setName(name);
        test.setBody(body);
        test.setCondition(condition);
        testService.update(test);
        response.sendRedirect("/admin/article/" + test.getArticle().getId());
    }

}
