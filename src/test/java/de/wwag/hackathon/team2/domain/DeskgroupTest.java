package de.wwag.hackathon.team2.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.wwag.hackathon.team2.web.rest.TestUtil;

public class DeskgroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deskgroup.class);
        Deskgroup deskgroup1 = new Deskgroup();
        deskgroup1.setId(1L);
        Deskgroup deskgroup2 = new Deskgroup();
        deskgroup2.setId(deskgroup1.getId());
        assertThat(deskgroup1).isEqualTo(deskgroup2);
        deskgroup2.setId(2L);
        assertThat(deskgroup1).isNotEqualTo(deskgroup2);
        deskgroup1.setId(null);
        assertThat(deskgroup1).isNotEqualTo(deskgroup2);
    }
}
