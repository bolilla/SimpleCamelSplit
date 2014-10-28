package test.cameltests;

import org.apache.camel.Exchange;

public class Bean1 {

  public void process(Exchange msg) {
    System.out.println("Bean1 " + msg.getIn().getBody());
  }
}
