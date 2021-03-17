package com.maksat.uni.customUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.bottomsheets.SettingsFragment;
import com.maksat.uni.fragments.home.HomeViewModel;
import com.maksat.uni.models.Events;

import java.util.Objects;

public class NavigationDrawerFragment extends RoundedBottomSheetDialogFragment {
    public NavigationDrawerFragment(MainActivity mainClass) {
        this.mainClass= mainClass;
    }

    private class ClickListener implements View.OnClickListener {

        private String name;
        private MaterialButton btn;
        ClickListener(String name, MaterialButton btn) {
            this.name=name;
            this.btn=btn;
        }

        @Override
        public void onClick(View v) {
            try {
                btn.getBackground().setTint(ContextCompat.getColor(mainClass, R.color.colorAccentRipple));
                btn.setTextColor(ContextCompat.getColor(mainClass, R.color.colorAccent));
                btn.setIconTintResource(R.color.colorAccent);
                switch (name) {
                    case "one":
                        Variables.fragment = "one";
                        mainClass.ChangeScreen("one");
                        dismiss();
                        break;
                    case "two":
                        Variables.fragment = "two";
                        mainClass.ChangeScreen("two");
                        dismiss();
                        break;
                    case "three":
                        Variables.fragment = "three";
                        mainClass.ChangeScreen("three");
                        dismiss();
                        break;
                     case "four":
                        Variables.fragment = "four";
                        mainClass.ChangeScreen("four");
                        dismiss();
                        break;
                    case "five":
                        Variables.fragment = "five";
                        mainClass.ChangeScreen("five");
                        dismiss();
                        break;
                }

            }catch (Exception e){
                dismiss();
                //Toast.makeText(getContext(), "Произошла ошибка. Попробуйте еще раз.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }
    private final MainActivity mainClass;
    public String event="Чемпионат";
    private static OnLogoutClickListener mListener;
    public interface OnLogoutClickListener {
        void onClick();
    }

    public void setOnLogoutClickListener(OnLogoutClickListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            TextView name_tv=view.findViewById(R.id.name_tv);
            if (Variables.profile!=null){
                String name=Variables.profile.getFirstName()+" "+Variables.profile.getLastName();
                name_tv.setText(name);
            }
            TextView events_tv=view.findViewById(R.id.event_tv);
            MaterialButton home_btn = view.findViewById(R.id.home_btn);
            MaterialButton stat_btn = view.findViewById(R.id.stat_btn);
            MaterialButton memb_btn = view.findViewById(R.id.memb_btn);
            MaterialButton calndr_btn = view.findViewById(R.id.calndr_btn);
            MaterialButton cntcts_btn = view.findViewById(R.id.cntcts_btn);
            LinearLayout events_lo = view.findViewById(R.id.events_lo);
            Button settings_btn=view.findViewById(R.id.settings_btn);
            switch (Variables.fragment) {
                case "one":
                    home_btn.getBackground().setTint(ContextCompat.getColor(mainClass, R.color.colorAccentRipple));
                    home_btn.setTextColor(ContextCompat.getColor(mainClass, R.color.colorAccent));
                    home_btn.setIconTintResource(R.color.colorAccent);
                    break;
                case "two":
                    stat_btn.getBackground().setTint(ContextCompat.getColor(mainClass, R.color.colorAccentRipple));
                    stat_btn.setTextColor(ContextCompat.getColor(mainClass, R.color.colorAccent));
                    stat_btn.setIconTintResource(R.color.colorAccent);
                    break;
               case "three":
                    memb_btn.getBackground().setTint(ContextCompat.getColor(mainClass, R.color.colorAccentRipple));
                    memb_btn.setTextColor(ContextCompat.getColor(mainClass, R.color.colorAccent));
                    memb_btn.setIconTintResource(R.color.colorAccent);
                    break;
                 case "four":
                    calndr_btn.getBackground().setTint(ContextCompat.getColor(mainClass, R.color.colorAccentRipple));
                    calndr_btn.setTextColor(ContextCompat.getColor(mainClass, R.color.colorAccent));
                    calndr_btn.setIconTintResource(R.color.colorAccent);
                    break;
                case "five":
                    cntcts_btn.getBackground().setTint(ContextCompat.getColor(mainClass, R.color.colorAccentRipple));
                    cntcts_btn.setTextColor(ContextCompat.getColor(mainClass, R.color.colorAccent));
                    cntcts_btn.setIconTintResource(R.color.colorAccent);
                    break;
            }
            home_btn.setOnClickListener(new ClickListener("one", home_btn));
            stat_btn.setOnClickListener(new ClickListener("two", stat_btn));
            memb_btn.setOnClickListener(new ClickListener("three", stat_btn));
            calndr_btn.setOnClickListener(new ClickListener("four", stat_btn));
            cntcts_btn.setOnClickListener(new ClickListener("five", stat_btn));
            settings_btn.setOnClickListener(v -> {
                SettingsFragment settingsFragment=new SettingsFragment(mainClass);
                if (!settingsFragment.isAdded()){
                    settingsFragment.setOnLogoutClickListener(() -> {
                              settingsFragment.dismiss();
                              mListener.onClick();
                    });
                    settingsFragment.show(getChildFragmentManager(), "settings");
                }
            });
            events_lo.setOnClickListener(v -> {
                EventsDialog eventsDialog=new EventsDialog(mainClass);
                FragmentTransaction ft;
                if (getFragmentManager() != null) {
                    ft = getFragmentManager().beginTransaction();
                    eventsDialog.setOnDismissClickListener(new EventsDialog.OnDismissListener() {
                        @Override
                        public void onDismiss(Events.events event) {
                            events_tv.setText(event.getNameShortRus());
                            eventsDialog.dismiss();
                            if(mainClass.fragmentManager.findFragmentByTag("events")!=null){
                                mainClass.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainClass.fragmentManager.findFragmentByTag("events"))).commit();
                            }
                            HomeViewModel homeViewModel=new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
                            homeViewModel.setEvent(event);
                        }

                        @Override
                        public void onDismiss() {
                            eventsDialog.dismiss();
                            if(mainClass.fragmentManager.findFragmentByTag("events")!=null){
                                mainClass.fragmentManager.beginTransaction().remove(Objects.requireNonNull(mainClass.fragmentManager.findFragmentByTag("events"))).commit();
                            }
                        }
                    });
                    eventsDialog.show(ft, "events");
                }
            });

            events_tv.setText(Variables.currentEvent.getNameFullRus());
        }catch (Exception e){
            Toast.makeText(getContext(), "Произошла ошибка. Попробуйте еще раз.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
