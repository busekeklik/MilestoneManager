package com.etiya.milestonemanager.team.impl;

import com.etiya.milestonemanager.data.entity.ProjectEntity;
import com.etiya.milestonemanager.data.entity.TeamEntity;
import com.etiya.milestonemanager.data.repository.ITeamRepository;
import com.etiya.milestonemanager.team.ITeamTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//LOMBOK
@Log4j2

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TeamTestImpl implements ITeamTest {

    private TeamEntity teamEntity;
    private final ITeamRepository iTeamRepository;

    public TeamTestImpl(ITeamRepository iTeamRepository) {
        this.iTeamRepository = iTeamRepository;
    }

    @BeforeAll
    static void getBeforeAllTeamsAllMethod() {
        System.out.println("******Teams Methodlarından hepsinden Önce********");
        log.info("******Teams Methodlarından hepsinden Önce********");
    }

    @BeforeEach
    void getTeamAllMethod() {
        System.out.println("Team Methodlarından Hemen Önce");
        log.info("Team Methodlarından hepsinden Önce");

        teamEntity = new TeamEntity();
    }

    @Test
    @Override
    public void getFail() {

    }

    @RepeatedTest(1)
    @Order(1)
    @Tag("create")
    @Override
    public void TeamCreateTest() {
        System.out.println("Team Create");
        teamEntity.setTeamName("Pulsar");
        iTeamRepository.save(teamEntity);
        assertNotNull(iTeamRepository.findById(1L).get());
    }

    @Test
    @Order(2)
    @Tag("find")
    @Override
    public void TeamFindTest() {
        System.out.println("Team Find");
        // Bulma
        teamEntity = iTeamRepository.findById(1L).get();
        assertEquals("Pulsar",teamEntity.getTeamName());

    }

    @Test
    @Order(3)
    @Tag("list")
    @Override
    public void TeamListTest() {
        System.out.println("Teams List");
        Iterable<TeamEntity> list = iTeamRepository.findAll();
        assertThat(list).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Tag("update")
    @Override
    public void TeamUpdateTest() {
        System.out.println("Team Update");
        teamEntity = iTeamRepository.findById(1L).get();
        teamEntity.setTeamName("Team 1");
        iTeamRepository.save(teamEntity);
        assertNotEquals("Pulsar",iTeamRepository.findById(1L).get().getTeamName());

    }

    @Test
    @Order(5)
    @Tag("delete")
    @Override
    public void TeamDeleteTest() {
        System.out.println("Project Delete");
        iTeamRepository.deleteById(1L);
        assertThat(iTeamRepository.existsById(1L)).isFalse();
    }
}
