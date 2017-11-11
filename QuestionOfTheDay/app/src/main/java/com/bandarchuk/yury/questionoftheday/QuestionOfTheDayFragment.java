package com.bandarchuk.yury.questionoftheday;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class QuestionOfTheDayFragment extends Fragment {

    private View mainView = null;
    private Button[] answerOptions = null;
    public int[] answers = new int[]{1, 0, 0, 0};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.feed_fragment, container, false);
        answerOptions = new Button[]{mainView.findViewById(R.id.answerButton1),
                                      mainView.findViewById(R.id.answerButton2),
                                      mainView.findViewById(R.id.answerButton3),
                                      mainView.findViewById(R.id.answerButton4)};
        for (Button answerOptionButton: answerOptions) {
            answerOptionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.view_flip_animation);
                    objectAnimator.setTarget(mainView);
                    objectAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            for (int index = 0; index < answerOptions.length; index++) {
                                answerOptions[index].setBackground(null);
                                if (answers[index] == 1) {
                                    answerOptions[index].setBackgroundColor(R.color.green);

                                } else {
                                    answerOptions[index].setBackgroundColor(R.color.red);
                                }
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    objectAnimator.setDuration(500);

                    objectAnimator.start();
                }
            });
        }
        return mainView;
    }
}
