package seckill.log;


import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingHandler {
    Logger log = LoggerFactory.getLogger(this.getClass());

    //TODO implement poincuts and advices
}
