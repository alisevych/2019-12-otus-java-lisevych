package ru.otus.atm.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SuppressWarnings("unused")
@SupportedAnnotationTypes("ru.otus.atm.annotations.AuthorizedAs")
public class AuthorizedAsProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment roundEnv) {

    System.out.println(roundEnv);
    System.out.println("Annotations size: " + annotations.size());

    Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(AuthorizedAs.class);

    System.out.printf("Total elements annotated with %s: %d\n", AuthorizedAs.class.getCanonicalName(),
        elements.size());

    for (var element : elements)
      System.out.println(element.getSimpleName());

    return false;
  }
}
