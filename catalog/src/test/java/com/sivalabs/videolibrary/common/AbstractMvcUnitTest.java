package com.sivalabs.videolibrary.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.videolibrary.common.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(Constants.PROFILE_TEST)
public abstract class AbstractMvcUnitTest {

    @Autowired protected MockMvc mockMvc;

    @Autowired protected ObjectMapper objectMapper;

}
