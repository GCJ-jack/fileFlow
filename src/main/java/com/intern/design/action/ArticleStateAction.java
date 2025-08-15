package com.intern.design.action;


import com.intern.design.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component
@WithStateMachine( name = "stateMachine")
@RequiredArgsConstructor
public class ArticleStateAction {

    private final ArticleService articleService;





}
