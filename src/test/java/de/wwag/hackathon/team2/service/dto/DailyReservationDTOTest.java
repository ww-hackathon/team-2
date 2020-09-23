package de.wwag.hackathon.team2.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.wwag.hackathon.team2.web.rest.TestUtil;

public class DailyReservationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyReservationDTO.class);
        DailyReservationDTO dailyReservationDTO1 = new DailyReservationDTO();
        dailyReservationDTO1.setId(1L);
        DailyReservationDTO dailyReservationDTO2 = new DailyReservationDTO();
        assertThat(dailyReservationDTO1).isNotEqualTo(dailyReservationDTO2);
        dailyReservationDTO2.setId(dailyReservationDTO1.getId());
        assertThat(dailyReservationDTO1).isEqualTo(dailyReservationDTO2);
        dailyReservationDTO2.setId(2L);
        assertThat(dailyReservationDTO1).isNotEqualTo(dailyReservationDTO2);
        dailyReservationDTO1.setId(null);
        assertThat(dailyReservationDTO1).isNotEqualTo(dailyReservationDTO2);
    }
}
