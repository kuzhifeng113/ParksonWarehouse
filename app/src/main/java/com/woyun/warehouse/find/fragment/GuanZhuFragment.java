package com.woyun.warehouse.find.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 关注
 */
public class GuanZhuFragment extends BaseFragment {
    private static final String TAG = "GuanZhuFragment";
    private static final String FIND_TYPE = "find_type";
    Unbinder unbinder;
    @BindView(R.id.tv)
    TextView tv;
    private String userId;
    private int findTypeId;


    public static GuanZhuFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(FIND_TYPE, page);
        GuanZhuFragment fragment = new GuanZhuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findTypeId = getArguments().getInt(FIND_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_guanz, container, false);
        unbinder = ButterKnife.bind(this, view);
        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        tv.setText("关注" + findTypeId);
        Log.e(TAG, "onCreateView: "+findTypeId );
        return view;
    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
//        initData();
    }

    private void initData() {

    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
