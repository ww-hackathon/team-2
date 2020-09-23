package de.wwag.hackathon.team2.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.wwag.hackathon.team2.web.rest.TestUtil;

public class DeskgroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeskgroupDTO.class);
        DeskgroupDTO deskgroupDTO1 = new DeskgroupDTO();
        deskgroupDTO1.setId(1L);
        DeskgroupDTO deskgroupDTO2 = new DeskgroupDTO();
        assertThat(deskgroupDTO1).isNotEqualTo(deskgroupDTO2);
        deskgroupDTO2.setId(deskgroupDTO1.getId());
        assertThat(deskgroupDTO1).isEqualTo(deskgroupDTO2);
        deskgroupDTO2.setId(2L);
        assertThat(deskgroupDTO1).isNotEqualTo(deskgroupDTO2);
        deskgroupDTO1.setId(null);
        assertThat(deskgroupDTO1).isNotEqualTo(deskgroupDTO2);
    }
}
