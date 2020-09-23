package de.wwag.hackathon.team2.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FloorMapperTest {

    private FloorMapper floorMapper;

    @BeforeEach
    public void setUp() {
        floorMapper = new FloorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(floorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(floorMapper.fromId(null)).isNull();
    }
}
