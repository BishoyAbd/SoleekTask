package com.project.bishoy.soleektask.weddingdetails;

import com.project.bishoy.soleektask.data.model.ServerResponse;

/**
 * Created by bisho on 1/24/2018.
 */

public class TipsAndTodos {


    private final ServerResponse todoResponse;
    private final ServerResponse tipsResponce;

    public ServerResponse getTipsResponce() {
        return tipsResponce;
    }

    public ServerResponse getTodoResponse() {
        return todoResponse;
    }

    public TipsAndTodos(ServerResponse todoResponse, ServerResponse tipsResponce) {

        this.todoResponse = todoResponse;
        this.tipsResponce = tipsResponce;

    }
}
