package de.wwag.hackathon.team2.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DeskgroupMapperTest {

    private DeskgroupMapper deskgroupMapper;

    @BeforeEach
    public void setUp() {
        deskgroupMapper = new DeskgroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(deskgroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deskgroupMapper.fromId(null)).isNull();
    }
}
