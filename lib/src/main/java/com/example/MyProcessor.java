package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;


@AutoService(Processor.class)
@SupportedAnnotationTypes("com.example.MyAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MyProcessor extends AbstractProcessor{

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer=processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        MethodSpec main=MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class,"args")
                .addCode("System.out.println(\"Hello APT!\");")
                /*.addStatement("$T.out.println($s)",System.class,"Hello APT")*/
                .build();
        TypeSpec helloword=TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                .addMethod(main).build();

        JavaFile javaFile=JavaFile.builder("com.zilla.libraryzilla",helloword).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Element element:roundEnv.getElementsAnnotatedWith(MyAnnotation.class)) {

            if(element.getKind()== ElementKind.METHOD){

                ExecutableElement exEl= (ExecutableElement) element;

                System.out.println("method return type="+exEl.getReturnType().toString());

                System.out.println("method name="+exEl.getSimpleName());

                List<? extends VariableElement> params = exEl.getParameters();

                for(VariableElement variableElement : params){
                    System.out.println("method parameter name="+variableElement.getSimpleName());
                }


                System.out.println("annotation value="+exEl.getAnnotation(MyAnnotation.class).value());
            }
        }
        return false;
    }
}
