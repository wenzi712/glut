package com.jacknic.glut.fragments.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacknic.glut.R;
import com.jacknic.glut.utils.Config;

import static android.content.Context.MODE_PRIVATE;


/**
 * 账户设置
 */
public class AccountSettingsFragment extends Fragment implements View.OnClickListener {

    private View fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_setting_account, container, false);
        setOnClick();
        return fragment;
    }

    private void setOnClick() {
        fragment.findViewById(R.id.setting_btn_clear_cw).setOnClickListener(this);
        fragment.findViewById(R.id.setting_btn_clear_ts).setOnClickListener(this);
    }

    //控件点击事件处理
    @Override
    public void onClick(final View v) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("确定清除?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (v.getId()) {
                            case R.id.setting_btn_clear_cw:
                                clear_cw();
                                break;
                            case R.id.setting_btn_clear_ts:
                                clear_ts();
                                break;
                        }
                        SharedPreferences setting = getActivity().getSharedPreferences(Config.PREFER_SETTING, MODE_PRIVATE);
                        setting.edit().putBoolean(Config.SETTING_IS_REFRESH, true).apply();
                    }
                }).create();
        dialog.show();


    }

    /**
     * 清除财务信息
     */
    private void clear_cw() {
        getContext().getSharedPreferences(Config.PREFER_CW, Context.MODE_PRIVATE).edit().clear().apply();
    }

    /**
     * 清除图书信息
     */
    private void clear_ts() {

    }


}