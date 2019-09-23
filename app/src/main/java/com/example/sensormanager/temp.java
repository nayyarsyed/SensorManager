package com.example.sensormanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

public class temp extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creating a new RelativeLayout
        RelativeLayout mainRelativeLayout = new RelativeLayout(this);

        // Defining the RelativeLayout layout parameters with Fill_Parent
        @SuppressWarnings("deprecation") RelativeLayout.LayoutParams relativeLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);

        // Creating a new Left Button
        Button buttonLeft = new Button(this);
        buttonLeft.setText("Left");

        // Creating a new Left Button with Margin
        Button buttonLeftWithMargin = new Button(this);
        buttonLeftWithMargin.setText("Left with Margin");

        // Creating a new Center Button
        Button buttonCenterParent = new Button(this);
        buttonCenterParent.setText("Center");

        // Creating a new Bottom Button
        Button buttonBottom = new Button(this);
        buttonBottom.setText("Bottom");

        // Add a Layout to the Buttons
        AddButtonLayout(buttonLeft, RelativeLayout.ALIGN_PARENT_LEFT);
        AddButtonLayout(buttonCenterParent, RelativeLayout.CENTER_IN_PARENT);
        AddButtonLayout(buttonBottom, RelativeLayout.ALIGN_PARENT_BOTTOM);

        // Add a Layout to the Button with Margin
        AddButtonLayout(buttonLeftWithMargin, RelativeLayout.ALIGN_PARENT_LEFT, 30, 80, 0, 0);

        // Add the Buttons to the View
        mainRelativeLayout.addView(buttonLeft);
        mainRelativeLayout.addView(buttonCenterParent);
        mainRelativeLayout.addView(buttonBottom);
        mainRelativeLayout.addView(buttonLeftWithMargin);

        // Setting the RelativeLayout as our content view
        setContentView(mainRelativeLayout, relativeLayoutParameters);
    }

    private void AddButtonLayout(Button button, int centerInParent, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        // Defining the layout parameters of the Button
        RelativeLayout.LayoutParams buttonLayoutParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Add Margin to the LayoutParameters
        buttonLayoutParameters.setMargins(marginLeft, marginTop, marginRight, marginBottom);

        // Add Rule to Layout
        buttonLayoutParameters.addRule(centerInParent);

        // Setting the parameters on the Button
        button.setLayoutParams(buttonLayoutParameters);
    }

    private void AddButtonLayout(Button button, int centerInParent) {
        // Just call the other AddButtonLayout Method with Margin 0
        AddButtonLayout(button, centerInParent, 0 ,0 ,0 ,0);
    }
}