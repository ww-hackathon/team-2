package de.wwag.hackathon.team2.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DailyReservationMapperTest {

    private DailyReservationMapper dailyReservationMapper;

    @BeforeEach
    public void setUp() {
        dailyReservationMapper = new DailyReservationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dailyReservationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dailyReservationMapper.fromId(null)).isNull();
    }
}
