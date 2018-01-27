package com.project.bishoy.soleektask.data.model;

/**
 * Created by bisho on 1/24/2018.
 */


import com.project.bishoy.soleektask.weddingdetails.WeddingPresenter;

/**
 * model to map both tips and todos request in the RX chain  consecutively using zip() operator
 * {@link  WeddingPresenter}
 **/
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
