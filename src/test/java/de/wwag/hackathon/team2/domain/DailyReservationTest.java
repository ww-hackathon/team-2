package de.wwag.hackathon.team2.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.wwag.hackathon.team2.web.rest.TestUtil;

public class DailyReservationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyReservation.class);
        DailyReservation dailyReservation1 = new DailyReservation();
        dailyReservation1.setId(1L);
        DailyReservation dailyReservation2 = new DailyReservation();
        dailyReservation2.setId(dailyReservation1.getId());
        assertThat(dailyReservation1).isEqualTo(dailyReservation2);
        dailyReservation2.setId(2L);
        assertThat(dailyReservation1).isNotEqualTo(dailyReservation2);
        dailyReservation1.setId(null);
        assertThat(dailyReservation1).isNotEqualTo(dailyReservation2);
    }
}
