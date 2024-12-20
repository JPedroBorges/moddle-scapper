package com.roomelephant.moopper.adapter;

import com.roomelephant.moopper.adapter.scrapper.GradableDTO;
import com.roomelephant.moopper.adapter.scrapper.MessageDTO;

import java.util.List;

public interface Moodle {
    List<GradableDTO> getGradables(String courseId);

    public List<MessageDTO> getMessages();
}
