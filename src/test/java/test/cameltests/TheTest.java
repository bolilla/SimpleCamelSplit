package test.cameltests;

//@formatter:off

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Makes the simple test on the Bean
 *
 * @author Borja Roux
 *
 */
public class TheTest extends CamelTestSupport {
  // Endpoints to check the messages that reach them
  @EndpointInject(uri = "mock:resultA")
  protected MockEndpoint resultEndpointA;

  @EndpointInject(uri = "mock:resultB")
  protected MockEndpoint resultEndpointB;

  @EndpointInject(uri = "mock:resultC")
  protected MockEndpoint resultEndpointC;

  // Messages generate here
  @Produce(uri = "direct:start")
  protected ProducerTemplate template;

  /**
   * Sends one message and expects to receive three messages, one in each endpoint.
   *
   * @throws Exception JIC
   */
  @Test
  public void testSimpleParseFile() throws Exception {
    resultEndpointA.expectedBodiesReceived("a");
    resultEndpointB.expectedBodiesReceived("b");
    resultEndpointC.expectedBodiesReceived("c");
    template.sendBodyAndHeader("axbxc", "", "");
    resultEndpointA.assertIsSatisfied();
    resultEndpointB.assertIsSatisfied();
    resultEndpointC.assertIsSatisfied();
  }

  @Override
  protected RouteBuilder createRouteBuilder() {
    return new RouteBuilder() {
      @Override
      public void configure() {
        from("direct:start")                   // Here we begin
        .wireTap("stream:out")                 // Show to STDOUT the contents in the route at this point
        .split().method(Bean.class, "process") // Separate the message
        .wireTap("stream:out")                 // Show again contents in the route
        .choice()                              // Dispatch messages by header 
          .when(header("h").isEqualTo("1"))
            .bean(Bean1.class, "process")      // Process these messages with method "process" of bean "Bean1"...
            .to("mock:resultA")                // ... and and send the result to "mock:resultA"
          .when(header("h").isEqualTo("2"))
            .bean(Bean2.class, "process")
            .to("mock:resultB")
          .when(header("h").isEqualTo("3"))
            .bean(Bean3.class, "process")
            .to("mock:resultC");
      }
    };
  }

}
//@formatter:on
