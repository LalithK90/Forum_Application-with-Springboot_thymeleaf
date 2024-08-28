package cyou.forum.aspects;

import cyou.helps.CommonService;
import cyou.forum.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class PostAspect {

    @AfterReturning("execution(* cyou.forum.main_form_app.controller.ForumController.persistPost(..))")
    public void postAfterReturning(JoinPoint joinPoint) {
        Post post = (Post) joinPoint.getArgs()[0];
    }

/*
  @Before( "execution( * com.springsecurity3withthymeleaf.*.service.*(..))" )
  public void log(JoinPoint joinPoint) {
    System.out.printf("Appel de %s avec %d paramètres%n",
                      joinPoint.toShortString(),
                      joinPoint.getArgs().length);
    log.warning("Executing method: {}" +
                    joinPoint);

  }

  @AfterReturning( "execution(* com.springsecurity3withthymeleaf.*.service.*(..))" )
  public void logAfterReturning(JoinPoint joinPoint) {
    log.warning("Finished executing method:after " + joinPoint.getSignature().toShortString());
  }

  @Before( "execution( * com.springsecurity3withthymeleaf.asset.user_details.service.UsersDetailsService.*(..))" )
  public void userDetails(JoinPoint joinPoint) {
    System.out.printf("Appel de %s avec %d paramètres %n",
                      joinPoint.toShortString(),
                      joinPoint.getArgs().length);
    log.warning("Kehel mala method: {}" +
                    joinPoint);

  }
*/
//
//  @Before( value = "execution( * com.springsecurity3withthymeleaf.configuration.custom_handlers" +
//      ".CustomAuthenticationSuccessHandler.onAuthenticationSuccess(..))" )
//  public void login(JoinPoint joinPoint) {
//
//
//  }
//
//  @AfterReturning( value = "execution( * com.springsecurity3withthymeleaf.configuration.custom_handlers" +
//      ".CustomAuthenticationSuccessHandler.onAuthenticationSuccess(..))" )
//  public void loginafter(JoinPoint joinPoint) {
//    CommonDataFromHTTPRequest commonDataFromHTTPRequest = new CommonDataFromHTTPRequest();
//    commonDataFromHTTPRequest.commonDataFromHTTPRequest((HttpServletRequest) joinPoint.getArgs()[0]);
//
//    System.out.println(commonDataFromHTTPRequest);
//
//
//  }

}

//Types of AOP Advices

/*  @Before Advice: Advice that executes before a join point, is called before advice. We use @Before annotation to
mark advice as Before advice.

    @After Advice: Advice that executes after a join point, is called after advice. We use @After annotation to mark
    advice as After advice.

    @Around Advice: Advice that executes before and after of a join point, is called around advice.

    @AfterThrowing Advice: Advice that executes when a join point throws an exception.

    @AfterReturning Advice: Advice that executes when a method executes successfully.
    */

 /*
  Some examples of common pointcut expressions are given below.

    the execution of any public method:

    execution(public * *(..))
    the execution of any method with a name beginning with "set":

    execution(* set*(..))
    the execution of any method defined by the AccountService interface:

    execution(* com.xyz.service.AccountService.*(..))
    the execution of any method defined in the service package:

    execution(* com.xyz.service.*.*(..))
    the execution of any method defined in the service package or a sub-package:

    execution(* com.xyz.service..*.*(..))
    any join point (method execution only in Spring AOP) within the service package:

    within(com.xyz.service.*)
    any join point (method execution only in Spring AOP) within the service package or a sub-package:

    within(com.xyz.service..*)
    any join point (method execution only in Spring AOP) where the proxy implements the AccountService interface:

    this(com.xyz.service.AccountService)

    'this' is more commonly used in a binding form :- see the following section on advice for how to make the proxy
    object available in the advice body.

    any join point (method execution only in Spring AOP) where the target object implements the AccountService
    interface:

    target(com.xyz.service.AccountService)

    'target' is more commonly used in a binding form :- see the following section on advice for how to make the
    target object available in the advice body.

    any join point (method execution only in Spring AOP) which takes a single parameter, and where the argument
    passed at runtime is Serializable:

    args(java.io.Serializable)
    'args' is more commonly used in a binding form :- see the following section on advice for how to make the method
    arguments available in the advice body.

    Note that the pointcut given in this example is different to execution(* *(java.io.Serializable)): the args
    version matches if the argument passed at runtime is Serializable, the execution version matches if the method
    signature declares a single parameter of type Serializable.

    any join point (method execution only in Spring AOP) where the target object has an @Transactional annotation:

@target(org.springframework.transaction.annotation.Transactional)

'@target' can also be used in a binding form :- see the following section on advice for how to make the annotation
object available in the advice body.

    any join point (method execution only in Spring AOP) where the declared type of the target object has an
    @Transactional annotation:

@within(org.springframework.transaction.annotation.Transactional)

'@within' can also be used in a binding form :- see the following section on advice for how to make the annotation
object available in the advice body.

    any join point (method execution only in Spring AOP) where the executing method has an @Transactional annotation:

@annotation(org.springframework.transaction.annotation.Transactional)

'@annotation' can also be used in a binding form :- see the following section on advice for how to make the
annotation object available in the advice body.

    any join point (method execution only in Spring AOP) which takes a single parameter, and where the runtime type
    of the argument passed has the @Classified annotation:

@args(com.xyz.security.Classified)

'@args' can also be used in a binding form :- see the following section on advice for how to make the annotation
object(s) available in the advice body.

    any join point (method execution only in Spring AOP) on a Spring bean named 'tradeService':

    bean(tradeService)
    any join point (method execution only in Spring AOP) on Spring beans having names that match the wildcard
    expression '*Service':

    bean(*Service)
    */

  /*// Method Information
  MethodSignature signature = (MethodSignature) joinPoint.getSignature();

    System.out.println("full method description: " + signature.getMethod());
        System.out.println("method name: " + signature.getMethod().getName());
        System.out.println("declaring type: " + signature.getDeclaringType());




        // Method args
        System.out.println("Method args names:");
        Arrays.stream(signature.getParameterNames())
        .forEach(s -> System.out.println("arg name: " + s));

        System.out.println("Method args types:");
        Arrays.stream(signature.getParameterTypes())
        .forEach(s -> System.out.println("arg type: " + s));

        System.out.println("Method args values:");
        Arrays.stream(joinPoint.getArgs())
        .forEach(o -> System.out.println("arg value: " + o.toString()));

        // Additional Information
        System.out.println("returning type: " + signature.getReturnType());
        System.out.println("method modifier: " + Modifier.toString(signature.getModifiers()));
        Arrays.stream(signature.getExceptionTypes())
        .forEach(aClass -> System.out.println("exception type: " + aClass));
        */
