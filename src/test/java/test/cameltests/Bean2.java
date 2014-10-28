package test.cameltests;

import org.apache.camel.Exchange;

public class Bean2 {

  public void process(Exchange msg) {
    System.out.println("Bean2 " + msg.getIn().getBody());
  }
}
