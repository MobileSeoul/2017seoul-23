package com.example.naejang.chat_bot_project;

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import java.util.List;

/**
 * Created by naejang on 2017-09-25.
 */

public interface ConversationCallbacks {

    void test(MessageResponse response);

}
