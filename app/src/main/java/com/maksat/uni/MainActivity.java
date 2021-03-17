package com.maksat.uni;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.maksat.uni.customUI.NavigationDrawerFragment;
import com.maksat.uni.fragments.auth.LoginFragment;
import com.maksat.uni.fragments.contacts.ContactsFragment;
import com.maksat.uni.fragments.home.HomeFragment;
import com.maksat.uni.fragments.participants.ParticipantsFragment;
import com.maksat.uni.fragments.program.ProgramFragment;
import com.maksat.uni.fragments.statistic.StatisticFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NavigationDrawerFragment bottomNavFragment;
    public MainActivity mainActivity;
    public SharedPreferences preferences;
    public FragmentManager fragmentManager;
    private FragmentTransaction transition;
    boolean doubleBackToExitPressedOnce = false;
    public BottomAppBar bottomAppBar;
    public MaterialButton search_btn, share_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*try {
            MethodHandles.Lookup lookup = MethodHandles.publicLookup();
            MethodType methodType = MethodType.methodType(String.class, char.class, char.class);
            MethodHandle replaceMethodHandle =
                    lookup.findVirtual(String.class, "replace", methodType);

            String output = (String) replaceMethodHandle.invoke("jovo", 'o', 'a');
            System.out.println(output);
        } catch (Throwable e) {
            System.out.println("Exception from MethodHandle code " + e);
        }*/
        mainActivity = this;
        findIDs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        preferences =this.getSharedPreferences("event", Context.MODE_PRIVATE);
        fragmentManager = getSupportFragmentManager();
        if (Variables.token.equals("none")){
            bottomAppBar.setVisibility(View.GONE);
            ChangeScreen("auth");
        }else {
            if (fragmentManager.findFragmentByTag("participantInfo")==null && fragmentManager.findFragmentByTag("statisticSearch")==null) {
                if (bottomAppBar.getVisibility()==View.GONE){
                    bottomAppBar.setVisibility(View.VISIBLE);
                }
                ChangeScreen(Variables.fragment);
            }
        }
    }

    private void findIDs() {
        MaterialButton menu_btn=findViewById(R.id.menu_btn);
        bottomAppBar=findViewById(R.id.bottomAppBar);
        menu_btn.setOnClickListener(v -> {
            try {
                if (bottomNavFragment == null) {
                    bottomNavFragment = new NavigationDrawerFragment(mainActivity);
                }
                if (!bottomNavFragment.isAdded()) {
                    bottomNavFragment.setOnLogoutClickListener(() -> {
                        bottomNavFragment.dismiss();
                        Variables.token="none";
                        preferences.edit().putString("token", "").apply();
                        bottomAppBar.setVisibility(View.GONE);
                        ChangeScreen("auth");
                    });
                    bottomNavFragment.show(fragmentManager, "bottomNavFragment");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        bottomAppBar=findViewById(R.id.bottomAppBar);
        share_btn=findViewById(R.id.share_btn);
        search_btn=findViewById(R.id.srch_btn);
    }


    public void ChangeScreen(String tag){
        switch (tag){
            case "auth":
                if (fragmentManager.findFragmentByTag("auth")!=null){
                    fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag("auth"))).commit();
                }else {
                    fragmentManager.beginTransaction().add(R.id.container, new LoginFragment(mainActivity), "auth").commit();
                }
                for (Fragment fragment : fragmentManager.getFragments()) {
                    if (fragment instanceof NavigationDrawerFragment) {
                        continue;
                    }
                    else if (fragment != null) {
                        fragmentManager.beginTransaction().hide(fragment).commit();
                        fragmentManager.beginTransaction().remove(fragment).commit();
                    }
                }
                break;
            case "one":
                if (fragmentManager.findFragmentByTag("one")!=null){
                    fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag("one"))).commit();
                }else {
                    fragmentManager.beginTransaction().add(R.id.container, new HomeFragment(mainActivity), "one").commit();
                }
                search_btn.setVisibility(View.GONE);
                share_btn.setVisibility(View.GONE);
                break;
            case "two":
                if (fragmentManager.findFragmentByTag("two")!=null){
                    fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag("two"))).commit();
                }else {
                    fragmentManager.beginTransaction().add(R.id.container, new StatisticFragment(mainActivity), "two").commit();
                }
                search_btn.setVisibility(View.GONE);
                share_btn.setVisibility(View.GONE);
                break;
            case "three":
                if (fragmentManager.findFragmentByTag("three")!=null){
                    fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag("three"))).commit();
                }else {
                    fragmentManager.beginTransaction().add(R.id.container, new ParticipantsFragment(mainActivity), "three").commit();
                }
                search_btn.setVisibility(View.VISIBLE);
                share_btn.setVisibility(View.VISIBLE);
                break;
            case "four":
                if (fragmentManager.findFragmentByTag("four")!=null){
                    fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag("four"))).commit();
                }else {
                    fragmentManager.beginTransaction().add(R.id.container, new ProgramFragment(mainActivity), "four").commit();
                }
                search_btn.setVisibility(View.VISIBLE);
                share_btn.setVisibility(View.VISIBLE);
                break;
            case "five":
                if (fragmentManager.findFragmentByTag("five")!=null){
                    fragmentManager.beginTransaction().show(Objects.requireNonNull(fragmentManager.findFragmentByTag("five"))).commit();
                }else {
                    fragmentManager.beginTransaction().add(R.id.container, new ContactsFragment(mainActivity), "five").commit();
                }
                search_btn.setVisibility(View.VISIBLE);
                share_btn.setVisibility(View.GONE);
                break;

        }
        for (Fragment fragment: fragmentManager.getFragments()){
            if (fragment.getTag()!=null) {
                if ((fragment.isVisible()) && (!fragment.getTag().equals(tag)) && (!fragment.getTag().equals("bottomNavFragment"))) {
                    fragmentManager.beginTransaction().hide(fragment).commit();
                }
            }
        }
    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragmentManager.findFragmentByTag("participantInfo")!=null) {
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("three")).commit();
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("participantInfo")).commit();
        }
    }*/

    @Override
    public void onBackPressed() {
        HomeFragment homeFragment=(HomeFragment) getSupportFragmentManager().findFragmentByTag("one");
        if(homeFragment!=null && homeFragment.isVisible()) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    }
}