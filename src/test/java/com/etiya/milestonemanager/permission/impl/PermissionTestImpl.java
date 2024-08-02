package com.etiya.milestonemanager.permission.impl;

import com.etiya.milestonemanager.data.entity.PermissionEntity;
import com.etiya.milestonemanager.data.repository.IPermissionRepository;
import com.etiya.milestonemanager.permission.IPermissionTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//Lombok
@Log4j2

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PermissionTestImpl implements IPermissionTest {
    private PermissionEntity permissionEntity;
    private final IPermissionRepository iPermissionRepository;

    public PermissionTestImpl(IPermissionRepository iPermissionRepository) {
        this.iPermissionRepository = iPermissionRepository;
    }

    @BeforeAll
    static void getBeforeAllPermissionAllMethod() {
        System.out.println("******Permission Methodlarından hepsinden Önce********");
        log.info("******Permission Methodlarından hepsinden Önce********");
    }

    @BeforeEach
    void getProjectAllMethod() {
        System.out.println("Permission Methodlarından Hemen Önce");
        log.info("Permission Methodlarından hepsinden Önce");

        permissionEntity = new PermissionEntity();
    }

    @Test
    @Override
    public void getFail() {

    }

    @RepeatedTest(1)
    @Order(1)
    @Tag("create")
    @Override
    public void PermissionCreateTest() {
        System.out.println("Permission Create");
        permissionEntity.setPermissionName("Backend");
        iPermissionRepository.save(permissionEntity);
        assertNotNull(iPermissionRepository.findById(1L).get());
    }

    @Test
    @Order(2)
    @Tag("find")
    @Override
    public void PermissionFindTest() {
        System.out.println("Permission Find");
        permissionEntity = iPermissionRepository.findById(1L).get();
        assertEquals("Backend",permissionEntity.getPermissionName());
    }

    @Test
    @Order(3)
    @Tag("list")
    @Override
    public void PermissionListTest() {
        System.out.println("Permission List");
        Iterable<PermissionEntity> list=iPermissionRepository.findAll();
        assertThat(list).size().isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Tag("update")
    @Override
    public void PermissionUpdateTest() {
        System.out.println("Permission Update");
        permissionEntity = iPermissionRepository.findById(1L).get();
        permissionEntity.setPermissionName("frontend");
        iPermissionRepository.save(permissionEntity);
        assertNotEquals("Backend",iPermissionRepository.findById(1L).get().getPermissionName());
    }

    @Test
    @Order(5)
    @Tag("delete")
    @Override
    public void PermissionDeleteTest() {
        System.out.println("Permission Delete");
        iPermissionRepository.deleteById(1L);
        assertThat(iPermissionRepository.existsById(1L)).isFalse();
    }
}
