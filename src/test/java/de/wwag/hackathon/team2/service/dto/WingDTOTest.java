package de.wwag.hackathon.team2.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.wwag.hackathon.team2.web.rest.TestUtil;

public class WingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WingDTO.class);
        WingDTO wingDTO1 = new WingDTO();
        wingDTO1.setId(1L);
        WingDTO wingDTO2 = new WingDTO();
        assertThat(wingDTO1).isNotEqualTo(wingDTO2);
        wingDTO2.setId(wingDTO1.getId());
        assertThat(wingDTO1).isEqualTo(wingDTO2);
        wingDTO2.setId(2L);
        assertThat(wingDTO1).isNotEqualTo(wingDTO2);
        wingDTO1.setId(null);
        assertThat(wingDTO1).isNotEqualTo(wingDTO2);
    }
}
