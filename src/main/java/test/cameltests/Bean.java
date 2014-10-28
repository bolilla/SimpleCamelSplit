package test.cameltests;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;

/**
 * This bean splits messages by character 'x' and creates messages with headers depending on the
 * contents of the lines
 *
 * @author Borja Roux
 *
 */
public class Bean {

  public List<Message> process(Exchange msg) {
    ArrayList<Message> result = new ArrayList<Message>();
    String[] parts = msg.getIn().getBody(String.class).split("x");
    for (String part : parts) {
      Message m = new DefaultMessage();
      if (part.equalsIgnoreCase("a")) {
        m.setHeader("h", "1");
      }
      if (part.equalsIgnoreCase("b")) {
        m.setHeader("h", "2");
      }
      if (part.equalsIgnoreCase("c")) {
        m.setHeader("h", "3");
      }
      m.setBody(part);
      result.add(m);
    }
    System.out.println("Splitted!");
    return result;
  }
}
