package com.etiya.milestonemanager.user.impl;

import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.entity.UserEntity;
import com.etiya.milestonemanager.data.repository.IUserRepository;
import com.etiya.milestonemanager.user.IUserTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//Lombok
@Log4j2

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserTestImpl implements IUserTest {

    private UserEntity userEntity;
    private final IUserRepository iUserRepository;

    public UserTestImpl(UserEntity userEntity, IUserRepository iUserRepository) {
        this.userEntity = userEntity;
        this.iUserRepository = iUserRepository;
    }

    @BeforeAll
    static void getBeforeAllProjectsAllMethod() {
        System.out.println("******User Methodlarından hepsinden Önce********");
        log.info("******User Methodlarından hepsinden Önce********");
    }

    @BeforeEach
    void getProjectAllMethod() {
        System.out.println("User Methodlarından Hemen Önce");
        log.info("User Methodlarından hepsinden Önce");

        userEntity = new UserEntity();
    }

    @Test
    @Override
    public void getFail() {
    }


    @RepeatedTest(1)
    @Order(1)
    @Tag("create")
    @Override
    public void UserCreateTest() {
        System.out.println("User Create");
        userEntity.setUserName("Buse Keklik");
        iUserRepository.save(userEntity);
        assertNotNull(iUserRepository.findById(1L).get());
    }

    @Test
    @Order(2)
    @Tag("find")
    @Override
    public void UserFindTest() {
        System.out.println("User Find");
        // Bulma
        userEntity=iUserRepository.findById(1L).get();
        assertEquals("Buse Keklik",userEntity.getUserName());

    }

    @Test
    @Order(3)
    @Tag("list")
    @Override
    public void UserListTest() {
        System.out.println("Users List");
        Iterable<UserEntity> list=iUserRepository.findAll();
        assertThat(list).size().isGreaterThan(0);


    }

    @Test
    @Order(4)
    @Tag("update")
    @Override
    public void UserUpdateTest() {
        System.out.println("User Update");
        userEntity = iUserRepository.findById(1L).get();
        userEntity.setUserName("Sude");
        iUserRepository.save(userEntity);
        assertNotEquals("Buse Keklik",iUserRepository.findById(1L).get().getUserName());
    }

    @Test
    @Order(5)
    @Tag("delete")
    @Override
    public void UserDeleteTest() {
        System.out.println("User Delete");
        iUserRepository.deleteById(1L);
        assertThat(iUserRepository.existsById(1L)).isFalse();
    }
}
