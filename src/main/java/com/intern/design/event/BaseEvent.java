package com.intern.design.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BaseEvent<T> extends ApplicationEvent {

    private final T data;

    public BaseEvent(T data, Object source){
        super(source);
        this.data = data;
    }
}
