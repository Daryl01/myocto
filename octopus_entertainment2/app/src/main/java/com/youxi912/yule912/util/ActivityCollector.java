package com.youxi912.yule912.util;

import android.app.Activity;

import java.util.Stack;

public class ActivityCollector {

    private Stack<Activity> stack;
    private static ActivityCollector instance;

    public ActivityCollector() {
    }

    public static ActivityCollector getInstance() {
        if (instance == null)
            instance = new ActivityCollector();
        return instance;
    }

    public void add(Activity activity) {
        if (stack == null)
            stack = new Stack<>();
        stack.add(activity);
    }

    public void removeActivity(Activity activity) {
        stack.remove(activity);
        activity.finish();
    }

    public Activity getCurActivity() {
        return stack.lastElement();
    }

    public void finishAll() {
        int i = 0;
        for (; i < stack.size(); i++) {
            removeActivity(stack.get(i));
        }
    }
}
