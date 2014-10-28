package test.cameltests;

import org.apache.camel.Exchange;

public class Bean3 {

  public void process(Exchange msg) {
    System.out.println("Bean3 " + msg.getIn().getBody());
  }
}
