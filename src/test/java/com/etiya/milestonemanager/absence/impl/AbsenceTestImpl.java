package com.etiya.milestonemanager.absence.impl;

import com.etiya.milestonemanager.absence.IAbsenceTest;
import com.etiya.milestonemanager.data.entity.AbsenceEntity;
import com.etiya.milestonemanager.data.repository.IAbsenceRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// LOMBOK
@Log4j2

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AbsenceTestImpl implements IAbsenceTest {

    private AbsenceEntity absenceEntity;
    private final IAbsenceRepository iAbsenceRepository;

    public AbsenceTestImpl(IAbsenceRepository iAbsenceRepository) {
        this.iAbsenceRepository = iAbsenceRepository;
    }

    @Test
    @Override
    public void getFail() {

    }

    @RepeatedTest(1)
    @Order(1)
    @Tag("create")
    @Override
    public void AbsenceCreateTest() {
        System.out.println("Absence Create");
        absenceEntity.setDescription("health appointment");
        absenceEntity.setType("health");
        iAbsenceRepository.save(absenceEntity);
        assertNotNull(iAbsenceRepository.findById(1L).get());
    }

    @Test
    @Order(2)
    @Tag("find")
    @Override
    public void AbsenceFindTest() {
        System.out.println("Absence Find");
        absenceEntity = iAbsenceRepository.findById(1L).get();
        assertEquals("health",absenceEntity.getType());

    }

    @Test
    @Order(3)
    @Tag("list")
    @Override
    public void AbsenceListTest() {
        System.out.println("Absence List");
        Iterable<AbsenceEntity> list=iAbsenceRepository.findAll();
        assertThat(list).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Tag("update")
    @Override
    public void AbsenceUpdateTest() {
        System.out.println("Absence Update");
        absenceEntity = iAbsenceRepository.findById(1L).get();
        absenceEntity.setType("bank");
        iAbsenceRepository.save(absenceEntity);
        assertNotEquals("health",iAbsenceRepository.findById(1L).get().getType());

    }

    @Test
    @Order(5)
    @Tag("delete")
    @Override
    public void AbsenceDeleteTest() {
        System.out.println("Absence Delete");
        iAbsenceRepository.deleteById(1L);
        assertThat(iAbsenceRepository.existsById(1L)).isFalse();
    }
}
