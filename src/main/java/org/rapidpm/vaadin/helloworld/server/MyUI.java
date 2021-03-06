package org.rapidpm.vaadin.helloworld.server;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

/**
 *
 */
public class MyUI extends UI {

  public static final String BUTTON_ID  = "buttonID";
  public static final String INPUT_ID_A = "input_A_ID";
  public static final String INPUT_ID_B = "input_B_ID";
  public static final String OUTPUT_ID  = "outputID";

  @Override
  protected void init(VaadinRequest request) {
    final TextField inputA = new TextField();
    inputA.setId(INPUT_ID_A);

    final TextField inputB = new TextField();
    inputB.setId(INPUT_ID_B);

    final TextField output = new TextField();
    output.setId(OUTPUT_ID);
    output.setReadOnly(true);

    final Button button = new Button("click me");
    button.setId(BUTTON_ID);
    button.addClickListener(
        event -> output.setValue(
            inputA.getValue() + inputB.getValue()));

    setContent(
        new HorizontalLayout(
            inputA,
            new Label("+"),
            inputB,
            button,
            output
        ));
  }
}
