package blog.com.stack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Uni on २९-१२-२०१५.
 */
public class Stack extends Activity implements Animation.AnimationListener, View.OnClickListener {

    LinearLayout element, container;
    FrameLayout frame;
    TextView tvsize, tvtop, tvtotal, tvitem;
    EditText etitem, etsize;
    Button push, pop, set;
    ArrayList<LinearLayout> element_list;
    int left, top, right, bottom, index, rwidth, rheight, size;

    //Animation handler class
    TranslateAnimation anim_1, anim_2;
    AnimationSet animationSet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    //Initializes the variables
    public void init() {
        try {
            frame = (FrameLayout) findViewById(R.id.frame);
            tvsize = (TextView) findViewById(R.id.tvsize);
            tvtop = (TextView) findViewById(R.id.tvtop);
            tvtotal = (TextView) findViewById(R.id.tvtotal);
            etitem = (EditText) findViewById(R.id.etitem);
            etsize = (EditText) findViewById(R.id.etsize);
            element_list = new ArrayList<LinearLayout>();
            push = (Button) findViewById(R.id.bpush);
            pop = (Button) findViewById(R.id.bpop);
            set = (Button) findViewById(R.id.bset);


            push.setOnClickListener(this);
            pop.setOnClickListener(this);
            set.setOnClickListener(this);
            index=-1;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error is: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bpush:
                // Check if the stack is full or not
                if(index==size-1) {  //If full
                    Toast.makeText(getApplication(), "Stack Overflow", Toast.LENGTH_SHORT).show();
                } else { // If not full
                    //if(etitem.getText().toString().trim() != "") {

                    // Creating the item to be pushed
                        element = new LinearLayout(this);
                        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(rwidth, rheight);
                        flp.leftMargin = 0;
                        flp.topMargin = 20;
                        element.setBackgroundResource(R.drawable.rectsolid);
                        element.setLayoutParams(flp);
                        tvitem = new TextView(this);
                        LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                        ilp.leftMargin = rwidth / 2 - 14;
                        ilp.topMargin = rheight / 2 - tvitem.getHeight() / 2;
                        tvitem.setTextSize(28);
                        tvitem.setText(etitem.getText().toString());
                        ilp.leftMargin = element.getWidth() / 2 - tvitem.getWidth() / 2;
                        ilp.topMargin = element.getHeight() / 2 - tvitem.getHeight() / 2;
                        element.addView(tvitem, ilp);
                        frame.addView(element);

                        //Adding the item in the ArrayList
                        element_list.add(element);
                        index++;

                        // Creating object of animaitonSet
                        animationSet = new AnimationSet(true);

                        //Horizontal animation
                        anim_1 = new TranslateAnimation(
                                            0.0f,
                                            frame.getWidth() / 2 - rwidth / 2,
                                            0.0f,
                                            0.0f
                                        );
                        anim_1.setDuration(800);
                        anim_1.setStartOffset(200);
                        anim_1.setFillAfter(true);
                        animationSet.addAnimation(anim_1);

                        //Vertical animation
                        anim_2 = new TranslateAnimation(
                                            0.0f,
                                            0.0f,
                                            0.0f,
                                            frame.getHeight() - 40 - rheight * (index + 1)
                                        );
                        anim_2.setDuration(800);
                        anim_2.setStartOffset(1000);
                        anim_2.setFillAfter(true);
                        animationSet.addAnimation(anim_2);
                        animationSet.setFillAfter(true);

                        element.startAnimation(animationSet);
                        tvtop.setText("Top: " + index);
                        tvtotal.setText("Total: " + (index + 1));
                        etitem.setText("");
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Error: Enter a valid item.", Toast.LENGTH_SHORT).show();
//                    }

                }
                break;
            case R.id.bpop:
                if(index==-1) {
                    Toast.makeText(getApplicationContext(), "Stack Underflow", Toast.LENGTH_SHORT).show();
                } else {
                    // Getting the item to be popped form the ArrayList
                    element = element_list.get(index);

                    // creating the animationSet object
                    animationSet = new AnimationSet(true);

                    //Vertical animation
                    anim_1 = new TranslateAnimation(
                                    0.0f,
                                    0.0f,
                                    frame.getHeight() - 40 - rheight * (index + 1),
                                    0.0f
                                );
                    anim_1.setDuration(800);
                    anim_1.setStartOffset(200);
                    anim_1.setFillAfter(true);
                    animationSet.addAnimation(anim_1);

                    //Horizontal animation
                    anim_2 = new TranslateAnimation(
                                    frame.getWidth() / 2 - rwidth / 2,
                                    frame.getWidth(),
                                    0.0f,
                                    0.0f
                                );
                    anim_2.setDuration(800);
                    anim_2.setStartOffset(1000);
                    anim_2.setFillAfter(true);
                    animationSet.addAnimation(anim_2);
                    animationSet.setFillAfter(true);

                    // Removing the element from the ArrayList
                    element_list.remove(index--);
                    element.startAnimation(animationSet);
                    tvtop.setText("Top: " + index);
                    tvtotal.setText("Total: " + (index + 1));
                }
                break;
            case R.id.bset: //Set the size of the stack
                size = Integer.parseInt(etsize.getText().toString());
                if(size<10 && size > 1) { // Check the size to avoid very large or
                                          // very small values

                    // Creating the container for the stack
                    left = frame.getWidth() / 2 - frame.getWidth() / 8;
                    right = frame.getWidth() / 2 - frame.getWidth() / 8;
                    top = 20;
                    bottom = 20;
                    rwidth = frame.getWidth() - left * 2;
                    rheight = (frame.getHeight() - top - bottom) / size;

                    container = new LinearLayout(Stack.this);
                    FrameLayout.LayoutParams llp = new FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT
                                    );
                    llp.setMargins(left, top, right, bottom);

                    container.setLayoutParams(llp);
                    container.setOrientation(LinearLayout.HORIZONTAL);
                    container.setBackgroundResource(R.drawable.stripe);
                    frame.addView(container);

                    tvsize.setText("Size: " + size);
                    tvtop.setText("Top: -1");
                    tvtotal.setText("Total: 0");
                    push.setEnabled(true);
                    pop.setEnabled(true);
                    etitem.setEnabled(true);
                    set.setEnabled(false);
                    etsize.setEnabled(false);
                } else {
                    Toast.makeText(Stack.this, "Enter a valid value(i.e. >1 and <10", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
