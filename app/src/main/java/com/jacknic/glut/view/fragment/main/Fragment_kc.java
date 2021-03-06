package com.jacknic.glut.view.fragment.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jacknic.glut.R;
import com.jacknic.glut.activity.educational.ChangeTermActivity;
import com.jacknic.glut.model.dao.CourseDao;
import com.jacknic.glut.model.entity.CourseEntity;
import com.jacknic.glut.util.ActivityUtil;
import com.jacknic.glut.util.Config;
import com.jacknic.glut.view.widget.CourseTableView;
import com.jacknic.glut.view.widget.Dialogs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 课表
 */
public class Fragment_kc extends Fragment {

    private CourseTableView timeTableView;
    private SharedPreferences prefer_jw;
    private View fragment;
    private TabLayout tabLayout;
    private ImageView iv_course_setting;
    private int week_now;
    private TextView tv_week;
    private ImageView iv_toggle;
    private LinearLayout ll_select_time;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_kc, container, false);
        initView();
        setTab();
        showSelect();
        return fragment;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        prefer_jw = getContext().getSharedPreferences(Config.PREFER_JW, Context.MODE_PRIVATE);
        timeTableView = (CourseTableView) fragment.findViewById(R.id.table_view);
        iv_course_setting = (ImageView) fragment.findViewById(R.id.kc_iv_course_setting);
        tabLayout = (TabLayout) fragment.findViewById(R.id.lv_select_week);
        iv_toggle = (ImageView) fragment.findViewById(R.id.jw_iv_toggle);
        tv_week = (TextView) fragment.findViewById(R.id.jw_tv_week);
        ll_select_time = (LinearLayout) fragment.findViewById(R.id.ll_select_time);
        iv_course_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = Dialogs.getChangeWeekDialog(getActivity());
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        refresh();
                    }
                });
                builder.show();
            }
        });
        tv_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_toggle.callOnClick();
            }
        });

    }

    /**
     * 显示当前课程周数
     */
    private void showSelect() {
        int select_week = prefer_jw.getInt(Config.JW_WEEK_SELECT, 1);
        Calendar calendar_now = Calendar.getInstance();
        int year_week_old = prefer_jw.getInt(Config.JW_YEAR_WEEK_OLD, calendar_now.get(Calendar.WEEK_OF_YEAR));
        int year_week_now = calendar_now.get(Calendar.WEEK_OF_YEAR);
        week_now = select_week + (year_week_now - year_week_old);
        if (year_week_now > year_week_old && calendar_now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week_now -= 1;
        }
        Log.d("kc", "选择周数: " + select_week);
        Log.d("kc", "现在周数: " + year_week_now);
        Log.d("kc", "存储周数: " + year_week_old);
        Log.d("kc", "实际周数: " + week_now);
        selectWeek(week_now);
        int semester = prefer_jw.getInt(Config.JW_SEMESTER, 1);
        int school_year = prefer_jw.getInt(Config.JW_SCHOOL_YEAR, Calendar.getInstance().get(Calendar.YEAR));
        TextView tv_school_year = (TextView) fragment.findViewById(R.id.jw_tv_school_year);
        TextView tv_semester = (TextView) fragment.findViewById(R.id.jw_tv_semester);
        tv_week.setText("第" + week_now + "周 ");
        tv_school_year.setText(school_year + "年");
        tv_semester.setText((semester == 1 ? "春" : "秋") + "季学期");
        View.OnClickListener change = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.lunchActivity(getContext(), ChangeTermActivity.class);
                Fragment_kc.this.fragment.invalidate();
            }
        };
        tv_school_year.setOnClickListener(change);
        tv_semester.setOnClickListener(change);
        setToggle(week_now);
    }

    /**
     * 拓展栏开关
     */
    private void setToggle(final int week) {
        iv_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout.Tab tab = tabLayout.getTabAt(week - 1);
                if (tab != null) {
                    tab.select();
                }
                int degree = -135;
                if (ll_select_time.getVisibility() == View.VISIBLE) {
                    ll_select_time.setVisibility(View.GONE);
                    v.setRotation(0f);
                } else {
                    v.setRotation(45f);
                    ll_select_time.setVisibility(View.VISIBLE);
                    degree = 135;
                }
                RotateAnimation rota = new RotateAnimation(degree, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rota.setFillAfter(true);
                rota.setDuration(500L);
                v.startAnimation(rota);
                showSelect();
            }
        });
    }

    /**
     * 设置tab滚动条
     */
    private void setTab() {
        int end_week = prefer_jw.getInt(Config.JW_WEEK_END, 30);
        for (int i = 1; i <= end_week; i++) {
            String text = "第" + i + "周";
            SpannableString spanned = new SpannableString(text);
            spanned.setSpan(new RelativeSizeSpan(1.4f), 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            TextView tv_tab = new TextView(getContext());
            ColorStateList colorStateList = getContext().getResources().getColorStateList(R.color.tab_selector);
            tv_tab.setTextColor(colorStateList);
            tv_tab.setText(spanned);
            tv_tab.setGravity(Gravity.CENTER);
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(tv_tab);
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                selectWeek(position + 1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * 选择显示周课表
     *
     * @param week 周数
     */
    private void selectWeek(int week) {
        List<CourseEntity> courses = new CourseDao().getCourse(week);
        setTime(week - week_now);
        timeTableView.setTimeTable(courses);
    }

    /**
     * 时间提示设置
     *
     * @param addWeek 周数增量
     */
    private void setTime(final int addWeek) {
        GridView gv_weekName = (GridView) fragment.findViewById(R.id.kc_gv_weekName);
        TextView tv_month = (TextView) fragment.findViewById(R.id.kc_tv_month);
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, addWeek);
        System.out.println("初始时间：" + calendar.getTime().toLocaleString());
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        System.out.println("调整后时间：" + calendar.getTime().toLocaleString());
        tv_month.setText((calendar.get(Calendar.MONTH) + 1) + "月");
        final ArrayList<Integer> weekdays = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            weekdays.add(calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DATE, 1);
        }
        gv_weekName.setAdapter(new BaseAdapter() {
            private final String[] weekNames = {"一", "二", "三", "四", "五", "六", "日",};

            @Override
            public int getCount() {
                return weekdays.size();
            }

            @Override
            public Object getItem(int position) {
                return "周" + weekNames[position] + "\n" + weekdays.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getContext());
                String text = (String) getItem(position);
                textView.setText(text);
                if ((position + Calendar.MONDAY) % 7 == Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                    textView.setBackgroundColor(0xdddedede);
                }
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });
    }

    /**
     * 刷新视图
     */
    private void refresh() {

        boolean refresh = prefer_jw.getBoolean(Config.IS_REFRESH, false);
        if (refresh) {
            prefer_jw.edit().remove(Config.IS_REFRESH).apply();
            if (ll_select_time.getVisibility() == View.VISIBLE) iv_toggle.callOnClick();
            tabLayout.removeAllTabs();
            setTab();
            showSelect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}
