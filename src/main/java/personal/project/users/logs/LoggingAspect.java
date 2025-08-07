package personal.project.users.logs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* personal.project.users.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("class=" + joinPoint.getTarget().getClass().getSimpleName()
                + " method=" + joinPoint.getSignature().getName()
                +  " step=start");
    }

    @After("execution(* personal.project.users.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            System.out.println("Argumentos: " + arg);
        }
        System.out.println("class=" + joinPoint.getTarget().getClass().getSimpleName()
                + " method=" + joinPoint.getSignature().getName()
                +  " step=end");
    }
}
