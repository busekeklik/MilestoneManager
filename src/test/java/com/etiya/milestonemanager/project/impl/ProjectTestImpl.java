package com.etiya.milestonemanager.project.impl;

import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.repository.IProjectRepository;
import com.etiya.milestonemanager.project.IProjectTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// LOMBOK
@Log4j2

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ProjectTestImpl implements IProjectTest {

    private ProjectEntity projectEntity;
    private final IProjectRepository iProjectRepository;

    public ProjectTestImpl(IProjectRepository iProjectRepository) {
        this.iProjectRepository = iProjectRepository;
    }

    @BeforeAll
    static void getBeforeAllProjectsAllMethod() {
        System.out.println("******Projects Methodlarından hepsinden Önce********");
        log.info("******Projects Methodlarından hepsinden Önce********");
    }

    @BeforeEach
    void getProjectAllMethod() {
        System.out.println("Project Methodlarından Hemen Önce");
        log.info("Project Methodlarından hepsinden Önce");

        projectEntity = new ProjectEntity();
    }

    @Test
    @Override
    public void getFail() {

    }

    @RepeatedTest(1) // Bu Create testini 3 defa çalıştır
    @Order(1)
    @Tag("create")
    @Override
    public void ProjectCreateTest() {

        /*
        System.out.println("Blog Categories Create");
        blogCategoryEntity.setCategoryName("bilgisayar");
        iBlogCategoryRepository.save(blogCategoryEntity);
        assertNotNull(iBlogCategoryRepository.findById(1L).get()); // kaydedilmesi
         */

        System.out.println("Projects Create");
        projectEntity.setProjectName("Backend");
        iProjectRepository.save(projectEntity);
        assertNotNull(iProjectRepository.findById(1L).get());

    }

    @Test
    @Order(2)
    @Tag("find")
    @Override
    public void ProjectFindTest() {
        System.out.println("Blog Categories Find");
        // Bulma
        projectEntity=iProjectRepository.findById(1L).get();
        assertEquals("Backend",projectEntity.getProjectName());
    }

    @Test
    @Order(3)
    @Tag("list")
    @Override
    public void ProjectListTest() {
        System.out.println("Blog Categories List");
        Iterable<ProjectEntity> list=iProjectRepository.findAll();
        assertThat(list).size().isGreaterThan(0);

    }
    @Test
    @Order(4)
    @Tag("update")
    @Override
    public void ProjectUpdateTest() {
        System.out.println("Project Update");
        projectEntity = iProjectRepository.findById(1L).get();
        projectEntity.setProjectName("frontend");
        iProjectRepository.save(projectEntity);
        // Önceki bilgisayar ile şimdiki laptop
        assertNotEquals("Backend",iProjectRepository.findById(1L).get().getProjectName());
    }

    @Test
    @Order(5)
    @Tag("delete")
    @Override
    public void ProjectDeleteTest() {
        System.out.println("Project Delete");
        iProjectRepository.deleteById(1L);
        assertThat(iProjectRepository.existsById(1L)).isFalse();
    }
}
