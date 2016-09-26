package com.example;

import com.google.auto.service.AutoService;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;


@AutoService(Processor.class)//自动生成META-INF配置信息
@SupportedAnnotationTypes("com.example.MyAnnotation")//列出支持的注解
public class MyProcessor extends AbstractProcessor{

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //取出每一个用MyAnnotation注解的元素
        for (Element element:roundEnv.getElementsAnnotatedWith(MyAnnotation.class)) {
            //如果这个元素是一个方法
            if(element.getKind()== ElementKind.METHOD){
                //强转成方法对应的element，同理，如果你的注解是一个类，那你可以强转成TypeElement
                ExecutableElement exEl= (ExecutableElement) element;
                //打印方法的返回类型
                System.out.println(exEl.getReturnType().toString());

                //获取方法所有的参数
                List<? extends VariableElement> params = exEl.getParameters();
                //逐个打印参数名
                for(VariableElement variableElement : params){
                    System.out.println(variableElement.getSimpleName());
                }

                //打印注解的值
                System.out.println(exEl.getAnnotation(MyAnnotation.class).value());
            }
        }
        return false;
    }
}
