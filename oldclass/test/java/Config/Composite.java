package Config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.collections.Lists;
import org.testng.internal.ClassHelper;

public class Composite implements IAnnotationTransformer  {
  //ideally you can keep these in a file separately
  private static final String ListOfListeners = "Call_Flow_Scenarios.Deep_Link_Call_Flows.Covid_19_Driver,"
      + " Call_Flow_Scenarios.Deep_Link_Call_Flows.Deep_Link_Driver,"
      + "Call_Flow_Scenarios.Deep_Link_Call_Flows.Follow_Up_Covid_Intent, Call_Flow_Scenarios.Intent_Routing_Call_Flows.intents_driver_Exception,Call_Flow_Scenarios.Intent_Routing_Call_Flows.Smart_Call_Driver";
  private List<IAnnotationTransformer> transformers = Lists.newArrayList();
  public Composite() {
    String listeners = System.getProperty("transformers", ListOfListeners);

    Arrays.stream(listeners.split(","))
        .forEach(
            each -> {
              Class<?> clazz = ClassHelper.forName(each.trim());
              IAnnotationTransformer transformer =
                  (IAnnotationTransformer) ClassHelper.newInstance(clazz);
              transformers.add(transformer);
            });
  }

  @Override
  public void transform(
      ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    for (IAnnotationTransformer each : transformers) {
      each.transform(annotation, testClass, testConstructor, testMethod);
    }
  }
}
