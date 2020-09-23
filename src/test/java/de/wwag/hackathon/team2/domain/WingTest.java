package de.wwag.hackathon.team2.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.wwag.hackathon.team2.web.rest.TestUtil;

public class WingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wing.class);
        Wing wing1 = new Wing();
        wing1.setId(1L);
        Wing wing2 = new Wing();
        wing2.setId(wing1.getId());
        assertThat(wing1).isEqualTo(wing2);
        wing2.setId(2L);
        assertThat(wing1).isNotEqualTo(wing2);
        wing1.setId(null);
        assertThat(wing1).isNotEqualTo(wing2);
    }
}
