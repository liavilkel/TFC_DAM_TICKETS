package com.example.tfc_dam_tickets.autenticacion;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){

        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    public Fragment getItem (int posicion) {
        switch (posicion) {
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;

            case 1:
                RegistroFragment registroFragment = new RegistroFragment();
                Log.e("ERROR", "EL BOTON ESTA PULSADO");
                return registroFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

