package com.etiya.milestonemanager.role.impl;

import com.etiya.milestonemanager.data.entity.RoleEntity;
import com.etiya.milestonemanager.role.IRoleTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class RoleTestImpl implements IRoleTest {

    @Autowired
    private IRoleServices<RoleDto, RoleEntity> roleServices;

    @Autowired
    private IRoleRepository roleRepository;

    private RoleEntity roleEntity;

    @BeforeAll
    static void setUpBeforeAll() {
        log.info("Starting Role Tests...");
    }

    @BeforeEach
    void setUpBeforeEach() {
        log.info("Preparing for a Role test...");
        roleEntity = new RoleEntity();
        roleEntity.setRoleName("Administrator");
        roleEntity.setDescription("Admin has full access");
        roleRepository.save(roleEntity);
    }

    @Test
    @Order(1)
    @Tag("create")
    @Override
    public void RoleCreateTest() {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName("Editor");
        roleDto.setDescription("Editor has limited access");
        RoleDto createdRole = roleServices.roleServiceCreate(roleDto);
        assertNotNull(createdRole);
        log.info("Role creation test passed.");
    }

    @Test
    @Order(2)
    @Tag("find")
    @Override
    public void RoleFindTest() {
        RoleDto foundRole = roleServices.roleServiceFindById(roleEntity.getRoleID());
        assertNotNull(foundRole);
        assertEquals("Administrator", foundRole.getRoleName());
        log.info("Role find test passed.");
    }

    @Test
    @Order(3)
    @Tag("list")
    @Override
    public void RoleListTest() {
        List<RoleDto> roles = roleServices.roleServiceList();
        assertThat(roles).isNotEmpty();
        log.info("Role listing test passed.");
    }

    @Test
    @Order(4)
    @Tag("update")
    @Override
    public void RoleUpdateTest() {
        RoleDto roleDto = roleServices.roleServiceFindById(roleEntity.getRoleID());
        roleDto.setRoleName("Super Admin");
        roleDto.setDescription("Super Admin has all new privileges");
        RoleDto updatedRole = roleServices.roleServiceUpdateById(roleEntity.getRoleID(), roleDto);
        assertNotNull(updatedRole);
        assertEquals("Super Admin", updatedRole.getRoleName());
        log.info("Role update test passed.");
    }

    @Test
    @Order(5)
    @Tag("delete")
    @Override
    public void RoleDeleteTest() {
        roleServices.roleServiceDeleteById(roleEntity.getRoleID());
        assertThrows(Exception.class, () -> roleServices.roleServiceFindById(roleEntity.getRoleID()));
        log.info("Role deletion test passed.");
    }
}