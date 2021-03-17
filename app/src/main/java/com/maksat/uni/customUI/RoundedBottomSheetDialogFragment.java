package com.maksat.uni.customUI;

import android.app.Dialog;
import android.content.ComponentCallbacks2;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maksat.uni.R;

public class RoundedBottomSheetDialogFragment extends BottomSheetDialogFragment  {
    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
