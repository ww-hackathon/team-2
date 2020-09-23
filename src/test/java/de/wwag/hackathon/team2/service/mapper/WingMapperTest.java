package de.wwag.hackathon.team2.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WingMapperTest {

    private WingMapper wingMapper;

    @BeforeEach
    public void setUp() {
        wingMapper = new WingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wingMapper.fromId(null)).isNull();
    }
}
