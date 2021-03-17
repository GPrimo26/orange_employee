package com.maksat.uni.customUI;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.models.Events;

public class EventsDialog extends DialogFragment {
    private MainActivity mainClass;
    private static final String TAG="events";
    private Integer eventId;
    private OnDismissListener mListener;
    public EventsDialog(MainActivity mainClass) {
        this.mainClass=mainClass;
        eventId=Variables.currentEvent.getId();
    }
    public interface OnDismissListener{
        void onDismiss(Events.events event);

        void onDismiss();
    }
    public void setOnDismissClickListener(OnDismissListener dismisslistener) {
        mListener = dismisslistener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RadioGroup radioGroup=view.findViewById(R.id.radioGroup);

      /*  android:id="@+id/champ_rb"
        style="@style/MyCheckBoxStyle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:fontFamily="@font/roboto"
        android:paddingLeft="16dp"
        android:text="Чемпионат"
        android:textColor="#DE000000"
        android:textSize="16sp"*/
        final float scale = requireContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (16 * scale + 0.5f);
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(pixels, 0, pixels, 0);
        Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto);

        Button close_btn=view.findViewById(R.id.close_btn);
        Button change_btn=view.findViewById(R.id.change_btn);
        for(Events.events event: Variables.events){
            RadioButton radioButton=new RadioButton(requireContext());
            radioButton.setId(event.getId());
            //radioButton.setButtonDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.rb_colors, null));
            radioButton.setTypeface(typeface);
            radioButton.setText(event.getNameShortRus());
            radioButton.setTextColor(Color.parseColor("#DE000000"));
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            radioButton.setLayoutParams(layoutParams);
            radioGroup.addView(radioButton);
            if (event.getId().equals(Variables.currentEvent.getId())){
                radioButton.setChecked(true);
            }
        }
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDismiss();
            }
        });
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Events.events event:Variables.events){
                    if (eventId.equals(event.getId())){
                        mListener.onDismiss(event);
                    }
                }
            }
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            eventId=checkedId;
        });
    }
}
