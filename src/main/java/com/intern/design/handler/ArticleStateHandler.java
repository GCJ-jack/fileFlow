package com.intern.design.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.intern.design.bean.Article;
import com.intern.design.enums.ArticleState;
import com.intern.design.enums.StateEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;

@Slf4j
public class ArticleStateHandler {

    @SneakyThrows
    public synchronized static void handleArticleState(Article article, StateEvent event){
        StateMachine<ArticleState, StateEvent> stateMachine = SpringUtil.getBean("stateMachine");

        StateMachinePersister<ArticleState, StateEvent, Article> persister = SpringUtil.getBean("persister");

        try {
            stateMachine.start();

            persister.restore(stateMachine, article);

            Message<StateEvent> message = MessageBuilder.withPayload(event)
                    .setHeader("article", article)
                    .build();

            boolean res = stateMachine.sendEvent(message);

            persister.persist(stateMachine, article);

            if (!res) {
                log.error("从{}状态, 触发{}事件失败", article.getState().getDesc(), event);
                throw new Exception("状态机状态转换失败");
            }

        }finally {
            stateMachine.stop();
        }

    }
}
