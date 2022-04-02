package com.qrcodegenerator.creation.ui.main;

import com.qrcodegenerator.creation.ui.main.create.CreateFragment;
import com.qrcodegenerator.creation.ui.main.created.CreatedFragment;
import com.qrcodegenerator.creation.ui.main.scan.ScanFragment;
import com.qrcodegenerator.creation.ui.main.scanned.ScannedFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position % 4) {
            case 0:
                return ScanFragment.newInstance();
            case 1:
                return CreateFragment.newInstance();
            case 2:
                return ScannedFragment.newInstance();
//            case 3:
//                return CreatedFragment.newInstance();
            default:
                return CreateFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
//        return 4;
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 4) {
            case 0:
                return "Scan";
            case 1:
                return "Create";
            case 2:
                return "Scanned";
//            case 3:
//                return "Created";
        }
        return "";
    }
}
